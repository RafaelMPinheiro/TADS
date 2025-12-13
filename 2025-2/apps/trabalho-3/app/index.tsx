import { Redirect } from 'expo-router';
import { useAuth } from '@/contexts/AuthContext';
import { Loading } from '@/components';

export default function IndexScreen() {
  const { isAuthenticated, isLoading } = useAuth();

  if (isLoading) {
    return <Loading />;
  }

  // Redireciona baseado no estado de autenticação
  if (isAuthenticated) {
    return <Redirect href="/(protected)/posts" />;
  }

  return <Redirect href="/login" />;
}
