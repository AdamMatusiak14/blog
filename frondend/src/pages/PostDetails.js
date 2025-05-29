import React, { use, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import "../components/css/PostDetails.css";
import token from "../components/token";

const PostDetails = () => {
    const { id } = useParams();
    const [post, setPost] = useState(null);
    const [totalVotes, setTotalVotes] = useState(0);
    const [commentContent, setCommentContent] = useState("");
    const [error, setError] = useState("");



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


    const handleAddComment = (e) => {
        e.preventDefault();
        if (!commentContent.trim()) return;

        token.post(`/posts/${id}/comments`, { content: commentContent })
            .then(response => {
                console.log("Komentarz dodany:", response.data);
                setPost(prev => ({
                    ...prev,
                    comments: [...prev.comments, response.data]
                }));
                setCommentContent("");
                setError("");
            })
            .catch(() => setError("Błąd podczas dodawania komentarza."));
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

              {/* Sekcja komentarzy */}
            <div className="comments-section">
                <h3>Komentarze</h3>
                {post.comments && post.comments.length > 0 ? (
                    <ul>
                        {post.comments.map(comment => (
                            <li key={comment.id}>
                                {comment.content}
                                {/* Możesz dodać np. autora: {comment.author?.username} */}
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>Brak komentarzy.</p>
                )}

                {/* Formularz dodawania komentarza */}
                <form onSubmit={handleAddComment} className="add-comment-form">
                    <textarea
                        value={commentContent}
                        onChange={e => setCommentContent(e.target.value)}
                        placeholder="Dodaj komentarz..."
                        rows={3}
                        required
                    />
                    <button type="submit">Dodaj komentarz</button>
                    {error && <p className="error">{error}</p>}
                </form>
            </div>
        </div>
    );
};

export default PostDetails;
