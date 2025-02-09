import React from "react";
import ReactDOM from "react-dom/client"; // ✅ Use createRoot from React 18
import App from "./App";

const root = ReactDOM.createRoot(document.getElementById("root")); // ✅ Fix for React 18
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
