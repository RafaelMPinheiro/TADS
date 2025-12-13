import React, { useEffect, useState, useCallback } from 'react';
import { View, FlatList, StyleSheet, Text, RefreshControl } from 'react-native';
import { COLORS } from '@/constants/colors';
import { UserCard, Loading, Pagination } from '@/components';
import { userService } from '@/services/api';
import { User, PaginatedUsers } from '@/types';

const ITEMS_PER_PAGE = 10;

export default function UsersScreen() {
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);
  const [refreshing, setRefreshing] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [error, setError] = useState<string | null>(null);

  const fetchUsers = useCallback(async (page: number = 1) => {
    try {
      setError(null);
      const response: PaginatedUsers = await userService.getUsers(page, ITEMS_PER_PAGE);
      setUsers(response.users);
      setCurrentPage(response.page);
      setTotalPages(response.totalPages);
    } catch (err: any) {
      setError(err.message || 'Erro ao carregar usuários');
    } finally {
      setLoading(false);
      setRefreshing(false);
    }
  }, []);

  useEffect(() => {
    fetchUsers();
  }, [fetchUsers]);

  const handleRefresh = () => {
    setRefreshing(true);
    fetchUsers(1);
  };

  const handlePageChange = (page: number) => {
    setLoading(true);
    fetchUsers(page);
  };

  if (loading && !refreshing) {
    return <Loading />;
  }

  if (error) {
    return (
      <View style={styles.errorContainer}>
        <Text style={styles.errorText}>{error}</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <FlatList
        data={users}
        keyExtractor={(item) => item.id}
        renderItem={({ item }) => <UserCard user={item} />}
        contentContainerStyle={styles.list}
        refreshControl={
          <RefreshControl
            refreshing={refreshing}
            onRefresh={handleRefresh}
            colors={[COLORS.primary]}
            tintColor={COLORS.primary}
          />
        }
        ListEmptyComponent={
          <View style={styles.emptyContainer}>
            <Text style={styles.emptyText}>Nenhum usuário encontrado</Text>
          </View>
        }
        ListFooterComponent={
          totalPages > 1 ? (
            <Pagination
              currentPage={currentPage}
              totalPages={totalPages}
              onPageChange={handlePageChange}
            />
          ) : null
        }
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLORS.background,
  },
  list: {
    padding: 16,
  },
  errorContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 24,
    backgroundColor: COLORS.background,
  },
  errorText: {
    color: COLORS.danger,
    fontSize: 16,
    textAlign: 'center',
  },
  emptyContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    paddingVertical: 40,
  },
  emptyText: {
    color: COLORS.textSecondary,
    fontSize: 16,
  },
});
