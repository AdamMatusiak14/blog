import React, { useState } from "react";
import {Link} from  "react-router-dom";
import "./css/NavbarLogin.css"
import { useNavigate } from "react-router-dom";


// Pasek logowania

function NavbarLogin () 

{
  const navigate = useNavigate();
  const isLoggedIn = !!sessionStorage.getItem("token");;

   const handleLogout = () => {
    sessionStorage.removeItem("token");
    navigate("/");
  };

  return (
    <nav className="navbarlogin">
      <div className="navbarlogin-buttons">
        {isLoggedIn ? (
          <button onClick={handleLogout}>Logout</button>
        ) : (
          <>
          <button onClick={() => navigate("/login")}>Login</button>
          <button onClick={() => navigate("/register")}>Register</button>
         </>
        )}
     
     </div>
    </nav>
  );
};





export default NavbarLogin;
