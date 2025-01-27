import logo from './logo.svg';
import './App.css';
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Menu from "./components/Menu";
import "./components/css/Menu.css"
import AddPost from "./pages/AddPost";
import PostList from "./pages/PostList";
import Chat from "./pages/Chat";
import Weather from "./pages/Weather";



function App() {
  return (
    <Router>

        <Routes>
          <Route path="/" element={<Menu/>} />
          <Route path="/addPost" element={<AddPost />} />
          <Route path="/listPosts" element={<PostList />} />
          <Route path="/chat" element={<Chat />} />
          <Route path="/weather" element={<Weather />} />
        </Routes>
    </Router>
  );
}

export default App;
