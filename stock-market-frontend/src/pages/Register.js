import React, { useState } from "react";
import axios from "axios";

const RegisterForm = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [netWorth, setNetWorth] = useState("");
  const [message, setMessage] = useState("");

  const handleRegister = async (event) => {
    event.preventDefault();

    try {
      // Send registration data to the backend
      const response = await axios.post("http://localhost:8080/auth/register", {
        username,
        password,
        netWorth,
      });

      if (response.status === 200) {
        setMessage("Registration successful! You can now log in.");
      } else {
        setMessage("Registration failed. Please try again.");
      }
    } catch (error) {
      setMessage(
        error.response?.data?.error || "An error occurred during registration."
      );
    }
  };

  return (
    <div>
      <h2>Register</h2>
      <form onSubmit={handleRegister}>
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
        <div>
          <label>Net Worth</label>
          <input
            type="number"
            value={netWorth}
            onChange={(e) => setNetWorth(e.target.value)}
            required
          />
        </div>
        <button type="submit">Register</button>
      </form>
      {message && <div>{message}</div>}
    </div>
  );
};

export default RegisterForm;
