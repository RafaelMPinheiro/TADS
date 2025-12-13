import React from 'react';
import { View, Text, Image, TouchableOpacity, ScrollView, StyleSheet, Alert, StatusBar } from 'react-native';
import { useLocalSearchParams, useRouter } from 'expo-router';
import { useCart } from '../../contexts/CartContext';
import { PRODUCTS } from '../../constants/products';
import { SHOPEE_COLORS } from '../../constants/colors';

export default function ProductDetailsScreen() {
  const { id } = useLocalSearchParams<{ id: string }>();
  const router = useRouter();
  const { addToCart } = useCart();

  const product = PRODUCTS.find(p => p.id === id);

  if (!product) {
    return (
      <View style={styles.errorContainer}>
        <Text style={styles.errorText}>Produto não encontrado</Text>
        <TouchableOpacity 
          style={styles.backButton} 
          onPress={() => router.back()}
        >
          <Text style={styles.backButtonText}>Voltar</Text>
        </TouchableOpacity>
      </View>
    );
  }

  const handleAddToCart = () => {
    addToCart(product);
    Alert.alert(
      'Produto Adicionado!',
      `${product.name} foi adicionado ao seu carrinho.`,
      [
        { text: 'Continuar Comprando', style: 'cancel' },
        { 
          text: 'Ver Carrinho', 
          onPress: () => router.push('/cart') 
        }
      ]
    );
  };

  const discountAmount = product.originalPrice 
    ? product.originalPrice - product.price 
    : 0;

  return (
    <View style={styles.container}>
      <StatusBar barStyle="light-content" backgroundColor={SHOPEE_COLORS.primary} />
      
      <ScrollView showsVerticalScrollIndicator={false}>
        <Image source={{ uri: product.image }} style={styles.productImage} />
        
        {product.discount && (
          <View style={styles.discountBadge}>
            <Text style={styles.discountText}>-{product.discount}% OFF</Text>
          </View>
        )}

        <View style={styles.productInfo}>
          <Text style={styles.productName}>{product.name}</Text>
          
          <View style={styles.ratingContainer}>
            <Text style={styles.rating}>⭐ {product.rating}</Text>
            <Text style={styles.reviews}>({product.reviews} avaliações)</Text>
            <View style={styles.categoryBadge}>
              <Text style={styles.categoryText}>{product.category}</Text>
            </View>
          </View>

          <View style={styles.priceContainer}>
            <Text style={styles.price}>R$ {product.price.toFixed(2)}</Text>
            {product.originalPrice && (
              <View style={styles.originalPriceContainer}>
                <Text style={styles.originalPrice}>
                  R$ {product.originalPrice.toFixed(2)}
                </Text>
                <Text style={styles.savings}>
                  Economia de R$ {discountAmount.toFixed(2)}
                </Text>
              </View>
            )}
          </View>

          <View style={styles.descriptionContainer}>
            <Text style={styles.descriptionTitle}>Descrição do Produto</Text>
            <Text style={styles.description}>{product.description}</Text>
          </View>

          <View style={styles.featuresContainer}>
            <Text style={styles.featuresTitle}>Informações Adicionais</Text>
            <View style={styles.featureItem}>
              <Text style={styles.featureLabel}>Categoria:</Text>
              <Text style={styles.featureValue}>{product.category}</Text>
            </View>
            <View style={styles.featureItem}>
              <Text style={styles.featureLabel}>Avaliação:</Text>
              <Text style={styles.featureValue}>{product.rating}/5 estrelas</Text>
            </View>
            <View style={styles.featureItem}>
              <Text style={styles.featureLabel}>Avaliações:</Text>
              <Text style={styles.featureValue}>{product.reviews} compradores</Text>
            </View>
          </View>
        </View>
      </ScrollView>

      <View style={styles.bottomContainer}>
        <TouchableOpacity 
          style={styles.addToCartButton}
          onPress={handleAddToCart}
        >
          <Text style={styles.addToCartText}>🛒 Adicionar ao Carrinho</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: SHOPEE_COLORS.background,
  },
  errorContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
  },
  errorText: {
    fontSize: 18,
    color: SHOPEE_COLORS.text,
    marginBottom: 20,
  },
  backButton: {
    backgroundColor: SHOPEE_COLORS.primary,
    paddingHorizontal: 20,
    paddingVertical: 10,
    borderRadius: 8,
  },
  backButtonText: {
    color: SHOPEE_COLORS.background,
    fontSize: 16,
    fontWeight: 'bold',
  },
  productImage: {
    width: '100%',
    height: 300,
    backgroundColor: SHOPEE_COLORS.backgroundLight,
  },
  discountBadge: {
    position: 'absolute',
    top: 16,
    right: 16,
    backgroundColor: SHOPEE_COLORS.secondary,
    paddingHorizontal: 12,
    paddingVertical: 6,
    borderRadius: 8,
  },
  discountText: {
    color: SHOPEE_COLORS.background,
    fontSize: 14,
    fontWeight: 'bold',
  },
  productInfo: {
    padding: 20,
  },
  productName: {
    fontSize: 22,
    fontWeight: 'bold',
    color: SHOPEE_COLORS.text,
    marginBottom: 12,
    lineHeight: 28,
  },
  ratingContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 16,
    gap: 8,
  },
  rating: {
    fontSize: 14,
    color: SHOPEE_COLORS.text,
  },
  reviews: {
    fontSize: 14,
    color: SHOPEE_COLORS.textSecondary,
  },
  categoryBadge: {
    backgroundColor: SHOPEE_COLORS.backgroundLight,
    paddingHorizontal: 8,
    paddingVertical: 4,
    borderRadius: 4,
    marginLeft: 'auto',
  },
  categoryText: {
    fontSize: 12,
    color: SHOPEE_COLORS.tertiary,
    fontWeight: '600',
  },
  priceContainer: {
    marginBottom: 24,
  },
  price: {
    fontSize: 28,
    fontWeight: 'bold',
    color: SHOPEE_COLORS.primary,
    marginBottom: 4,
  },
  originalPriceContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 12,
  },
  originalPrice: {
    fontSize: 16,
    color: SHOPEE_COLORS.textSecondary,
    textDecorationLine: 'line-through',
  },
  savings: {
    fontSize: 14,
    color: SHOPEE_COLORS.secondary,
    fontWeight: '600',
  },
  descriptionContainer: {
    marginBottom: 24,
  },
  descriptionTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: SHOPEE_COLORS.text,
    marginBottom: 8,
  },
  description: {
    fontSize: 16,
    color: SHOPEE_COLORS.textSecondary,
    lineHeight: 24,
  },
  featuresContainer: {
    backgroundColor: SHOPEE_COLORS.backgroundLight,
    borderRadius: 8,
    padding: 16,
  },
  featuresTitle: {
    fontSize: 16,
    fontWeight: 'bold',
    color: SHOPEE_COLORS.text,
    marginBottom: 12,
  },
  featureItem: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 8,
  },
  featureLabel: {
    fontSize: 14,
    color: SHOPEE_COLORS.textSecondary,
  },
  featureValue: {
    fontSize: 14,
    fontWeight: '600',
    color: SHOPEE_COLORS.text,
  },
  bottomContainer: {
    padding: 20,
    backgroundColor: SHOPEE_COLORS.background,
    borderTopWidth: 1,
    borderTopColor: SHOPEE_COLORS.backgroundLight,
  },
  addToCartButton: {
    backgroundColor: SHOPEE_COLORS.primary,
    paddingVertical: 16,
    borderRadius: 8,
    alignItems: 'center',
  },
  addToCartText: {
    color: SHOPEE_COLORS.background,
    fontSize: 18,
    fontWeight: 'bold',
  },
});