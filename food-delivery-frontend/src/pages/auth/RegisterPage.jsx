import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import { Mail, Lock, User, Phone, MapPin, Eye, EyeOff } from "lucide-react";
import { useState, useEffect } from "react";
import { useAuth } from "../../hooks/useAuth";
import Button from "../../components/common/Button";
import AuthLayout from "../../layouts/AuthLayout";
import {
  emailRules,
  passwordRules,
  fullNameRules,
} from "../../utils/validators";
import toast from "react-hot-toast";

const RegisterPage = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const {
    register: registerUser,
    loading,
    error,
    isAuthenticated,
    user,
    clearError,
  } = useAuth();

  const navigate = useNavigate();

  const [showPassword, setShowPassword] = useState(false);

  useEffect(() => {
    if (isAuthenticated && user) {
      navigate("/", { replace: true });
    }
  }, [isAuthenticated, user, navigate]);

  useEffect(() => {
    clearError();
  }, []);

  const onSubmit = async (data) => {
    const requestData = {
      name: data.name,
      email: data.email,
      password: data.password,
      phone: data.phone,
      address: data.address,
    };

    const result = await registerUser(requestData);

    if (result.meta?.requestStatus === "fulfilled") {
      toast.success("Account created successfully! 🎉");
    }
  };

  return (
    <AuthLayout>
      <div
        className="rounded-3xl p-8 shadow-xl"
        style={{
          backgroundColor: "var(--color-surface)",
          border: "1px solid var(--color-border)",
        }}
      >
        {/* Header */}

        <div className="text-center mb-8">
          <Link to="/" className="inline-flex items-center gap-2 mb-4">
            <span className="text-3xl">🍔</span>

            <span
              className="text-2xl font-black"
              style={{
                color: "var(--color-primary)",
              }}
            >
              Food Delivery
            </span>
          </Link>

          <h1
            className="text-2xl font-bold"
            style={{
              color: "var(--color-text-primary)",
            }}
          >
            Create Account
          </h1>

          <p
            className="text-sm mt-1"
            style={{
              color: "var(--color-text-secondary)",
            }}
          >
            Join us and order your favourite food
          </p>
        </div>

        {/* Backend Error */}

        {error && (
          <div
            className="mb-4 p-3 rounded-xl text-sm"
            style={{
              color: "var(--color-error)",
              backgroundColor: "var(--color-error-light)",
              border: "1px solid var(--color-error)",
            }}
          >
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
          {/* Name */}

          <div>
            <label
              className="block text-sm font-medium mb-1.5"
              style={{
                color: "var(--color-text-primary)",
              }}
            >
              Full Name
            </label>

            <div className="relative">
              <User
                className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4"
                style={{
                  color: "var(--color-text-tertiary)",
                }}
              />

              <input
                type="text"
                {...register("name", fullNameRules)}
                placeholder="Enter your full name"
                className="w-full pl-10 pr-4 py-3 rounded-xl text-sm outline-none"
                style={{
                  backgroundColor: "var(--color-bg-secondary)",
                  border: `1.5px solid ${
                    errors.name ? "var(--color-error)" : "var(--color-border)"
                  }`,
                  color: "var(--color-text-primary)",
                }}
              />
            </div>

            {errors.name && (
              <p className="text-xs text-red-500 mt-1">{errors.name.message}</p>
            )}
          </div>

          {/* Email */}

          <div>
            <label
              className="block text-sm font-medium mb-1.5"
              style={{
                color: "var(--color-text-primary)",
              }}
            >
              Email
            </label>

            <div className="relative">
              <Mail
                className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4"
                style={{
                  color: "var(--color-text-tertiary)",
                }}
              />

              <input
                type="email"
                {...register("email", emailRules)}
                placeholder="you@example.com"
                className="w-full pl-10 pr-4 py-3 rounded-xl text-sm outline-none"
                style={{
                  backgroundColor: "var(--color-bg-secondary)",
                  border: `1.5px solid ${
                    errors.email ? "var(--color-error)" : "var(--color-border)"
                  }`,
                  color: "var(--color-text-primary)",
                }}
              />
            </div>

            {errors.email && (
              <p className="text-xs text-red-500 mt-1">
                {errors.email.message}
              </p>
            )}
          </div>

          {/* Phone */}

          <div>
            <label
              className="block text-sm font-medium mb-1.5"
              style={{
                color: "var(--color-text-primary)",
              }}
            >
              Phone Number
            </label>

            <div className="relative">
              <Phone
                className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4"
                style={{
                  color: "var(--color-text-tertiary)",
                }}
              />

              <input
                type="text"
                {...register("phone", {
                  required: "Phone number is required",
                })}
                placeholder="Enter phone number"
                className="w-full pl-10 pr-4 py-3 rounded-xl text-sm outline-none"
                style={{
                  backgroundColor: "var(--color-bg-secondary)",
                  border: `1.5px solid ${
                    errors.phone ? "var(--color-error)" : "var(--color-border)"
                  }`,
                  color: "var(--color-text-primary)",
                }}
              />
            </div>

            {errors.phone && (
              <p className="text-xs text-red-500 mt-1">
                {errors.phone.message}
              </p>
            )}
          </div>

          {/* Address */}

          <div>
            <label
              className="block text-sm font-medium mb-1.5"
              style={{
                color: "var(--color-text-primary)",
              }}
            >
              Address
            </label>

            <div className="relative">
              <MapPin
                className="absolute left-3 top-3 w-4 h-4"
                style={{
                  color: "var(--color-text-tertiary)",
                }}
              />

              <textarea
                {...register("address", {
                  required: "Address is required",
                })}
                placeholder="Enter your address"
                rows="2"
                className="w-full pl-10 pr-4 py-3 rounded-xl text-sm outline-none resize-none"
                style={{
                  backgroundColor: "var(--color-bg-secondary)",
                  border: `1.5px solid ${
                    errors.address
                      ? "var(--color-error)"
                      : "var(--color-border)"
                  }`,
                  color: "var(--color-text-primary)",
                }}
              />
            </div>

            {errors.address && (
              <p className="text-xs text-red-500 mt-1">
                {errors.address.message}
              </p>
            )}
          </div>

          {/* Password */}

          <div>
            <label
              className="block text-sm font-medium mb-1.5"
              style={{
                color: "var(--color-text-primary)",
              }}
            >
              Password
            </label>

            <div className="relative">
              <Lock
                className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4"
                style={{
                  color: "var(--color-text-tertiary)",
                }}
              />

              <input
                type={showPassword ? "text" : "password"}
                {...register("password", passwordRules)}
                placeholder="Minimum 6 characters"
                className="w-full pl-10 pr-10 py-3 rounded-xl text-sm outline-none"
                style={{
                  backgroundColor: "var(--color-bg-secondary)",
                  border: `1.5px solid ${
                    errors.password
                      ? "var(--color-error)"
                      : "var(--color-border)"
                  }`,
                  color: "var(--color-text-primary)",
                }}
              />

              <button
                type="button"
                onClick={() => setShowPassword(!showPassword)}
                className="absolute right-3 top-1/2 -translate-y-1/2 cursor-pointer"
                style={{
                  color: "var(--color-text-tertiary)",
                }}
              >
                {showPassword ? (
                  <EyeOff className="w-4 h-4" />
                ) : (
                  <Eye className="w-4 h-4" />
                )}
              </button>
            </div>

            {errors.password && (
              <p className="text-xs text-red-500 mt-1">
                {errors.password.message}
              </p>
            )}
          </div>

          <Button type="submit" fullWidth loading={loading} size="lg">
            Create Account
          </Button>
        </form>

        <p
          className="text-center text-sm mt-6"
          style={{
            color: "var(--color-text-secondary)",
          }}
        >
          Already have an account?{" "}
          <Link
            to="/login"
            className="font-semibold"
            style={{
              color: "var(--color-primary)",
            }}
          >
            Sign In
          </Link>
        </p>
      </div>
    </AuthLayout>
  );
};

export default RegisterPage;
