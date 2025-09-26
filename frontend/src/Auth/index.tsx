import { useContext, createContext, FC, useState } from "react";
import { AuthContextType, User, AuthProviderComponentProps } from "./types";

const AuthContext = createContext<AuthContextType | undefined>(undefined);

const AppUrl = process.env["REACT_APP_API_URL"]
  ? process.env["REACT_APP_API_URL"]
  : "https://localhost:8443";

const AuthApiUrl = process.env["REACT_APP_AUTH_API_URL"]
  ? process.env["REACT_APP_AUTH_API_URL"]
  : "/api/v1/auth";

const AuthProvider: FC<AuthProviderComponentProps> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);

  const login = async (username: string, password: string) => {
    const res = await fetch(AppUrl + AuthApiUrl, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password }),
      credentials: "include",
    });

    if (!res.ok) {
      const messageWrapper = await res.json();
      throw new Error("Login failed, reason = " + messageWrapper.message);
    }
  };

  const info = async () => {
    const res = await fetch(AppUrl + AuthApiUrl, {
      headers: { "Content-Type": "application/json" },
      credentials: "include",
    });

    if (!res.ok) {
      setUser(null);
      throw new Error("User is not authenticated, login to continue.");
    }
    const responseJson = await res.json();
    console.log("responseJson:", responseJson);
    setUser(responseJson);
  };

  const logout = async () => {
    await fetch(AppUrl + AuthApiUrl + "/logout", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
    });
  };

  return (
    <AuthContext.Provider value={{ user, login, info, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;

export const useAuth = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider.");
  }
  return context;
};
