const USER_SERVICE_URL = 'http://localhost:8080';
const ISSUE_SERVICE_URL = 'http://localhost:8081';

// Function to handle user login
export const loginUser = async (email, password) => {
  const response = await fetch(`${USER_SERVICE_URL}/api/v1/auth/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password }),
  });

  if (!response.ok) {
    throw new Error('Invalid email or password.');
  }
  return response.json();
};

// Function to handle user registration
export const registerUser = async (name, email, password) => {
  const response = await fetch(`${USER_SERVICE_URL}/api/v1/auth/register`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name, email, password }),
  });

  if (!response.ok) {
    throw new Error('Registration failed. The email might already be in use.');
  }
  return response.json();
};

// Function to report a new issue, requiring a JWT token for authorization
export const reportIssue = async (issueData, token) => {
  const response = await fetch(`${ISSUE_SERVICE_URL}/api/v1/issues`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`, // Here we send the token
    },
    body: JSON.stringify(issueData),
  });

  if (!response.ok) {
    throw new Error('Failed to report issue. Please try again.');
  }
  return response.json();
};