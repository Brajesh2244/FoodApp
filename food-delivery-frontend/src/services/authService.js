import api from "../api/axios";

/**
 * Authentication API Service
 */

// Register new user
export const registerUser = async (userData) => {
  const response = await api.post("/api/auth/register", userData);
  return response.data;
};

// Login user
export const loginUser = async (loginData) => {
  const response = await api.post("/api/auth/login", loginData);
  return response.data;
};

// Get current logged-in user
export const getCurrentUser = async () => {
  const response = await api.get("/api/auth/profile"); // Assuming /api/auth/profile based on usual patterns, or we check backend
  return response.data;
};

export default {
  registerUser,
  loginUser,
  getCurrentUser,
};
