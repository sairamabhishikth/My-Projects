import React, { useState, useEffect } from "react";
import axios from "axios";

const Stocks = () => {
  const [stocks, setStocks] = useState([]);
  const token = localStorage.getItem("authToken"); // ✅ Check if user is logged in

  useEffect(() => {
    axios
      .get("http://localhost:8080/stocks")
      .then((response) => {
        console.log("Stock API Response:", response.data);
        setStocks(response.data);
      })
      .catch((error) => console.error("Error fetching stocks:", error));
  }, []);

  return (
    <div>
      <h2>Stock Market</h2>
      <table border="1">
        <thead>
          <tr>
            <th>Symbol</th>
            <th>Name</th>
            <th>Current Price</th>
            <th>Market Cap</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {stocks.map((stock) => (
            <tr key={stock.stockId}>
              <td>{stock.symbol}</td>
              <td>{stock.name}</td>
              <td>${stock.currentPrice ? stock.currentPrice.toFixed(2) : "N/A"}</td>
              <td>${stock.marketCap ? stock.marketCap.toLocaleString() : "N/A"}</td>
              <td>
                <button disabled={!token}>Buy</button>
                <button disabled={!token}>Sell</button>
              </td> 
            </tr>
          ))}
        </tbody>
      </table>
      {!token && <p style={{ color: "red" }}>🔒 Please login to trade stocks.</p>} {/* ✅ Show message if not logged in */}
    </div>
  );
};

export default Stocks;
