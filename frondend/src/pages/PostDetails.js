import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import "../components/css/PostDetails.css";
import token from "../components/token";

const PostDetails = () => {
    const { id } = useParams();
    const [post, setPost] = useState(null);
    const [totalVotes, setTotalVotes] = useState(0);

    useEffect(() => {
        token.get(`/posts/${id}`)
            .then(response => setPost(response.data))
            .catch(error => console.error("Błąd pobierania posta:", error));
    }, [id]);
// Jest adres ale nie ma tokena

    const handleLike = () => {
        token.post(`/posts/${id}/like`)
            .then(response => setPost(response.data))
            .catch(error => console.error("Błąd podczas like:", error));
    };

    const handleDislike = () => {
        token.post(`/posts/${id}/dislike`)
            .then(response => setPost(response.data))
            .catch(error => console.error("Błąd podczas dislike:", error));
    };


    if (!post) return <p>Loading...</p>;

    return (
        <div className="post-details">
            <h2>{post.title}</h2>
            <p>{post.content}</p>
            <div className="vote-counters">
                <p className="like-counter">Likes:{post.likesCount}</p>
                <p className="dislike-counter">Dislike: {post.dislike}</p>
            </div>
            <div className="buttons">
                <button className="like-btn" onClick={handleLike}>👍 Like</button>
                <button className="dislike-btn" onClick={handleDislike}>👎 Dislike</button>
             </div>
        </div>
    );
};

export default PostDetails;
