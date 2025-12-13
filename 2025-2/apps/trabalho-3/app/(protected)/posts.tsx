import React, { useEffect, useState, useCallback } from 'react';
import { View, FlatList, StyleSheet, Text, RefreshControl, TouchableOpacity } from 'react-native';
import { router, Stack } from 'expo-router';
import { COLORS } from '@/constants/colors';
import { PostCard, Loading, Pagination } from '@/components';
import { postService } from '@/services/api';
import { useAuth } from '@/contexts/AuthContext';
import { Post, PaginatedPosts } from '@/types';

const ITEMS_PER_PAGE = 10;

export default function PostsScreen() {
  const { user } = useAuth();
  const [posts, setPosts] = useState<Post[]>([]);
  const [loading, setLoading] = useState(true);
  const [refreshing, setRefreshing] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [error, setError] = useState<string | null>(null);

  const fetchPosts = useCallback(async (page: number = 1) => {
    try {
      setError(null);
      const response: PaginatedPosts = await postService.getPosts(page, ITEMS_PER_PAGE);
      setPosts(response.posts);
      setCurrentPage(response.page);
      setTotalPages(response.totalPages);
    } catch (err: any) {
      setError(err.message || 'Erro ao carregar posts');
    } finally {
      setLoading(false);
      setRefreshing(false);
    }
  }, []);

  useEffect(() => {
    fetchPosts();
  }, [fetchPosts]);

  const handleRefresh = () => {
    setRefreshing(true);
    fetchPosts(1);
  };

  const handlePageChange = (page: number) => {
    setLoading(true);
    fetchPosts(page);
  };

  const handlePostDeleted = () => {
    fetchPosts(currentPage);
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
      <Stack.Screen
        options={{
          headerLeft: () => (
            <TouchableOpacity
              onPress={() => router.push('/(protected)/users')}
              style={styles.usersButton}
            >
              <Text style={styles.usersButtonText}>Usuários</Text>
            </TouchableOpacity>
          ),
        }}
      />
      <FlatList
        data={posts}
        keyExtractor={(item) => item.id}
        renderItem={({ item }) => (
          <PostCard
            post={item}
            currentUserId={user?.id}
            onDelete={handlePostDeleted}
          />
        )}
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
            <Text style={styles.emptyText}>Nenhum post encontrado</Text>
            <Text style={styles.emptySubtext}>
              Seja o primeiro a criar um post!
            </Text>
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

      <TouchableOpacity
        style={styles.fab}
        onPress={() => router.push('/(protected)/create-post')}
        activeOpacity={0.8}
      >
        <Text style={styles.fabText}>+</Text>
      </TouchableOpacity>
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
    paddingBottom: 80,
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
    paddingVertical: 60,
  },
  emptyText: {
    color: COLORS.textSecondary,
    fontSize: 18,
    fontWeight: '600',
    marginBottom: 8,
  },
  emptySubtext: {
    color: COLORS.textLight,
    fontSize: 14,
  },
  fab: {
    position: 'absolute',
    right: 20,
    bottom: 20,
    width: 60,
    height: 60,
    borderRadius: 30,
    backgroundColor: COLORS.primary,
    justifyContent: 'center',
    alignItems: 'center',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.3,
    shadowRadius: 6,
    elevation: 8,
  },
  fabText: {
    color: '#fff',
    fontSize: 32,
    fontWeight: '300',
    lineHeight: 34,
  },
  usersButton: {
    paddingHorizontal: 12,
    paddingVertical: 6,
  },
  usersButtonText: {
    color: '#fff',
    fontSize: 14,
    fontWeight: '500',
  },
});
