import React, {useState} from "react";
import axios from "axios";
import "../components/css/AddPost.css"



function AddPost() {
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");


  const handleSubmit = async (e) => {
    e.preventDefault();

    const postData = { title, content };

    try {
      // Wysyłanie żądania POST za pomocą Axios
      const response = await axios.post("http://localhost:3000/api/posts", postData);

      if (response.status === 200) {
        alert("Post added successfully!");
        setTitle(""); // Resetowanie pola tytułu
        setContent(""); // Resetowanie pola treści
      }
    } catch (error) {
      console.error("Error:", error);
      alert("An error occurred while adding the post.");
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
