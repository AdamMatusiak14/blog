import React, { useEffect, useState } from "react";
import token from "../../components/token"; // Import token for authentication
import "../../components/css/DeleteUser.css"; // Import CSS for styling

function DeleteUsers() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);

  // Pobierz użytkowników z backendu
  useEffect(() => {
    token.get("admin/users")
      .then((data) => {
        setUsers(data);
        console.log("Odpowiedź z serwera:", data);
        setUsers(Array.isArray(data) ? data : data.data);
        setLoading(false);
      })
      .catch(() => setLoading(false)); 
  }, []);

  // Usuwanie użytkownika
  const handleDelete = (id) => {
    token.delete(`admin/users/${id}`)
      .then(() => {
        setUsers(prevUsers => prevUsers.filter(user => user.id !== id));
      });
  };

  if (loading) return <div>Ładowanie użytkowników...</div>;

  return (
    <div className="delete-users-container">
      <h2>Usuń użytkowników</h2>
      <ul className = "delete-users-list">
        {users.map((user) => (
          <li key={user.id}>
           < span className="delete-users-username" >{user.username}</span>
           <button
           className="delete-users-btn" 
              onClick={() => handleDelete(user.id)}
            >
              Usuń
            </button>
          </li>
        ))}
      </ul>
      {users.length === 0 && <div>Brak użytkowników do wyświetlenia.</div>}
    </div>
  );
}

export default DeleteUsers;