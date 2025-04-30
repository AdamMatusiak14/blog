import React, { useEffect, useState, useRef } from 'react';
import token from '../components/token';
import {jwtDecode} from 'jwt-decode'; 
import { Client } from '@stomp/stompjs'; 
import SockJS from 'sockjs-client';

const UserList = () => {
  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [loggedInUser, setLoggedInUser] = useState("loding..."); 
  const [messages, setMessages] = useState([]); // State to hold messages
  const [newMessage, setNewMessage] = useState(""); // State to hold new message input
  const stompClientRef = useRef(null); // Ref to hold the socket connection


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
          setMessages(prevMessages => [...prevMessages, receivedMessage]);
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
  

  const handleUserClick = (user) => 
{
  setSelectedUser(user);
  setMessages([]); // Clear messages when a new user is selected
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
        sender: loggedInUser,
        receiver: selectedUser.username,
        content: newMessage.trim(),
        //timestamp: new Date().toISOString() // but is in backend
      };

      stompClientRef.current.publish({
        destination: "/app/chat",
        body: JSON.stringify(message), 
            
      });
      setMessages(prevMessages => [...prevMessages, message]); // Add the sent message to the chat window
      setNewMessage(""); // Clear the input field after sending 
    } else{
      console.error("Stomp client is not connected or message is empty.");
    }  }; // Closing brace added here

  return (
    <div>
      <h2>Użytkownicy</h2>
      <ul>
        {users.map(u => (
          <li 
          key={u.id}
          onClick={()=> handleUserClick(u)}
          style={{cursor: 'pointer', color: 'blue'}}
          >
            {u.username}
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
          <p>Logged in as: {loggedInUser.username}</p>
          <div style={{
            flex: 1,
            overflowY: 'auto',
            border: '1px solid #ccc',
            marginBottom: '10px',
            padding: '5px',
          }}>
            {messages.map((msg, index) => (
              <div key={index} style={{ marginBottom: '5px' }}>
                <strong>{msg.sender === loggedInUser ? "You" : msg.sender}:</strong> {msg.content}
              </div>
            ))}
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
