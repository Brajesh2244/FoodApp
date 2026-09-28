/**
 * Application-wide constants.
 */

// API base — Vite proxy forwards /api → localhost:8081
export const API_BASE_URL = import.meta.env.VITE_API_URL || "";

// Stripe
export const STRIPE_PUBLIC_KEY = import.meta.env.VITE_STRIPE_PUBLIC_KEY || "";

// User roles — match backend roles exactly
export const ROLES = {
  CUSTOMER: "CUSTOMER",
  ADMIN: "ADMIN",
  RESTAURANT_OWNER: "RESTAURANT_OWNER",
};

// Food categories
export const CATEGORIES = [
  { value: "PIZZA", label: "Pizza", emoji: "🍕", image: "/images/categories/pizza.jpg" },
  { value: "BURGER", label: "Burger", emoji: "🍔", image: "/images/categories/burger.jpg" },
  { value: "DRINK", label: "Drinks", emoji: "🥤", image: "/images/categories/drink.jpg" },
  { value: "DESSERT", label: "Dessert", emoji: "🍰", image: "/images/categories/dessert.jpg" },
  { value: "INDIAN", label: "Indian", emoji: "🍛", image: "/images/categories/indian.jpg" },
  { value: "CHINESE", label: "Chinese", emoji: "🥢", image: "/images/categories/chinese.jpg" },
  { value: "SOUTH_INDIAN", label: "South Indian", emoji: "🫙", image: "/images/categories/south_indian.jpg" },
  { value: "CAKE", label: "Cake", emoji: "🎂", image: "/images/categories/cake.jpg" },
  { value: "ROLLS", label: "Rolls", emoji: "🌯", image: "/images/categories/rolls.jpg" },
  { value: "NOODLES", label: "Noodles", emoji: "🍜", image: "/images/categories/noodles.jpg" },
  { value: "COFFEE", label: "Coffee", emoji: "☕", image: "/images/categories/coffee.jpg" },
  { value: "HEALTHY", label: "Healthy", emoji: "🥗", image: "/images/categories/healthy.jpg" },
  { value: "SUSHI", label: "Sushi", emoji: "🍣", image: "/images/categories/sushi.jpg" },
  { value: "STARTERS", label: "Starters", emoji: "🥙", image: "/images/categories/starters.jpg" },
  { value: "BREADS", label: "Breads", emoji: "🫓", image: "/images/categories/breads.jpg" },
];


// Order statuses
export const ORDER_STATUSES = {
  PENDING: "PENDING",
  CONFIRMED: "CONFIRMED",
  PREPARING: "PREPARING",
  OUT_FOR_DELIVERY: "OUT_FOR_DELIVERY",
  DELIVERED: "DELIVERED",
  CANCELLED: "CANCELLED",
};

// Payment methods
export const PAYMENT_METHODS = {
  CASH_ON_DELIVERY: "CASH_ON_DELIVERY",
  ONLINE_PAYMENT: "ONLINE_PAYMENT",
};

// Payment statuses
export const PAYMENT_STATUSES = {
  PENDING: "PENDING",
  PAID: "PAID",
  FAILED: "FAILED",
};

// Account statuses
export const ACCOUNT_STATUSES = {
  ACTIVE: "ACTIVE",
  BLOCKED: "BLOCKED",
  SUSPENDED: "SUSPENDED",
};

// Address types
export const ADDRESS_TYPES = {
  HOME: "HOME",
  WORK: "WORK",
  OTHER: "OTHER",
};

// Pagination defaults
export const DEFAULT_PAGE_SIZE = 10;

// Local storage keys
export const STORAGE_KEYS = {
  TOKEN: "fd_token",
  USER: "fd_user",
  THEME: "fd_theme",
};
