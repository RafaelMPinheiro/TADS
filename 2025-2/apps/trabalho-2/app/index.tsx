import React from 'react';
import { View, Text, FlatList, StyleSheet, StatusBar } from 'react-native';
import { useRouter } from 'expo-router';
import ProductCard from '../components/ProductCard';
import Header from '../components/Header';
import { PRODUCTS } from '../constants/products';
import { SHOPEE_COLORS } from '../constants/colors';
import { useCart } from '../contexts/CartContext';

export default function ProductsScreen() {
  const router = useRouter();
  const { getTotalItems } = useCart();

  const navigateToProduct = (productId: string) => {
    router.push(`/product/${productId}`);
  };

  const navigateToCart = () => {
    router.push('/cart');
  };

  const renderProduct = ({ item }: { item: any }) => (
    <ProductCard
      product={item}
      onPress={() => navigateToProduct(item.id)}
    />
  );

  return (
    <View style={styles.container}>
      <StatusBar barStyle="light-content" backgroundColor={SHOPEE_COLORS.primary} />
      
      <Header 
        title="Shopee Brasil" 
        showCart 
        cartItemCount={getTotalItems()}
        onCartPress={navigateToCart}
      />
      
      <View style={styles.content}>
        <Text style={styles.sectionTitle}>Produtos em Destaque</Text>
        
        <FlatList
          data={PRODUCTS}
          keyExtractor={(item) => item.id}
          renderItem={renderProduct}
          numColumns={2}
          showsVerticalScrollIndicator={false}
          contentContainerStyle={styles.productList}
        />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: SHOPEE_COLORS.backgroundLight,
  },
  content: {
    flex: 1,
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: SHOPEE_COLORS.text,
    paddingHorizontal: 16,
    paddingVertical: 12,
  },
  productList: {
    paddingBottom: 20,
  },
});