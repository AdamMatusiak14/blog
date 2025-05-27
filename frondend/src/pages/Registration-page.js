import React, { useState } from "react";
import token from "../components/token.js";
import axios from "axios";

const RegistrationPage = () => {
  const [form, setForm] = useState({ username: "", password: "" });
  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");
    try {
        const postUser = {username: form.username, password: form.password};
        
      const response =  await axios.post("http://localhost:8080/api/users/create", postUser);
      console.log("This is response:", response);
      if (response.status == 200 || response.status == 201) {
       try{
        const loginResponse = await axios.post("http://localhost:8080/api/auth/login", postUser);
        console.log("This is loginResponse:", loginResponse);
       if(loginResponse.status == 200 || loginResponse.status == 201){
        const token = loginResponse.data.token;
        sessionStorage.setItem("token", token); // Przechowaj token w sessionStorage
        setMessage("Rejestracja zakończona sukcesem. Zalogowano.");
        setForm({ username: "", password: "" });
        window.location.href = "/"; 
       } else {
            setMessage("Rejestracja OK, ale logowanie nie powiodło się.");
          }
        } catch (loginError) {
          setMessage("Rejestracja OK, ale błąd logowania.");
        }
      } else {
        setMessage("Błąd rejestracji.");
      }
    } catch (error) {
      setMessage("Błąd połączenia z serwerem.");
    }
  };

  return (
    <div>
      <h2>Rejestracja</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Nazwa użytkownika:</label>
          <input
            type="text"
            name="username"
            value={form.username}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label>Hasło:</label>
          <input
            type="password"
            name="password"
            value={form.password}
            onChange={handleChange}
            required
          />
        </div>
        <button type="submit">Zarejestruj</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
};

export default RegistrationPage;