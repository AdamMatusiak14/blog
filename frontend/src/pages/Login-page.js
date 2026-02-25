import React, { useState } from "react";
import axios from "axios";
import "../components/css/AuthForms.css"; // Import stylów

function LoginPage() {
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    const data = { login, password };
  

    try {
      const response = await axios.post("http://localhost:8080/api/auth/login", {
        username: data.login,
        password: data.password, 
      }, {
    
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        withCredentials: true, 
       // body: JSON.stringify(data),
  
      });

        const token = response.data.token;
          sessionStorage.setItem("token", token); // Przechowaj token w sessionStorage
          console.log("Token:", token);
          window.location.href = "/"; // Przekierowanie po udanym logowaniu
        } 
      catch (error)  {
        console.error("Błąd logowania:", error);
      } finally {
        setLoading(false);
      }
    }
  
      // .then(response => {
      //   console.log("Odpowiedź serwera:", response);
      // });

      // if (!response.ok) {
      //   throw new Error("Błąd logowania");
      // }

    //   const result = await response.json();
    //   console.log(result); // Odpowiedź z API
    //   // Zalogowanie udane - np. przekierowanie
    // } catch (error) {
    //   setError(error.message);
    // } finally {
    //   setLoading(false);
    // }
  

  return (
    <div className="auth-form-container">
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Login:</label>
          <input
            type="text"
            value={login}
            onChange={(e) => setLogin(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Hasło:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit" disabled={loading}>
          {loading ? "Ładowanie..." : "Zaloguj"}
        </button>
      </form>
    </div>
  );
}

export default LoginPage;
