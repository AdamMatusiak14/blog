import React, {useState} from "react";
import axios from "axios";
import "../components/css/AddPost.css"
import token from "../components/token.js"

// To działa, ale w pierwszej lini wypchnij wszsytko na GitHub

function AddPost() {
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const like_count= 0;
    const dislike = 0; 


  const handleSubmit = async (e) => {
    e.preventDefault();

    const postData = { title, content, like_count, dislike };

    try {
      // Wysyłanie żądania POST za pomocą Axios
      const response = await token.post("/posts", postData);

      if (response.status) {
        alert("Post added successfully!");
        setTitle(""); // Resetowanie pola tytułu
        setContent(""); // Resetowanie pola treści
      }
    } catch (error) {
      console.error("Error:", error);
      alert("An error occurred while adding the post.");

      console.log(postData.title);
      console.log(postData.content);
    }
  };

  return (
    <div className="add-post-container">
      <h2>Add New Post</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>
            Title:
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              required
            />
          </label>
        </div>
        <div>
          <label>
            Content:
            <textarea
              value={content}
              onChange={(e) => setContent(e.target.value)}
              required
            ></textarea>
          </label>
        </div>
        <button type="submit">Add Post</button>
      </form>
    </div>
  );
}

export default AddPost;
