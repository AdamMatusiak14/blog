import React, { useEffect, useState } from "react";
import token from "../../components/token"; // Import token for authentication

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
    <div>
      <h2>Usuń post</h2>
      <ul>
        {posts.map((post) => (
          <li key={post.id}>
            {post.title}{" "}
            <button onClick={() => handleDelete(post.id)}>Usuń</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default DeletePost;