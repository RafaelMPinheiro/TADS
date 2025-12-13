import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  KeyboardAvoidingView,
  Platform,
  ScrollView,
  Image,
  TouchableOpacity,
  Alert,
} from 'react-native';
import { router } from 'expo-router';
import * as ImagePicker from 'expo-image-picker';
import { COLORS } from '@/constants/colors';
import { Button, Input } from '@/components';
import { postService } from '@/services/api';

export default function CreatePostScreen() {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [image, setImage] = useState<ImagePicker.ImagePickerAsset | null>(null);
  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState<{
    title?: string;
    content?: string;
    image?: string;
  }>({});

  const pickImage = async () => {
    const permissionResult = await ImagePicker.requestMediaLibraryPermissionsAsync();

    if (!permissionResult.granted) {
      Alert.alert(
        'Permissão Necessária',
        'É necessário permitir acesso à galeria para selecionar uma foto.'
      );
      return;
    }

    const result = await ImagePicker.launchImageLibraryAsync({
      mediaTypes: ['images'],
      allowsEditing: true,
      aspect: [16, 9],
      quality: 0.8,
    });

    if (!result.canceled && result.assets[0]) {
      setImage(result.assets[0]);
      setErrors((prev) => ({ ...prev, image: undefined }));
    }
  };

  const validate = () => {
    const newErrors: typeof errors = {};

    if (!title.trim()) {
      newErrors.title = 'Título é obrigatório';
    } else if (title.trim().length < 3) {
      newErrors.title = 'Título deve ter pelo menos 3 caracteres';
    }

    if (!content.trim()) {
      newErrors.content = 'Conteúdo é obrigatório';
    } else if (content.trim().length < 10) {
      newErrors.content = 'Conteúdo deve ter pelo menos 10 caracteres';
    }

    if (!image) {
      newErrors.image = 'Selecione uma imagem';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async () => {
    if (!validate() || !image) return;

    setLoading(true);
    try {
      const formData = new FormData();
      formData.append('title', title.trim());
      formData.append('content', content.trim());

      // Preparar a imagem para envio
      const imageUri = image.uri;
      const filename = imageUri.split('/').pop() || 'photo.jpg';
      const match = /\.(\w+)$/.exec(filename);
      const type = match ? `image/${match[1]}` : 'image/jpeg';

      formData.append('photo', {
        uri: imageUri,
        name: filename,
        type,
      } as any);

      await postService.createPost(formData);

      Alert.alert('Sucesso', 'Post criado com sucesso!', [
        { text: 'OK', onPress: () => router.back() },
      ]);
    } catch (error: any) {
      Alert.alert('Erro', error.message || 'Não foi possível criar o post');
    } finally {
      setLoading(false);
    }
  };

  return (
    <KeyboardAvoidingView
      style={styles.container}
      behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
    >
      <ScrollView
        contentContainerStyle={styles.scrollContent}
        keyboardShouldPersistTaps="handled"
      >
        <Input
          label="Título"
          placeholder="Título do post"
          value={title}
          onChangeText={setTitle}
          error={errors.title}
        />

        <Input
          label="Conteúdo"
          placeholder="Escreva o conteúdo do seu post..."
          value={content}
          onChangeText={setContent}
          multiline
          numberOfLines={6}
          style={styles.textArea}
          error={errors.content}
        />

        <View style={styles.imageSection}>
          <Text style={styles.label}>Foto</Text>
          <TouchableOpacity
            style={[styles.imagePicker, errors.image && styles.imagePickerError]}
            onPress={pickImage}
          >
            {image ? (
              <Image source={{ uri: image.uri }} style={styles.previewImage} />
            ) : (
              <View style={styles.placeholder}>
                <Text style={styles.placeholderIcon}>📷</Text>
                <Text style={styles.placeholderText}>
                  Toque para selecionar uma foto
                </Text>
              </View>
            )}
          </TouchableOpacity>
          {errors.image && <Text style={styles.errorText}>{errors.image}</Text>}
        </View>

        <Button
          title="Publicar Post"
          onPress={handleSubmit}
          loading={loading}
          style={styles.submitButton}
        />
      </ScrollView>
    </KeyboardAvoidingView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLORS.background,
  },
  scrollContent: {
    padding: 24,
    paddingBottom: 40,
  },
  textArea: {
    height: 120,
    textAlignVertical: 'top',
    paddingTop: 14,
  },
  imageSection: {
    marginBottom: 16,
  },
  label: {
    fontSize: 14,
    fontWeight: '600',
    color: COLORS.text,
    marginBottom: 8,
  },
  imagePicker: {
    width: '100%',
    height: 200,
    borderRadius: 12,
    borderWidth: 2,
    borderColor: COLORS.border,
    borderStyle: 'dashed',
    overflow: 'hidden',
    backgroundColor: COLORS.inputBackground,
  },
  imagePickerError: {
    borderColor: COLORS.danger,
  },
  previewImage: {
    width: '100%',
    height: '100%',
    resizeMode: 'cover',
  },
  placeholder: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  placeholderIcon: {
    fontSize: 40,
    marginBottom: 8,
  },
  placeholderText: {
    color: COLORS.textSecondary,
    fontSize: 14,
  },
  errorText: {
    color: COLORS.danger,
    fontSize: 12,
    marginTop: 4,
  },
  submitButton: {
    marginTop: 16,
  },
});
