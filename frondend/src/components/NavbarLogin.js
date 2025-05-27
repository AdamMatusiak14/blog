import React, { useState } from "react";
import {Link} from  "react-router-dom";
import "./css/NavbarLogin.css"
import { useNavigate } from "react-router-dom";


// Pasek logowania

function NavbarLogin () 

{
  const navigate = useNavigate();
  return (
    <nav className="navbarlogin">
      <div className="navbarlogin-buttons">
     <button onClick={() => navigate("/login")}>Login</button>
     <button onClick={() => navigate("/register")}>Register</button>
     </div>
    </nav>
  );
};





export default NavbarLogin;
