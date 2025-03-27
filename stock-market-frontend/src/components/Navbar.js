import React from "react";
import { Link, useNavigate } from "react-router-dom";

const Navbar = () => {
  const navigate = useNavigate();
  const token = localStorage.getItem("authToken"); // Check if user is logged in

  const handleLogout = () => {
    localStorage.removeItem("authToken"); // Remove token
    navigate("/login"); // Redirect to login page
  };

  return (
    <nav style={{ display: "flex", justifyContent: "space-between", padding: "10px", borderBottom: "2px solid black" }}>
      <div>
        <Link to="/" style={{ marginRight: "15px" }}>Stocks</Link>
        {token && <Link to="/portfolio" style={{ marginRight: "15px" }}>Portfolio</Link>}
        {token && <Link to="/transactions" style={{ marginRight: "15px" }}>Transactions</Link>}
      </div>
      <div>
        {!token ? (
          <>
            <Link to="/login" style={{ marginRight: "15px" }}>Login</Link>
            <Link to="/register">Register</Link>
          </>
        ) : (
          <button onClick={handleLogout} style={{ cursor: "pointer", padding: "5px 10px", background: "red", color: "white", border: "none" }}>
            Logout
          </button>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
