import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";

import {
  loginUser,
  registerUser,
  getCurrentUser,
} from "../../services/authService";

import {
  setToken,
  setStoredUser,
  clearAuthStorage,
  getStoredUser,
} from "../../utils/storage";

/* ==============================
   LOGIN
================================ */

export const login = createAsyncThunk(
  "auth/login",

  async (loginData, { rejectWithValue }) => {
    try {
      const response = await loginUser(loginData);

      if (response.token) {
        setToken(response.token);
      }

      const user = {
        email: response.email,
        role: response.role,
      };

      setStoredUser(user);

      return {
        ...response,
        user,
      };
    } catch (error) {
      return rejectWithValue(
        error.response?.data?.message || error.response?.data || "Login failed",
      );
    }
  },
);

/* ==============================
   REGISTER
================================ */

export const register = createAsyncThunk(
  "auth/register",

  async (registerData, { rejectWithValue }) => {
    try {
      const response = await registerUser(registerData);

      // Backend now returns JWT token after registration
      if (response.token) {
        setToken(response.token);
      }

      const user = {
        email: response.email,
        role: response.role,
      };

      setStoredUser(user);

      return {
        ...response,
        user,
      };
    } catch (error) {
      return rejectWithValue(
        error.response?.data?.message ||
          error.response?.data ||
          "Registration failed",
      );
    }
  },
);

/* ==============================
   GET CURRENT USER
================================ */

export const fetchCurrentUser = createAsyncThunk(
  "auth/currentUser",

  async (_, { rejectWithValue }) => {
    try {
      const response = await getCurrentUser();

      setStoredUser(response);

      return response;
    } catch (error) {
      clearAuthStorage();

      return rejectWithValue(
        error.response?.data?.message || "Failed to get user profile",
      );
    }
  },
);

/* ==============================
   INITIAL STATE
================================ */

const storedUser = getStoredUser();

const initialState = {
  user: storedUser,

  isAuthenticated: !!storedUser,

  loading: false,

  error: null,
};

/* ==============================
   SLICE
================================ */

const authSlice = createSlice({
  name: "auth",

  initialState,

  reducers: {
    logout: (state) => {
      clearAuthStorage();

      state.user = null;

      state.isAuthenticated = false;

      state.error = null;
    },

    clearError: (state) => {
      state.error = null;
    },
  },

  extraReducers: (builder) => {
    /* LOGIN */

    builder

      .addCase(login.pending, (state) => {
        state.loading = true;

        state.error = null;
      })

      .addCase(login.fulfilled, (state, action) => {
        state.loading = false;

        state.user = action.payload.user;

        state.isAuthenticated = true;

        state.error = null;
      })

      .addCase(login.rejected, (state, action) => {
        state.loading = false;

        state.error = action.payload;

        state.isAuthenticated = false;
      });

    /* REGISTER */

    builder

      .addCase(register.pending, (state) => {
        state.loading = true;

        state.error = null;
      })

      .addCase(register.fulfilled, (state, action) => {
        state.loading = false;

        state.user = action.payload.user;

        state.isAuthenticated = true;

        state.error = null;
      })

      .addCase(register.rejected, (state, action) => {
        state.loading = false;

        state.error = action.payload;

        state.isAuthenticated = false;
      });

    /* CURRENT USER */

    builder

      .addCase(fetchCurrentUser.pending, (state) => {
        state.loading = true;
      })

      .addCase(fetchCurrentUser.fulfilled, (state, action) => {
        state.loading = false;

        state.user = action.payload;

        state.isAuthenticated = true;
      })

      .addCase(fetchCurrentUser.rejected, (state, action) => {
        state.loading = false;

        state.error = action.payload;

        state.user = null;

        state.isAuthenticated = false;
      });
  },
});

export const { logout, clearError } = authSlice.actions;

export default authSlice.reducer;
