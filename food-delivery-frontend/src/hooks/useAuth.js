import { useDispatch, useSelector } from "react-redux";
import { login, register, logout, clearError } from "../store/slices/authSlice";
import { ROLES } from "../utils/constants";

export const useAuth = () => {
  const dispatch = useDispatch();

  const { user, token, loading, error, isAuthenticated } = useSelector(
    (state) => state.auth,
  );

  return {
    user,
    token,
    loading,
    error,
    isAuthenticated,
    isCustomer: user?.role === ROLES.CUSTOMER,
    isAdmin: user?.role === ROLES.ADMIN,
    isOwner: user?.role === ROLES.RESTAURANT_OWNER,

    login: (data) => dispatch(login(data)),
    register: (data) => dispatch(register(data)),
    logout: () => dispatch(logout()),
    clearError: () => dispatch(clearError()),
  };
};

