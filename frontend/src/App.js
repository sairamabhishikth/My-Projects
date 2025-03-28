import React, { useState, useEffect } from 'react';
import MaintenancePage from './MaintenancePage'; // Import your MaintenancePage component
import NotFoundPage from './NotFoundPage'; // Import your NotFoundPage component
import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Updated import for React Router v6

const App = () => {
  const [isMaintenance, setIsMaintenance] = useState(false);

  // Fetch maintenance status when the app starts
  useEffect(() => {
    fetch('http://localhost:8080/api/maintenance-status')
      .then(response => response.json())
      .then(data => setIsMaintenance(data.isMaintenance)) // Assuming the response is a boolean indicating maintenance mode
      .catch(error => console.log('Error fetching maintenance status:', error));
  }, []);

  return (
    <Router>
      <div className="App">
        {isMaintenance ? (
          // Show maintenance page if maintenance is active
          <MaintenancePage />
        ) : (
          <Routes>
            {/* Show MaintenancePage when not in maintenance mode */}
            <Route path="/" element={<MaintenancePage />} />
            
            {/* Catch-all route for 404 errors */}
            <Route path="*" element={<NotFoundPage />} />
          </Routes>
        )}
      </div>
    </Router>
  );
};

export default App;
