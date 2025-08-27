import React, { useState } from 'react';

export default function RegisterPage({ onRegister, onSwitchToLogin, error }) {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    onRegister(name, email, password);
  };

  return (
    <div className="w-full max-w-md">
      <form onSubmit={handleSubmit} className="bg-white shadow-lg rounded-xl px-8 pt-6 pb-8 mb-4">
        <h2 className="text-3xl font-bold text-center text-gray-800 mb-6">Create Account</h2>
        {error && <p className="bg-red-100 text-red-700 p-3 rounded-lg mb-4 text-center">{error}</p>}
        <div className="mb-4">
          <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="name">Full Name</label>
          <input
            className="shadow-sm appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500"
            id="name" type="text" placeholder="Jane Doe"
            value={name} onChange={(e) => setName(e.target.value)} required
          />
        </div>
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
            Register
          </button>
        </div>
        <p className="text-center text-gray-500 text-sm mt-6">
          Already have an account?{' '}
          <button type="button" onClick={onSwitchToLogin} className="font-bold text-blue-600 hover:text-blue-800">
            Login
          </button>
        </p>
      </form>
    </div>
  );
}