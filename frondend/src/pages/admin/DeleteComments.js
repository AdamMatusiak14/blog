import React, { useEffect, useState } from "react";
import token from "../../components/token";
import "../../components/css/DeleteComments.css";

function DeleteComments() {
  const [comments, setComments] = useState([]);
  const [search, setSearch] = useState("");

  useEffect(() => {
    token.get("admin/comments")
      .then((data) => {
      console.log("Odpowiedź z serwera:", data);
      setComments(Array.isArray(data) ? data : data.data);
    });
  }, []);


  const handleDelete = (id) => {
    token.delete(`admin/comments/${id}`)
      .then(() => {
        setComments((prev) => prev.filter((comment) => comment.id !== id));
      })
      .catch((err) => {
        console.error("Błąd podczas usuwania komentarza:", err);
      });
  };
  
   const filteredComments = comments.filter(
    (comment) =>
      comment.content.toLowerCase().includes(search.toLowerCase()) ||
      comment.author.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="delete-comments-container">
      <input
        type="text"
        placeholder="Wyszukaj komentarz..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        className="delete-comments-search"
        style={{ marginBottom: "16px", padding: "8px", width: "100%" }}
      />
      <h2>Komentarze</h2>
      <ul className="delete-comments-list">
        {filteredComments.map((comment) => (
          <li key={comment.id}>
            <span className="delete-comments-content">
            <strong>{comment.author}:</strong> <br /> {comment.content}
            </span>
             <button 
             className="delete-comments-btn"
             onClick={() => handleDelete(comment.id)}>
              Usuń
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default DeleteComments;