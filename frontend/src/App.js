import React, { useState, useEffect } from 'react';
import { loginUser, registerUser, reportIssue } from './services/api';
import LoginPage from './components/LoginPage';
import RegisterPage from './components/RegisterPage';
import DashboardPage from './components/DashboardPage';

export default function App() {
  const [page, setPage] = useState('login');
  const [token, setToken] = useState(null);
  const [userEmail, setUserEmail] = useState('');
  const [error, setError] = useState('');
  const [successMessage, setSuccessMessage] = useState('');

  useEffect(() => {
    const savedToken = localStorage.getItem('authToken');
    const savedEmail = localStorage.getItem('userEmail');
    if (savedToken && savedEmail) {
      setToken(savedToken);
      setUserEmail(savedEmail);
      setPage('dashboard');
    }
  }, []);

  const clearMessages = () => {
    setTimeout(() => {
      setError('');
      setSuccessMessage('');
    }, 5000);
  };

  const handleLogin = async (email, password) => {
    setError('');
    try {
      const data = await loginUser(email, password);
      setToken(data.token);
      setUserEmail(email);
      localStorage.setItem('authToken', data.token);
      localStorage.setItem('userEmail', email);
      setPage('dashboard');
    } catch (err) {
      setError(err.message);
      clearMessages();
    }
  };

  const handleRegister = async (name, email, password) => {
    setError('');
    try {
      await registerUser(name, email, password);
      setPage('login');
      setSuccessMessage('Registration successful! Please log in.');
      clearMessages();
    } catch (err) {
      setError(err.message);
      clearMessages();
    }
  };

  const handleLogout = () => {
    setToken(null);
    setUserEmail('');
    localStorage.removeItem('authToken');
    localStorage.removeItem('userEmail');
    setPage('login');
  };

  const handleReportIssue = async (issueData) => {
    setError('');
    setSuccessMessage('');
    try {
      await reportIssue(issueData, token);
      setSuccessMessage('Issue reported successfully!');
      clearMessages();
    } catch (err) {
      setError(err.message);
      clearMessages();
    }
  };

  const renderPage = () => {
    if (page === 'login') {
      return <LoginPage onLogin={handleLogin} onSwitchToRegister={() => setPage('register')} error={error} />;
    }
    if (page === 'register') {
      return <RegisterPage onRegister={handleRegister} onSwitchToLogin={() => setPage('login')} error={error} />;
    }
    if (page === 'dashboard') {
      return <DashboardPage onReportIssue={handleReportIssue} error={error} successMessage={successMessage} />;
    }
  };

  return (
    <div className="min-h-screen bg-slate-50 flex flex-col items-center pt-10 px-4">
      <header className="w-full max-w-6xl mx-auto mb-8">
        <nav className="bg-white shadow-md rounded-xl p-4 flex justify-between items-center">
          <h1 className="text-2xl font-bold text-blue-600">Civic Issue Tracker</h1>
          {token && (
            <div className="flex items-center space-x-4">
              <span className="text-gray-600 hidden sm:block">{userEmail}</span>
              <button onClick={handleLogout} className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded-lg">
                Logout
              </button>
            </div>
          )}
        </nav>
      </header>
      <main className="w-full flex-grow flex flex-col items-center justify-center">
        {renderPage()}
      </main>
    </div>
  );
}