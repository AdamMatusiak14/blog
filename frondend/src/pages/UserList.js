import React, { useEffect, useState } from 'react';
import token from '../components/token';
import {jwtDecode} from 'jwt-decode'; 

const UserList = () => {
  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [loggedInUser, setLoggedInUser] = useState("loding..."); 



  useEffect(() => {
    console.log("useEffect is running");
    const storedToken = sessionStorage.getItem("token");
    console.log("Stored token:", storedToken); // Check if the token is retrieved correctly
    if (storedToken) {
      try {
        // Decode the token to get user information
        const decodedToken = jwtDecode(storedToken);
        console.log("Decoded token:", decodedToken); // Check the structure of the decoded token
        setLoggedInUser(decodedToken.sub); // Adjust based on your token structure
      } catch (error) {
        console.error('Error decoding token:', error);
      }
    }


   token.get("/users")
      .then(response => setUsers(response.data))
      .catch(error => console.error("Błąd pobierania użytkowników:", error));
  }, []);

  const handleUserClick = (user) => 
{
  setSelectedUser(user);
};
  
const closeChat = () => {
  setSelectedUser(null);  };

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
      {selectedUser && (
        <div style={{
          position: 'fixed',
          bottom: '20px',
          right: '20px',
          border: '1px solid #ccc',
          padding: '10px',
          backgroundColor: 'white',
          boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)'
        }}>
          <h3>Chat with {selectedUser.username}</h3>
          <p>Logged in as: {loggedInUser.username}</p>
          <textarea 
            rows="5" 
            cols="30" 
            placeholder="Type your message here..."
          />
          <br />
          <button onClick={closeChat}>Close Chat</button>
        </div>
      )}
    </div>
  );
};

export default UserList;
