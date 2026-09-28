import { Link } from "react-router-dom";

const Footer = () => {
  return (
    <footer
      className="mt-16"
      style={{
        backgroundColor: "var(--color-surface)",
        borderTop: "1px solid var(--color-border)",
      }}
    >
      <div className="max-w-7xl mx-auto px-6 py-12">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-10">
          {/* BRAND */}

          <div className="md:col-span-1">
            <Link to="/" className="flex items-center gap-2">
              <span className="text-2xl">🍔</span>

              <span
                className="text-xl font-black"
                style={{
                  color: "var(--color-primary)",
                }}
              >
                FoodieHub
              </span>
            </Link>

            <p
              className="text-sm mt-4 leading-6"
              style={{
                color: "var(--color-text-secondary)",
              }}
            >
              Discover amazing food from the best restaurants around you.
            </p>
          </div>

          {/* COMPANY */}

          <div>
            <h4
              className="font-bold mb-4"
              style={{
                color: "var(--color-text-primary)",
              }}
            >
              Company
            </h4>

            <div className="flex flex-col gap-3 text-sm">
              <Link to="/">About Us</Link>

              <Link to="/restaurants">Restaurants</Link>

              <Link to="/">Careers</Link>
            </div>
          </div>

          {/* CUSTOMER */}

          <div>
            <h4
              className="font-bold mb-4"
              style={{
                color: "var(--color-text-primary)",
              }}
            >
              Customer
            </h4>

            <div className="flex flex-col gap-3 text-sm">
              <Link to="/orders">My Orders</Link>

              <Link to="/wishlist">Wishlist</Link>

              <Link to="/addresses">Saved Addresses</Link>
            </div>
          </div>

          {/* SUPPORT */}

          <div>
            <h4
              className="font-bold mb-4"
              style={{
                color: "var(--color-text-primary)",
              }}
            >
              Support
            </h4>

            <div className="flex flex-col gap-3 text-sm">
              <span>Help Center</span>

              <span>Privacy Policy</span>

              <span>Terms & Conditions</span>
            </div>
          </div>
        </div>

        <div
          className="mt-10 pt-6 text-sm flex flex-col md:flex-row justify-between gap-3"
          style={{
            borderTop: "1px solid var(--color-border)",
            color: "var(--color-text-tertiary)",
          }}
        >
          <span>© {new Date().getFullYear()} FoodieHub. All rights reserved.</span>

          <span>Made for food lovers 🍔</span>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
