import { ReactNode } from "react";

export interface User {
  id: number;
  username: string;
  roles: string[];
}

export interface AuthContextType {
  user: User | null;
  login: (username: string, password: string) => Promise<void>;
  logout: () => Promise<void>;
}

export interface AuthProviderComponentProps {
  children: ReactNode;
}
