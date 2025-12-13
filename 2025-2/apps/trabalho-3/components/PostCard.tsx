import React from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity, Alert } from 'react-native';
import { COLORS } from '@/constants/colors';
import { Post } from '@/types';
import { postService } from '@/services/api';

interface PostCardProps {
  post: Post;
  currentUserId?: string;
  onDelete?: () => void;
}

export function PostCard({ post, currentUserId, onDelete }: PostCardProps) {
  const isOwner = currentUserId === post.userId;

  const handleDelete = () => {
    Alert.alert(
      'Excluir Post',
      'Tem certeza que deseja excluir este post?',
      [
        { text: 'Cancelar', style: 'cancel' },
        {
          text: 'Excluir',
          style: 'destructive',
          onPress: async () => {
            try {
              await postService.deletePost(post.id);
              onDelete?.();
            } catch (error) {
              Alert.alert('Erro', 'Não foi possível excluir o post');
            }
          },
        },
      ]
    );
  };

  return (
    <View style={styles.card}>
      {post.photo && (
        <Image
          source={{ uri: post.photo }}
          style={styles.image}
          resizeMode="cover"
        />
      )}
      <View style={styles.content}>
        <Text style={styles.title}>{post.title}</Text>
        <Text style={styles.description} numberOfLines={3}>
          {post.content}
        </Text>
        {isOwner && (
          <TouchableOpacity style={styles.deleteButton} onPress={handleDelete}>
            <Text style={styles.deleteText}>Excluir</Text>
          </TouchableOpacity>
        )}
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  card: {
    backgroundColor: COLORS.surface,
    borderRadius: 12,
    marginBottom: 16,
    overflow: 'hidden',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.08,
    shadowRadius: 8,
    elevation: 3,
  },
  image: {
    width: '100%',
    height: 200,
  },
  content: {
    padding: 16,
  },
  title: {
    fontSize: 18,
    fontWeight: '700',
    color: COLORS.text,
    marginBottom: 8,
  },
  description: {
    fontSize: 14,
    color: COLORS.textSecondary,
    lineHeight: 20,
  },
  deleteButton: {
    marginTop: 12,
    alignSelf: 'flex-end',
    paddingHorizontal: 16,
    paddingVertical: 8,
    backgroundColor: COLORS.danger,
    borderRadius: 8,
  },
  deleteText: {
    color: '#fff',
    fontSize: 14,
    fontWeight: '600',
  },
});
