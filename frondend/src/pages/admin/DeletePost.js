import React, { useEffect, useState } from "react";
import token from "../../components/token"; // Import token for authentication
import "../../components/css/DeletePost.css"; // Import CSS for styling

function DeletePost() {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
     token.get("admin/posts")
     // .then((res) => res.json())
      .then((data) =>{
        console.log("Odpowiedź z serwera:", data);
        setPosts(Array.isArray(data) ? data : data.data);
      });
         
  }, []);

  const handleDelete = (id) => {
    token.delete(`/admin/posts/${id}`)
     //.then((res) => res.json())
      .then(() => {
          setPosts(prevPosts => prevPosts.filter(post => post.id !== id));
      });
  };

  return (
    <div className="delete-post-container">
      <h2>Usuń post</h2>
      <ul className="delete-post-list">  
        {posts.map((post) => (
          <li key={post.id}>
            <span className="delete-post-title">{post.title}{" "}</span> 
            <button className="delete-post-btn" onClick={() => handleDelete(post.id)}>Usuń</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default DeletePost;