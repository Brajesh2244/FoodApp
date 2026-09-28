import { Navigate, Outlet } from "react-router-dom";
import { useSelector } from "react-redux";

const RoleRoute = ({ role, allowedRoles, children }) => {
  const { user, isAuthenticated, loading } = useSelector((state) => state.auth);

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <p>Loading...</p>
      </div>
    );
  }

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  const rolesToCheck = allowedRoles || (role ? [role] : []);

  if (!user || (rolesToCheck.length > 0 && !rolesToCheck.includes(user.role))) {
    return <Navigate to="/unauthorized" replace />;
  }

  return children ? children : <Outlet />;
};

export default RoleRoute;
