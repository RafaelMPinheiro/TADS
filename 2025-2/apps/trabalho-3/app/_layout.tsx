import React from 'react';
import { Stack } from 'expo-router';
import { StatusBar } from 'expo-status-bar';
import { AuthProvider } from '@/contexts/AuthContext';
import { COLORS } from '@/constants/colors';

export default function RootLayout() {
  return (
    <AuthProvider>
      <StatusBar style="light" />
      <Stack
        screenOptions={{
          headerStyle: { backgroundColor: COLORS.primary },
          headerTintColor: '#fff',
          headerTitleStyle: { fontWeight: '600' },
        }}
      >
        <Stack.Screen
          name="index"
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name="login"
          options={{ title: 'Login', headerShown: false }}
        />
        <Stack.Screen
          name="register"
          options={{ title: 'Cadastro', headerBackTitle: 'Voltar' }}
        />
        <Stack.Screen
          name="(protected)"
          options={{ headerShown: false }}
        />
      </Stack>
    </AuthProvider>
  );
}
