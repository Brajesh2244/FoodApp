/**
 * Food Item Redux slice.
 */
import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import foodService from "../../services/foodService";

// ==============================
// FETCH ALL FOODS
// ==============================

export const fetchAllFoods = createAsyncThunk(
  "foods/fetchAll",

  async (_, { rejectWithValue }) => {
    try {
      return await foodService.getAll();
    } catch (error) {
      return rejectWithValue(
        error.response?.data?.message ||
          error.message ||
          "Failed to fetch foods",
      );
    }
  },
);

// ==============================
// FETCH FOOD BY ID
// ==============================

export const fetchFoodById = createAsyncThunk(
  "foods/fetchById",

  async (id, { rejectWithValue }) => {
    try {
      return await foodService.getById(id);
    } catch (error) {
      return rejectWithValue(
        error.response?.data?.message ||
          error.message ||
          "Failed to fetch food",
      );
    }
  },
);

// ==============================
// FETCH FOODS BY RESTAURANT
// ==============================

export const fetchFoodsByRestaurant = createAsyncThunk(
  "foods/fetchByRestaurant",

  async (restaurantId, { rejectWithValue }) => {
    try {
      return await foodService.getByRestaurant(restaurantId);
    } catch (error) {
      return rejectWithValue(
        error.response?.data?.message ||
          error.message ||
          "Failed to fetch restaurant foods",
      );
    }
  },
);

// ==============================
// TRENDING FOODS
// Uses existing /api/foods
// instead of unavailable recommendations API
// ==============================

export const fetchTrendingFoods = createAsyncThunk(
  "foods/fetchTrending",

  async (limit = 10, { rejectWithValue }) => {
    try {
      const foods = await foodService.getAll();

      return Array.isArray(foods) ? foods.slice(0, limit) : [];
    } catch (error) {
      return rejectWithValue(
        error.response?.data?.message ||
          error.message ||
          "Failed to fetch foods",
      );
    }
  },
);

// ==============================
// SLICE
// ==============================

const foodSlice = createSlice({
  name: "foods",

  initialState: {
    foods: [],

    foodsByRestaurant: [],

    trendingFoods: [],

    selectedFood: null,

    loading: false,

    error: null,
  },

  reducers: {
    clearSelectedFood: (state) => {
      state.selectedFood = null;
    },

    clearFoodsByRestaurant: (state) => {
      state.foodsByRestaurant = [];
    },
  },

  extraReducers: (builder) => {
    builder

      // ==========================
      // FETCH ALL FOODS
      // ==========================

      .addCase(fetchAllFoods.pending, (state) => {
        state.loading = true;

        state.error = null;
      })

      .addCase(fetchAllFoods.fulfilled, (state, action) => {
        state.loading = false;

        state.foods = action.payload;
      })

      .addCase(fetchAllFoods.rejected, (state, action) => {
        state.loading = false;

        state.error = action.payload;
      })

      // ==========================
      // FETCH FOOD BY ID
      // ==========================

      .addCase(fetchFoodById.pending, (state) => {
        state.loading = true;
      })

      .addCase(fetchFoodById.fulfilled, (state, action) => {
        state.loading = false;

        state.selectedFood = action.payload;
      })

      .addCase(fetchFoodById.rejected, (state, action) => {
        state.loading = false;

        state.error = action.payload;
      })

      // ==========================
      // FETCH FOODS BY RESTAURANT
      // ==========================

      .addCase(fetchFoodsByRestaurant.pending, (state) => {
        state.loading = true;
      })

      .addCase(fetchFoodsByRestaurant.fulfilled, (state, action) => {
        state.loading = false;

        state.foodsByRestaurant = action.payload;
      })

      .addCase(fetchFoodsByRestaurant.rejected, (state, action) => {
        state.loading = false;

        state.error = action.payload;
      })

      // ==========================
      // TRENDING FOODS
      // ==========================

      .addCase(fetchTrendingFoods.pending, (state) => {
        state.loading = true;
      })

      .addCase(fetchTrendingFoods.fulfilled, (state, action) => {
        state.loading = false;

        state.trendingFoods = action.payload;
      })

      .addCase(fetchTrendingFoods.rejected, (state, action) => {
        state.loading = false;

        state.error = action.payload;
      });
  },
});

export const { clearSelectedFood, clearFoodsByRestaurant } = foodSlice.actions;

export default foodSlice.reducer;
