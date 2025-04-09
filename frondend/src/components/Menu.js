import React from "react";
import { Link } from "react-router-dom";
import NavbarLogin from "./NavbarLogin";
import token from "./token"; // Import token for authentication

function Menu() {
  return (
    
    <div className="menu-container">
      <NavbarLogin />
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
