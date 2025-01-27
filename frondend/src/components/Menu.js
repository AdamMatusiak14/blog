import React from "react";
import { Link } from "react-router-dom";

function Menu() {
  return (
    <div className="menu-container">
    <nav>
      <ul>
        <li><Link to="/addPost">Add Post</Link></li>
        <li><Link to="/listPosts">List of Post</Link></li>
        <li><Link to="/chat">Chat</Link></li>
        <li><Link to="/weather">Weather</Link></li>
      </ul>
    </nav>
   </div>
  );
}

export default Menu;
