import React, { useState } from 'react';

export default function LoginPage({ onLogin, onSwitchToRegister, error }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    onLogin(email, password);
  };

  return (
    <div className="w-full max-w-md">
      <form onSubmit={handleSubmit} className="bg-white shadow-lg rounded-xl px-8 pt-6 pb-8 mb-4">
        <h2 className="text-3xl font-bold text-center text-gray-800 mb-6">Login</h2>
        {error && <p className="bg-red-100 text-red-700 p-3 rounded-lg mb-4 text-center">{error}</p>}
        <div className="mb-4">
          <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="email">Email</label>
          <input
            className="shadow-sm appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500"
            id="email" type="email" placeholder="you@example.com"
            value={email} onChange={(e) => setEmail(e.target.value)} required
          />
        </div>
        <div className="mb-6">
          <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="password">Password</label>
          <input
            className="shadow-sm appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 mb-3 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500"
            id="password" type="password" placeholder="******************"
            value={password} onChange={(e) => setPassword(e.target.value)} required
          />
        </div>
        <div className="flex items-center justify-between">
          <button className="bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-6 rounded-lg focus:outline-none focus:shadow-outline w-full" type="submit">
            Sign In
          </button>
        </div>
        <p className="text-center text-gray-500 text-sm mt-6">
          Don't have an account?{' '}
          <button type="button" onClick={onSwitchToRegister} className="font-bold text-blue-600 hover:text-blue-800">
            Register
          </button>
        </p>
      </form>
    </div>
  );
}