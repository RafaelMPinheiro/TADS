import React from 'react';
import { 
  View, 
  Text, 
  FlatList, 
  TouchableOpacity, 
  StyleSheet,
  StatusBar,
  Alert,
  Button
} from 'react-native';
import { useRouter } from 'expo-router';
import { useCart } from '../contexts/CartContext';
import CartItem from '../components/CartItem';
import { SHOPEE_COLORS } from '../constants/colors';

export default function CartScreen() {
  const router = useRouter();
  const { items, getTotalPrice, getTotalItems, updateQuantity, removeFromCart, clearCart } = useCart();

  const handleCheckout = () => {
    if (items.length === 0) {
      Alert.alert('Carrinho Vazio', 'Adicione produtos ao carrinho antes de finalizar a compra.');
      return;
    }

    Alert.alert(
      'Compra Finalizada!',
      `Obrigado pela sua compra! Total: R$ ${getTotalPrice().toFixed(2)}`,
      [
        {
          text: 'OK',
          onPress: () => {
            clearCart();
            router.push('/');
          }
        }
      ]
    );
  };

  const renderCartItem = ({ item }: { item: any }) => (
    <CartItem
      item={item}
      onUpdateQuantity={(quantity) => updateQuantity(item.product.id, quantity)}
      onRemove={() => removeFromCart(item.product.id)}
    />
  );

  if (items.length === 0) {
    return (
      <View style={styles.container}>
        <StatusBar barStyle="light-content" backgroundColor={SHOPEE_COLORS.primary} />
        
        <View style={styles.emptyContainer}>
          <Text style={styles.emptyIcon}>🛒</Text>
          <Text style={styles.emptyText}>Seu carrinho está vazio</Text>
          <Text style={styles.emptySubtext}>
            Adicione produtos incríveis ao seu carrinho e aproveite nossas ofertas!
          </Text>
          
          <TouchableOpacity 
            style={styles.shopButton}
            onPress={() => router.push('/')}
          >
            <Text style={styles.shopButtonText}>Continuar Comprando</Text>
          </TouchableOpacity>
        </View>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <StatusBar barStyle="light-content" backgroundColor={SHOPEE_COLORS.primary} />
      
      <View style={styles.header}>
        <Text style={styles.headerTitle}>Meu Carrinho</Text>
        <Text style={styles.itemCount}>{getTotalItems()} {getTotalItems() === 1 ? 'item' : 'itens'}</Text>
      </View>

      <FlatList
        data={items}
        keyExtractor={(item) => item.product.id}
        renderItem={renderCartItem}
        showsVerticalScrollIndicator={false}
        contentContainerStyle={styles.cartList}
      />
      
      <View style={styles.summary}>
        <View style={styles.totalContainer}>
          <View style={styles.totalRow}>
            <Text style={styles.totalLabel}>
              Subtotal ({getTotalItems()} {getTotalItems() === 1 ? 'item' : 'itens'}):
            </Text>
            <Text style={styles.totalPrice}>
              R$ {getTotalPrice().toFixed(2)}
            </Text>
          </View>
          
          <View style={styles.totalRow}>
            <Text style={styles.shippingLabel}>Frete:</Text>
            <Text style={styles.freeShipping}>GRÁTIS</Text>
          </View>
          
          <View style={[styles.totalRow, styles.finalTotal]}>
            <Text style={styles.finalTotalLabel}>Total:</Text>
            <Text style={styles.finalTotalPrice}>
              R$ {getTotalPrice().toFixed(2)}
            </Text>
          </View>
        </View>
        
        <TouchableOpacity 
          style={styles.checkoutButton}
          onPress={handleCheckout}
        >
          <Text style={styles.checkoutText}>Finalizar Compra</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: SHOPEE_COLORS.backgroundLight,
  },
  header: {
    backgroundColor: SHOPEE_COLORS.background,
    paddingHorizontal: 16,
    paddingVertical: 16,
    borderBottomWidth: 1,
    borderBottomColor: SHOPEE_COLORS.backgroundLight,
  },
  headerTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: SHOPEE_COLORS.text,
  },
  itemCount: {
    fontSize: 14,
    color: SHOPEE_COLORS.textSecondary,
    marginTop: 2,
  },
  cartList: {
    paddingVertical: 8,
  },
  emptyContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 40,
  },
  emptyIcon: {
    fontSize: 64,
    marginBottom: 16,
  },
  emptyText: {
    fontSize: 20,
    fontWeight: 'bold',
    color: SHOPEE_COLORS.text,
    marginBottom: 8,
  },
  emptySubtext: {
    fontSize: 14,
    color: SHOPEE_COLORS.textSecondary,
    textAlign: 'center',
    lineHeight: 20,
    marginBottom: 24,
  },
  shopButton: {
    backgroundColor: SHOPEE_COLORS.primary,
    paddingHorizontal: 24,
    paddingVertical: 12,
    borderRadius: 8,
  },
  shopButtonText: {
    color: SHOPEE_COLORS.background,
    fontSize: 16,
    fontWeight: 'bold',
  },
  summary: {
    backgroundColor: SHOPEE_COLORS.background,
    padding: 16,
    borderTopWidth: 1,
    borderTopColor: SHOPEE_COLORS.backgroundLight,
  },
  totalContainer: {
    marginBottom: 16,
  },
  totalRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 8,
  },
  totalLabel: {
    fontSize: 14,
    color: SHOPEE_COLORS.textSecondary,
  },
  totalPrice: {
    fontSize: 14,
    fontWeight: '600',
    color: SHOPEE_COLORS.text,
  },
  shippingLabel: {
    fontSize: 14,
    color: SHOPEE_COLORS.textSecondary,
  },
  freeShipping: {
    fontSize: 14,
    fontWeight: 'bold',
    color: SHOPEE_COLORS.secondary,
  },
  finalTotal: {
    borderTopWidth: 1,
    borderTopColor: SHOPEE_COLORS.backgroundLight,
    paddingTop: 8,
    marginTop: 8,
  },
  finalTotalLabel: {
    fontSize: 18,
    fontWeight: 'bold',
    color: SHOPEE_COLORS.text,
  },
  finalTotalPrice: {
    fontSize: 20,
    fontWeight: 'bold',
    color: SHOPEE_COLORS.primary,
  },
  checkoutButton: {
    backgroundColor: SHOPEE_COLORS.primary,
    paddingVertical: 16,
    borderRadius: 8,
    alignItems: 'center',
  },
  checkoutText: {
    color: SHOPEE_COLORS.background,
    fontSize: 18,
    fontWeight: 'bold',
  },
});