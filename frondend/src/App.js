
import './App.css';
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Menu from "./components/Menu";
import "./components/css/Menu.css"
import AddPost from "./pages/AddPost";
import PostList from "./pages/PostList";
import ChatWindow from "./pages/ChatWindow";
import Weather from "./pages/Weather";
import PostDetails from "./pages/PostDetails";
import LoginPage from "./pages/Login-page";
import RegisterPage from "./pages/Registration-page";
import token from './components/token';
import UserList from "./pages/UserList";
import AdminPanel from "./pages/AdminPanel";
import DeletePost from './pages/admin/DeletePost';



function App() {
  return (
   
    <Router>
        <Routes>
          <Route path="/" element={<Menu/>} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/addPost" element={<AddPost />} />
          <Route path="/listPosts" element={<PostList />} />
          <Route path="post/:id" element={<PostDetails />} />
          <Route path="/chat" element={<ChatWindow />} />
          <Route path="/weather" element={<Weather />} />
          <Route path="/users" element={<UserList />} />
          <Route path="/admin" element={<AdminPanel />} />
          <Route path="/admin/posts" element={<DeletePost />} />
    
        </Routes>
    </Router>
  );
}

export default App;
