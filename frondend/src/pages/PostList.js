import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import "../components/css/PostList.css"

const PostList = () => {
    const [posts, setPosts] = useState([]);

    useEffect(() => {
        axios.get("http://localhost:8080/api/posts")
            .then(response => setPosts(response.data))
            .catch(error => console.error("Błąd pobierania postów:", error));
    }, []);

    return (
        <div className="post-list">
            <h2>Lista postów</h2>
            <ul>
                {posts.map(post => (
                    <li key={post.id} className="post-item">
                        <Link to={`/post/${post.id}`} className="post-title">{post.title}</Link>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default PostList;
