
import './App.css';
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Menu from "./components/Menu";
import "./components/css/Menu.css"
import AddPost from "./pages/AddPost";
import PostList from "./pages/PostList";
import Chat from "./pages/Chat";
import Weather from "./pages/Weather";
import PostDetails from "./pages/PostDetails";
import LoginPage from "./pages/Login-page";
import token from './components/token';



function App() {
  return (
   
    <Router>
        <Routes>
          <Route path="/" element={<Menu/>} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/addPost" element={<AddPost />} />
          <Route path="/listPosts" element={<PostList />} />
          <Route path="post/:id" element={<PostDetails />} />
          <Route path="/chat" element={<Chat />} />
          <Route path="/weather" element={<Weather />} />
        </Routes>
    </Router>
  );
}

export default App;
