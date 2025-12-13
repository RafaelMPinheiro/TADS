import { Stack } from 'expo-router';
import { CartProvider } from '../contexts/CartContext';
import { SHOPEE_COLORS } from '../constants/colors';

export default function RootLayout() {
  return (
    <CartProvider>
      <Stack
        screenOptions={{
          headerStyle: {
            backgroundColor: SHOPEE_COLORS.primary,
          },
          headerTintColor: SHOPEE_COLORS.background,
          headerTitleStyle: {
            fontWeight: 'bold',
          },
        }}
      >
        <Stack.Screen 
          name="index" 
          options={{ 
            headerShown: false, // Using custom header component
          }} 
        />
        <Stack.Screen 
          name="product/[id]" 
          options={{ 
            title: 'Detalhes do Produto',
            headerBackTitle: 'Voltar',
          }} 
        />
        <Stack.Screen 
          name="cart" 
          options={{ 
            title: 'Meu Carrinho',
            headerBackTitle: 'Voltar',
          }} 
        />
      </Stack>
    </CartProvider>
  );
}
