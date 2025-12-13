import React from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';
import { SHOPEE_COLORS } from '../constants/colors';

interface HeaderProps {
  title: string;
  showCart?: boolean;
  cartItemCount?: number;
  onCartPress?: () => void;
}

export default function Header({ title, showCart = false, cartItemCount = 0, onCartPress }: HeaderProps) {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>{title}</Text>
      
      {showCart && (
        <TouchableOpacity style={styles.cartButton} onPress={onCartPress}>
          <Text style={styles.cartIcon}>🛒</Text>
          {cartItemCount > 0 && (
            <View style={styles.badge}>
              <Text style={styles.badgeText}>
                {cartItemCount > 99 ? '99+' : cartItemCount.toString()}
              </Text>
            </View>
          )}
        </TouchableOpacity>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    backgroundColor: SHOPEE_COLORS.primary,
    paddingHorizontal: 16,
    paddingVertical: 12,
    paddingTop: 48, // Account for status bar
  },
  title: {
    fontSize: 18,
    fontWeight: 'bold',
    color: SHOPEE_COLORS.background,
  },
  cartButton: {
    position: 'relative',
    padding: 8,
  },
  cartIcon: {
    fontSize: 24,
  },
  badge: {
    position: 'absolute',
    top: 0,
    right: 0,
    backgroundColor: SHOPEE_COLORS.secondary,
    borderRadius: 10,
    minWidth: 20,
    height: 20,
    justifyContent: 'center',
    alignItems: 'center',
    paddingHorizontal: 4,
  },
  badgeText: {
    fontSize: 12,
    fontWeight: 'bold',
    color: SHOPEE_COLORS.background,
  },
});