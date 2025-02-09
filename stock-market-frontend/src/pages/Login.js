import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const LoginForm = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleLogin = async (event) => {
    event.preventDefault();
    setLoading(true);
    setMessage("");

    // Prevent empty username/password submission
    if (!username.trim() || !password.trim()) {
      setMessage("❌ Username and password are required.");
      setLoading(false);
      return;
    }

    try {
      console.log("🔹 Sending login request:", { username, password });

      const response = await axios.post("http://localhost:8080/auth/login", {
        username,
        password,
      });

      const token = response.data; // The backend returns the token as a string
      console.log("✅ Received Token:", token);

      if (token) {
        localStorage.setItem("authToken", token);
        console.log("✅ Token stored in localStorage:", localStorage.getItem("authToken"));
        setMessage("✅ Login successful!");
        navigate("/portfolio"); // Redirect to Portfolio page
      } else {
        setMessage("❌ Failed to log in. Please try again.");
      }
    } catch (error) {
      console.error("❌ Login error:", error.response?.data || error.message);
      setMessage(error.response?.data?.error || "❌ Invalid username or password.");
    }

    setLoading(false);
  };

  return (
    <div>
      <h2>Login</h2>
      {message && <p style={{ color: message.includes("✅") ? "green" : "red" }}>{message}</p>}

      <form onSubmit={handleLogin}>
        <div>
          <label>Username</label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Password</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit" disabled={loading}>
          {loading ? "Logging in..." : "Login"}
        </button>
      </form>
    </div>
  );
};

export default LoginForm;
