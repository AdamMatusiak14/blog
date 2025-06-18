import React, { useEffect, useState, useRef } from 'react';
import token from '../components/token';
import {jwtDecode} from 'jwt-decode'; 
import { Client } from '@stomp/stompjs'; 
import SockJS from 'sockjs-client';
import  '../components/css/UserList.css'; // Import your CSS file for styling

const UserList = () => {
  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [loggedInUser, setLoggedInUser] = useState("loding..."); 
  const [messages, setMessages] = useState([]); // State to hold messages
  const [newMessage, setNewMessage] = useState(""); // State to hold new message input
  const stompClientRef = useRef(null); // Ref to hold the socket connection
  const messagesEndRef = useRef(null); // Ref to scroll to the bottom of the chat window

// GET USERS
  useEffect(() => {
   
    const storedToken = sessionStorage.getItem("token");
    if (storedToken) {
      try {
        const decodedToken = jwtDecode(storedToken);
        setLoggedInUser(decodedToken.sub); 
      } catch (error) {
        console.error('Error decoding token:', error);
      }
    }


   token.get("/users")
      .then(response => setUsers(response.data))
      .catch(error => console.error("Błąd pobierania użytkowników:", error));
  }, []);


 // Scroll to the bottom when messages change
  useEffect(() => {
    if (messagesEndRef.current) {
      messagesEndRef.current.scrollTop = messagesEndRef.current.scrollHeight; 
    }
  }, [messages]);


  // CONNECT TO WEBSOCKET
useEffect(() => {
  
  if (selectedUser) {
    const storedToken = sessionStorage.getItem("token");  
    console.log("This is token: ", storedToken);
    const socket = new SockJS(`http://localhost:8080/ws?token=${storedToken}`); // Use the token in the URL

    const stompClient = new Client({
    
      webSocketFactory: () => socket,
      debug: (str) => console.log(str),
      onConnect: () => {
        console.log("Connected to WebSocket");
        stompClient.subscribe("/topic/messages", (message) => {
          const receivedMessage = JSON.parse(message.body); 
          console.log("Username Reciver message:", receivedMessage.sender.username); // Log the received message
          const transformedMessage = {
            senderUsername: receivedMessage.sender.username,
            receiverUsername: receivedMessage.receiver.username,
            content: receivedMessage.content,
            timestamp: receivedMessage.timestamp,
          };
          if(transformedMessage.senderUsername !== loggedInUser){
            setMessages ((prevMessages) => [...prevMessages, transformedMessage]); 
            console.log("Received message:", receivedMessage); // Log the received message
          }
        });
      },
      onDisconnect: () => {
        console.log("Disconnected from WebSocket");
      }, 
    });

    stompClient.activate();
    stompClientRef.current = stompClient;

    return () => {
      if (stompClientRef.current) {
        stompClientRef.current.deactivate();
      }
    };
  }
}, [selectedUser]);
  

// GET THE MESSAGES FROM DATABASE
  const handleUserClick = (user) => 
{
  setSelectedUser(user);
 //  const storedToken = sessionStorage.getItem("token");
  console.log("This is receiver: ", user.username); 
  token.get(`/messages/user?senderUsername=${loggedInUser}`) 
  // &receiverUsername=${user.username}`) 
    .then((response) => {
      const formatted = response.data.map((msg) => ({
        senderUsername: msg.sender.username,
        receiverUsername: msg.receiver.username,
        content: msg.content,
        timestamp: msg.timestamp,
      }));
      setMessages(formatted);
    })
    .catch((err) => {
      console.error("Błąd pobierania wiadomości:", err);
      setMessages([]);
    });
};
  
const closeChat = () => {
  setSelectedUser(null);  
  setMessages([]); // Clear messages when chat is closed
  if (stompClientRef.current) {
    stompClientRef.current.deactivate();
  }
};

  const handleSendMessage = () => { 
    if(newMessage.trim() && stompClientRef.current && stompClientRef.current.connected) {
      const message = {
        senderUsername: loggedInUser,
        receiverUsername: selectedUser.username,
        content: newMessage.trim(),
       // timestamp: new Date().toISOString() // but is in backend
      };

      console.log("Sennder:", message.senderUsername);
      console.log("Reciver:", message.receiverUsername); // Log the message being sent

     
       stompClientRef.current.publish({
        destination: "/app/chat",
        body: JSON.stringify(message), 
      });
      console.log("Message sent:", message); // Log the sent message

      setMessages((prevMessages) => [...prevMessages, message]); // Add the sent message to the chat window
        
      setNewMessage(""); // Clear the input field after sending 
    } else{
      console.error("Stomp client is not connected or message is empty.");
    }  }; // Closing brace added here

  return (
    <div className = "user-list-container">
      <h2 className="user-list-title">Użytkownicy</h2>
      <ul className="user-list">
        {users.map(u => (
          <li 
          key={u.id}
          onClick={()=> handleUserClick(u)}
          style={{cursor: 'pointer', color: 'blue'}}
          >
           <span className="user-list-username"> {u.username} </span>
         </li>
        ))}
      </ul>
      {selectedUser && loggedInUser && (
        <div style={{
          position: 'fixed',
          bottom: '20px',
          right: '20px',
          border: '1px solid #ccc',
          padding: '10px',
          backgroundColor: 'white',
          boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)',
          width: '300px',
          height: '400px',
          overflow: 'hidden',
          display: 'flex',
          flexDirection: 'column',
        }}>
          <h3>Chat with {selectedUser.username}</h3>
          <p>Logged in as: {loggedInUser}</p>
          <div style={{
            flex: 1,
            overflowY: 'auto',
            border: '1px solid #ccc',
            marginBottom: '10px',
            padding: '5px',
            display: 'flex',
            flexDirection: 'column',
          }} ref = {messagesEndRef}>
            {messages.map((msg, index) => {
               console.log("Comparing:", msg.senderUsername, "with", loggedInUser, "Result:", msg.senderUsername === loggedInUser);
               return (
             <div 
             key={index} 
             style={{
               display: 'flex',
               justifyContent: msg.senderUsername === loggedInUser ? 'flex-start' : 'flex-end',
               margin: '10px 0',}}
               >
              <div
                style={{
                 maxWidth: '70%',
                  padding: '10px', 
                  borderRadius: '15px',
                  backgroundColor: msg.senderUsername === loggedInUser ? '#d1e7dd' : '#f8d7da',
                  color: msg.senderUsername  === loggedInUser ? '#0f5132' : '#842029',
                  boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
                  wordWrap: 'break-word', 
                  }}>

                {/*
                <strong>{ loggedInUser ? 'You' : selectedUser}</strong>
          */}
               
                <p style= {{ margin: '0' }}>{msg.content}</p>
                </div>
                </div>
               );
            })}
            </div>

          <textarea 
               rows="3"
               value={newMessage}
               onChange={(e) => setNewMessage(e.target.value)}
               placeholder="Type your message here..."
               style={{ marginBottom: '10px', width: '100%' }}
          />
          <button onClick={handleSendMessage} style={{ marginBottom: '10px' }}>
            Send  </button>
          <button onClick={closeChat}>Close Chat</button>
        </div>
      )}
    </div>
  );
};

export default UserList; 
