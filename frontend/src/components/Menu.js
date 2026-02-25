import React from "react";
import { Link } from "react-router-dom";
import NavbarLogin from "./NavbarLogin";
import token from "./token"; // Import token for authentication
import {jwtDecode} from "jwt-decode"; // Import jwt-decode for decoding JWT tokens

function Menu() {

let isAuthenticated = false; 
const tokenValue = sessionStorage.getItem("token");
if (tokenValue) {
  try{
    const decodedToken = jwtDecode(tokenValue);
    isAuthenticated = decodedToken.role === "ROLE_ADMIN";
    console.log("isAdmin", isAuthenticated);
    console.log("decodedToken", decodedToken);
  } catch (error) {
    isAuthenticated = false; // If there's an error decoding the token, treat as unauthenticated
  }
}

  return (
    
    <div className="menu-container">
      <NavbarLogin />
    <nav>
      <ul>
        <li><Link to="/addPost">Add Post</Link></li>
        <li><Link to="/listPosts">List of Post</Link></li>
        <li><Link to="/users">Chat</Link></li>
        <li><Link to="/weather">Weather</Link></li>
        {isAuthenticated && <li><Link to="/admin">Admin Panel</Link></li>}
      </ul>
    </nav>
   </div>
  );
}

export default Menu;
