import React, { createContext, useContext, useEffect, useState, ReactNode } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { authService } from '@/services/api';
import { User, AuthContextType, LoginCredentials, RegisterData } from '@/types';

const AuthContext = createContext<AuthContextType | undefined>(undefined);

interface AuthProviderProps {
  children: ReactNode;
}

export function AuthProvider({ children }: AuthProviderProps) {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  const isAuthenticated = !!token && !!user;

  // Carregar dados salvos ao iniciar o app
  useEffect(() => {
    loadStoredAuth();
  }, []);

  async function loadStoredAuth() {
    try {
      const storedToken = await AsyncStorage.getItem('@auth_token');
      const storedUser = await AsyncStorage.getItem('@auth_user');

      if (storedToken && storedUser) {
        setToken(storedToken);
        setUser(JSON.parse(storedUser));
      }
    } catch (error) {
      console.error('Erro ao carregar autenticação:', error);
    } finally {
      setIsLoading(false);
    }
  }

  async function login(credentials: LoginCredentials) {
    try {
      const response = await authService.login(credentials.email, credentials.password);
      
      const { token: newToken, user: newUser } = response;

      await AsyncStorage.setItem('@auth_token', newToken);
      await AsyncStorage.setItem('@auth_user', JSON.stringify(newUser));

      setToken(newToken);
      setUser(newUser);
    } catch (error: any) {
      const message = error.response?.data?.message || 'Erro ao fazer login';
      throw new Error(message);
    }
  }

  async function register(data: RegisterData) {
    try {
      const response = await authService.register(data.name, data.email, data.password);
      
      const { token: newToken, user: newUser } = response;

      await AsyncStorage.setItem('@auth_token', newToken);
      await AsyncStorage.setItem('@auth_user', JSON.stringify(newUser));

      setToken(newToken);
      setUser(newUser);
    } catch (error: any) {
      const message = error.response?.data?.message || 'Erro ao criar conta';
      throw new Error(message);
    }
  }

  async function logout() {
    try {
      await AsyncStorage.removeItem('@auth_token');
      await AsyncStorage.removeItem('@auth_user');
      setToken(null);
      setUser(null);
    } catch (error) {
      console.error('Erro ao fazer logout:', error);
    }
  }

  return (
    <AuthContext.Provider
      value={{
        user,
        token,
        isLoading,
        isAuthenticated,
        login,
        register,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth(): AuthContextType {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth deve ser usado dentro de um AuthProvider');
  }
  return context;
}
