/**
 * Restaurant Redux slice.
 */
import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import restaurantService from "../../services/restaurantService";
import searchService from "../../services/searchService";

// ==============================
// FETCH ALL RESTAURANTS
// ==============================

export const fetchRestaurants = createAsyncThunk(
  "restaurants/fetchAll",
  async (_, { rejectWithValue }) => {
    try {
      return await restaurantService.getAll();
    } catch (error) {
      return rejectWithValue(
        error.response?.data?.message ||
          error.message ||
          "Failed to fetch restaurants",
      );
    }
  },
);

// ==============================
// FETCH RESTAURANT BY ID
// ==============================

export const fetchRestaurantById = createAsyncThunk(
  "restaurants/fetchById",
  async (id, { rejectWithValue }) => {
    try {
      return await restaurantService.getById(id);
    } catch (error) {
      return rejectWithValue(
        error.response?.data?.message ||
          error.message ||
          "Failed to fetch restaurant",
      );
    }
  },
);

// ==============================
// FETCH TOP RESTAURANTS
// Uses existing /api/restaurants
// instead of unavailable recommendations API
// ==============================

export const fetchTopRestaurants = createAsyncThunk(
  "restaurants/fetchTop",
  async (limit = 10, { rejectWithValue }) => {
    try {
      const restaurants = await restaurantService.getAll();

      return Array.isArray(restaurants) ? restaurants.slice(0, limit) : [];
    } catch (error) {
      return rejectWithValue(
        error.response?.data?.message ||
          error.message ||
          "Failed to fetch restaurants",
      );
    }
  },
);

// ==============================
// SEARCH RESTAURANTS
// ==============================

export const searchRestaurants = createAsyncThunk(
  "restaurants/search",
  async (params, { rejectWithValue }) => {
    try {
      return await searchService.searchRestaurants(params);
    } catch (error) {
      return rejectWithValue(
        error.response?.data?.message || error.message || "Search failed",
      );
    }
  },
);

// ==============================
// SLICE
// ==============================

const restaurantSlice = createSlice({
  name: "restaurants",

  initialState: {
    restaurants: [],
    topRestaurants: [],
    selectedRestaurant: null,
    searchResults: null,
    loading: false,
    error: null,
  },

  reducers: {
    clearSelectedRestaurant: (state) => {
      state.selectedRestaurant = null;
    },

    clearSearchResults: (state) => {
      state.searchResults = null;
    },
  },

  extraReducers: (builder) => {
    builder

      // ==========================
      // FETCH ALL
      // ==========================

      .addCase(fetchRestaurants.pending, (state) => {
        state.loading = true;
        state.error = null;
      })

      .addCase(fetchRestaurants.fulfilled, (state, action) => {
        state.loading = false;
        state.restaurants = action.payload;
      })

      .addCase(fetchRestaurants.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })

      // ==========================
      // FETCH BY ID
      // ==========================

      .addCase(fetchRestaurantById.pending, (state) => {
        state.loading = true;
        state.error = null;
      })

      .addCase(fetchRestaurantById.fulfilled, (state, action) => {
        state.loading = false;
        state.selectedRestaurant = action.payload;
      })

      .addCase(fetchRestaurantById.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })

      // ==========================
      // TOP RESTAURANTS
      // ==========================

      .addCase(fetchTopRestaurants.pending, (state) => {
        state.loading = true;
      })

      .addCase(fetchTopRestaurants.fulfilled, (state, action) => {
        state.loading = false;
        state.topRestaurants = action.payload;
      })

      .addCase(fetchTopRestaurants.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })

      // ==========================
      // SEARCH
      // ==========================

      .addCase(searchRestaurants.pending, (state) => {
        state.loading = true;
      })

      .addCase(searchRestaurants.fulfilled, (state, action) => {
        state.loading = false;
        state.searchResults = action.payload;
      })

      .addCase(searchRestaurants.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      });
  },
});

export const { clearSelectedRestaurant, clearSearchResults } =
  restaurantSlice.actions;

export default restaurantSlice.reducer;
