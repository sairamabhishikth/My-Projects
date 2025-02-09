import React, { useEffect, useState } from "react";
import axios from "axios";

const Portfolio = () => {
  const [portfolio, setPortfolio] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const token = localStorage.getItem("authToken");

  useEffect(() => {
    if (!token) {
      setError("❌ Unauthorized: Please log in.");
      setLoading(false);
      return;
    }

    console.log("🔹 Fetching portfolio data...");

    axios
      .get("http://localhost:8080/portfolios", {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => {
        console.log("✅ Portfolio data received:", response.data);
        setPortfolio(response.data);
        setLoading(false);
      })
      .catch((error) => {
        console.error("❌ Error fetching portfolio:", error.response?.data);
        setError(
          error.response?.data?.error || "❌ Failed to load portfolio. Please try again."
        );
        setLoading(false);
      });
  }, [token]);

  return (
    <div>
      <h2>My Portfolio</h2>

      {loading ? (
        <p>Loading...</p>
      ) : error ? (
        <p style={{ color: "red" }}>{error}</p>
      ) : portfolio.length === 0 ? (
        <p>No stocks in portfolio</p>
      ) : (
        <table border="1">
          <thead>
            <tr>
              <th>Portfolio ID</th>
              <th>User ID</th>
              <th>Stock ID</th>
              <th>Stock Symbol</th>
              <th>Stock Name</th>
              <th>Quantity</th>
              <th>Avg Purchase Price</th>
              <th>Latest Price</th>
              <th>Total Value</th>
            </tr>
          </thead>
          <tbody>
            {portfolio.map((item) => (
              <tr key={item.portfolioId}>
                <td>{item.portfolioId}</td>
                <td>{item.user?.userId || "N/A"}</td>
                <td>{item.stock?.stockId || "N/A"}</td>
                <td>{item.stock?.symbol || "N/A"}</td>
                <td>{item.stock?.name || "N/A"}</td>
                <td>{item.quantity || 0}</td>
                <td>${item.averagePurchasePrice ? item.averagePurchasePrice.toFixed(2) : "0.00"}</td>
                <td>${item.latestPurchasePrice ? item.latestPurchasePrice.toFixed(2) : "0.00"}</td>
                <td>
                  $
                  {item.quantity && item.latestPurchasePrice
                    ? (item.quantity * item.latestPurchasePrice).toFixed(2)
                    : "0.00"}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default Portfolio;
