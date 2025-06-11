import React from "react";
import { Link } from "react-router-dom";
import "../components/css//AdminPanel.css";

function AdminPanel() {
  return (
    <div className="admin-container">
      <h2>Panel administratora</h2>
      <ul>
        <li>
          <Link className="admin-link-block"  to="/admin/posts">Usuwanie Postów</Link>
        </li>
        <li>
          <Link className="admin-link-block"  to="/admin/users">Usuwanie Użytkowników</Link>
        </li>
      </ul>
    </div>
  );
}

export default AdminPanel;