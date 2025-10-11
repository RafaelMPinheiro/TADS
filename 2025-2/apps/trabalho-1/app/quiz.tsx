import { useLocalSearchParams, useRouter } from 'expo-router';
import { useEffect, useState } from 'react';
import { ScrollView, StyleSheet, TouchableOpacity, View } from 'react-native';

import { ThemedText } from '@/components/themed-text';
import { ThemedView } from '@/components/themed-view';
import { QUESTIONS } from '@/constants/questions';

export default function QuizScreen() {
	const router = useRouter();
	const params = useLocalSearchParams();
	const { name, email } = params;

	const [questions, setQuestions] = useState<typeof QUESTIONS>([]);
	const [currentQuestion, setCurrentQuestion] = useState(0);
	const [selectedAnswer, setSelectedAnswer] = useState<number | null>(null);
	const [answers, setAnswers] = useState<number[]>([]);

	const handleNext = () => {
		if (selectedAnswer === null) return;

		const newAnswers = [...answers, selectedAnswer];
		setAnswers(newAnswers);

		if (currentQuestion < questions.length - 1) {
			setCurrentQuestion(currentQuestion + 1);
			setSelectedAnswer(null);
		} else {
			const score = answers
				.filter((answer, index) => answer === questions[index].correctAnswer).length;

			router.push({
				pathname: '/result',
				params: {
					name,
					email,
					score: score.toString(),
				}
			});
		}
	};

	const getRandomQuestion = () => {
		const questions_temporary = [...QUESTIONS];
		const questions_selected = [];
		for (let i = 0; i < 5; i++) {
			const randomIndex = Math.floor(Math.random() * questions_temporary.length);
			questions_selected.push(questions_temporary[randomIndex]);
			questions_temporary.splice(randomIndex, 1);
		}
		setQuestions(questions_selected);
	};

	useEffect(() => {
		getRandomQuestion();
	}, []);

	const question = questions[currentQuestion];
	const progress = ((currentQuestion + 1) / questions.length) * 100;

	return (
		<ThemedView style={styles.container}>
			<ScrollView contentContainerStyle={styles.scrollContent}>
				{/* Header com progresso */}
				<View style={styles.header}>
					<ThemedText style={styles.questionNumber}>
						Pergunta {currentQuestion + 1} de {questions.length}
					</ThemedText>
					<View style={styles.progressBar}>
						<View style={[styles.progressFill, { width: `${progress}%` }]} />
					</View>
				</View>

				{question && (
					<>
						{/* Pergunta */}
						< View style={styles.questionContainer}>
							<ThemedText type="title" style={styles.question}>
								{question.question}
							</ThemedText>
						</View>

						{/* Opções */}
						<View style={styles.optionsContainer}>
							{question.options.map((option, index) => (
								<TouchableOpacity
									key={index}
									style={[
										styles.option,
										selectedAnswer === index && styles.optionSelected
									]}
									onPress={() => setSelectedAnswer(index)}
								>
									<View style={styles.optionBullet}>
										<ThemedText style={styles.optionBulletText}>
											{String.fromCharCode(65 + index)}
										</ThemedText>
									</View>
									<ThemedText style={styles.optionText}>{option}</ThemedText>
								</TouchableOpacity>
							))}
						</View>

						{/* Botão Próxima */}
						<TouchableOpacity
							style={[
								styles.button,
								selectedAnswer === null && styles.buttonDisabled
							]}
							onPress={handleNext}
							disabled={selectedAnswer === null}
						>
							<ThemedText style={styles.buttonText}>
								{currentQuestion < questions.length - 1 ? 'Próxima' : 'Finalizar'}
							</ThemedText>
						</TouchableOpacity>
					</>
				)}
			</ScrollView>
		</ThemedView >
	);
}

const styles = StyleSheet.create({
	container: {
		flex: 1,
	},
	scrollContent: {
		flexGrow: 1,
		padding: 20,
		paddingTop: 60,
	},
	header: {
		marginBottom: 30,
	},
	questionNumber: {
		fontSize: 16,
		marginBottom: 10,
		opacity: 0.7,
	},
	progressBar: {
		height: 8,
		backgroundColor: '#e0e0e0',
		borderRadius: 4,
		overflow: 'hidden',
	},
	progressFill: {
		height: '100%',
		backgroundColor: '#007AFF',
		borderRadius: 4,
	},
	questionContainer: {
		marginBottom: 30,
	},
	question: {
		fontSize: 24,
		lineHeight: 32,
	},
	optionsContainer: {
		marginBottom: 30,
	},
	option: {
		flexDirection: 'row',
		alignItems: 'center',
		backgroundColor: '#f5f5f5',
		padding: 16,
		borderRadius: 12,
		marginBottom: 12,
		borderWidth: 2,
		borderColor: 'transparent',
	},
	optionSelected: {
		borderColor: '#007AFF',
		backgroundColor: '#e3f2fd',
	},
	optionBullet: {
		width: 32,
		height: 32,
		borderRadius: 16,
		backgroundColor: '#007AFF',
		alignItems: 'center',
		justifyContent: 'center',
		marginRight: 12,
	},
	optionBulletText: {
		color: '#fff',
		fontWeight: 'bold',
		fontSize: 16,
	},
	optionText: {
		fontSize: 16,
		flex: 1,
		color: '#000',
	},
	button: {
		backgroundColor: '#007AFF',
		padding: 16,
		borderRadius: 8,
		alignItems: 'center',
		marginTop: 20,
	},
	buttonDisabled: {
		backgroundColor: '#ccc',
		opacity: 0.5,
	},
	buttonText: {
		color: '#fff',
		fontSize: 18,
		fontWeight: 'bold',
	},
});
