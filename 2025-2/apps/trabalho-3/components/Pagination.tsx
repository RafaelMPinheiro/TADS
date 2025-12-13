import React from 'react';
import { View, TouchableOpacity, Text, StyleSheet } from 'react-native';
import { COLORS } from '@/constants/colors';

interface PaginationProps {
  currentPage: number;
  totalPages: number;
  onPageChange: (page: number) => void;
}

export function Pagination({ currentPage, totalPages, onPageChange }: PaginationProps) {
  const canGoPrevious = currentPage > 1;
  const canGoNext = currentPage < totalPages;

  return (
    <View style={styles.container}>
      <TouchableOpacity
        style={[styles.button, !canGoPrevious && styles.buttonDisabled]}
        onPress={() => onPageChange(currentPage - 1)}
        disabled={!canGoPrevious}
      >
        <Text style={[styles.buttonText, !canGoPrevious && styles.buttonTextDisabled]}>
          Anterior
        </Text>
      </TouchableOpacity>

      <Text style={styles.pageInfo}>
        {currentPage} / {totalPages}
      </Text>

      <TouchableOpacity
        style={[styles.button, !canGoNext && styles.buttonDisabled]}
        onPress={() => onPageChange(currentPage + 1)}
        disabled={!canGoNext}
      >
        <Text style={[styles.buttonText, !canGoNext && styles.buttonTextDisabled]}>
          Próximo
        </Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingVertical: 16,
    paddingHorizontal: 8,
  },
  button: {
    paddingVertical: 10,
    paddingHorizontal: 20,
    backgroundColor: COLORS.primary,
    borderRadius: 8,
  },
  buttonDisabled: {
    backgroundColor: COLORS.disabled,
  },
  buttonText: {
    color: '#fff',
    fontSize: 14,
    fontWeight: '600',
  },
  buttonTextDisabled: {
    color: COLORS.textLight,
  },
  pageInfo: {
    fontSize: 14,
    color: COLORS.textSecondary,
    fontWeight: '500',
  },
});
