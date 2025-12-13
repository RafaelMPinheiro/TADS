import React from 'react';
import { Stack, Redirect } from 'expo-router';
import { TouchableOpacity, Text, StyleSheet, Alert } from 'react-native';
import { useAuth } from '@/contexts/AuthContext';
import { Loading } from '@/components';
import { COLORS } from '@/constants/colors';

export default function ProtectedLayout() {
  const { isAuthenticated, isLoading, logout } = useAuth();

  if (isLoading) {
    return <Loading />;
  }

  if (!isAuthenticated) {
    return <Redirect href="/login" />;
  }

  const handleLogout = () => {
    Alert.alert('Sair', 'Deseja realmente sair da sua conta?', [
      { text: 'Cancelar', style: 'cancel' },
      {
        text: 'Sair',
        style: 'destructive',
        onPress: logout,
      },
    ]);
  };

  return (
    <Stack
      screenOptions={{
        headerStyle: { backgroundColor: COLORS.primary },
        headerTintColor: '#fff',
        headerTitleStyle: { fontWeight: '600' },
        headerRight: () => (
          <TouchableOpacity onPress={handleLogout} style={styles.logoutButton}>
            <Text style={styles.logoutText}>Sair</Text>
          </TouchableOpacity>
        ),
      }}
    >
      <Stack.Screen
        name="posts"
        options={{ title: 'Posts' }}
      />
      <Stack.Screen
        name="users"
        options={{ title: 'Usuários' }}
      />
      <Stack.Screen
        name="create-post"
        options={{ title: 'Novo Post' }}
      />
    </Stack>
  );
}

const styles = StyleSheet.create({
  logoutButton: {
    paddingHorizontal: 12,
    paddingVertical: 6,
  },
  logoutText: {
    color: '#fff',
    fontSize: 14,
    fontWeight: '500',
  },
});
