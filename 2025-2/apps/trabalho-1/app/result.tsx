import { useLocalSearchParams, useRouter } from 'expo-router';
import { StyleSheet, TouchableOpacity, View } from 'react-native';

import { ThemedText } from '@/components/themed-text';
import { ThemedView } from '@/components/themed-view';

export default function ResultScreen() {
	const router = useRouter();
	const params = useLocalSearchParams();
	const { name, email, score } = params;

	const percentage = (Number(score) / 5) * 100;

	const getMessage = () => {
		switch (score) {
			case '0':
				return 'Não desanime! Tente novamente para melhorar sua pontuação.';
			case '1':
				return 'Você pode fazer melhor! Continue praticando.';
			case '2':
				return 'Bom esforço! Você está no caminho certo.';
			case '3':
				return 'Ótimo trabalho! Você tem um bom conhecimento.';
			case '4':
				return 'Excelente! Quase lá, só mais um passo!';
			case '5':
				return 'Perfeito! Você é um mestre do quiz!';
			default:
				return 'Parabéns!';
		}
	};

	const handleRestart = () => {
		router.push('/');
	};

	return (
		<ThemedView style={styles.container}>
			<View style={styles.content}>
				<View style={styles.iconContainer}>
					<ThemedText style={styles.icon}>✓</ThemedText>
				</View>

				<ThemedText type="title" style={styles.title}>
					Quiz Completado!
				</ThemedText>

				<View style={styles.userInfo}>
					<ThemedText style={styles.label}>Nome:</ThemedText>
					<ThemedText style={styles.value}>{name}</ThemedText>

					<ThemedText style={styles.label}>Email:</ThemedText>
					<ThemedText style={styles.value}>{email}</ThemedText>
				</View>

				<View style={styles.scoreContainer}>
					<ThemedText style={styles.scoreLabel}>Sua Nota</ThemedText>
					<ThemedText style={styles.scoreValue}>
						{score} / 5
					</ThemedText>
					<ThemedText style={styles.percentage}>
						{percentage}%
					</ThemedText>
				</View>

				<View style={styles.messageContainer}>
					<ThemedText style={styles.message}>
						{getMessage()}
					</ThemedText>
				</View>

				<View style={styles.actions}>
					<TouchableOpacity
						style={styles.button}
						onPress={handleRestart}
					>
						<ThemedText style={styles.buttonText}>
							Fazer Novamente
						</ThemedText>
					</TouchableOpacity>
				</View>
			</View>
		</ThemedView>
	);
}

const styles = StyleSheet.create({
	container: {
		flex: 1,
	},
	content: {
		flex: 1,
		alignItems: 'center',
		justifyContent: 'center',
		padding: 20,
	},
	iconContainer: {
		width: 100,
		height: 100,
		borderRadius: 50,
		backgroundColor: '#4CAF50',
		alignItems: 'center',
		justifyContent: 'center',
		marginBottom: 30,
	},
	icon: {
		fontSize: 60,
		color: '#fff',
		lineHeight: 70,
	},
	title: {
		marginBottom: 30,
		textAlign: 'center',
	},
	userInfo: {
		width: '100%',
		maxWidth: 400,
		backgroundColor: '#f5f5f5',
		padding: 20,
		borderRadius: 12,
		marginBottom: 30,
	},
	label: {
		fontSize: 14,
		opacity: 0.7,
		marginBottom: 4,
		color: '#000',
	},
	value: {
		fontSize: 18,
		fontWeight: '600',
		marginBottom: 16,
		color: '#000',
	},
	scoreContainer: {
		alignItems: 'center',
		marginBottom: 30,
	},
	scoreLabel: {
		fontSize: 16,
		opacity: 0.7,
		marginBottom: 10,
	},
	scoreValue: {
		fontSize: 48,
		fontWeight: 'bold',
		color: '#007AFF',
		lineHeight: 56,
	},
	percentage: {
		fontSize: 24,
		opacity: 0.7,
		marginTop: 5,
	},
	messageContainer: {
		marginBottom: 30,
		paddingHorizontal: 20,
	},
	message: {
		fontSize: 18,
		textAlign: 'center',
		lineHeight: 24,
	},
	actions: {
		width: '100%',
		maxWidth: 400,
	},
	button: {
		backgroundColor: '#007AFF',
		padding: 16,
		borderRadius: 8,
		alignItems: 'center',
	},
	buttonText: {
		color: '#fff',
		fontSize: 18,
		fontWeight: 'bold',
	},
});
