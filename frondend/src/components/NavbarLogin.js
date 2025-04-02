import React, { useState } from "react";
import {Link} from  "react-router-dom";
import "./css/NavbarLogin.css"




function NavbarLogin () 

{
  return (
    <nav className="navbarlogin">
      <button className="navbarlogin-button">
        <Link to="/login">Login</Link>
      </button>
    </nav>
  );
};





export default NavbarLogin;
