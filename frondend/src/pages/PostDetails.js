import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import "../components/css/PostDetails.css";

const PostDetails = () => {
    const { id } = useParams();
    const [post, setPost] = useState(null);

    useEffect(() => {
        axios.get(`http://localhost:8080/api/posts/${id}`)
            .then(response => setPost(response.data))
            .catch(error => console.error("Błąd pobierania posta:", error));
    }, [id]);


    const handleLike = () => {
        axios.post(`http://localhost:8080/api/posts/${id}/like`)
            .then(response => setPost(response.data))
            .catch(error => console.error("Błąd podczas like:", error));
    };

    const handleDislike = () => {
        axios.post(`http://localhost:8080/api/posts/${id}/dislike`)
            .then(response => setPost(response.data))
            .catch(error => console.error("Błąd podczas dislike:", error));
    };


    if (!post) return <p>Loading...</p>;

    return (
        <div className="post-details">
            <h2>{post.title}</h2>
            <p>{post.content}</p>
            <div className="buttons">
                <button className="like-btn" onClick={handleLike}>👍 Like</button>
                <button className="dislike-btn" onClick={handleDislike}>👎 Dislike</button>
             </div>
        </div>
    );
};

export default PostDetails;
