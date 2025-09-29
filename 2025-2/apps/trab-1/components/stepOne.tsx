import { Card, TextInput } from "react-native-paper";

type StepOneProps = {
	dados: {
		nome: string;
		email: string;
	}
	function: (value: { nome: string; email: string }) => void;
}

export const StepOne = ({ dados, function: setDados }: StepOneProps) => {
	return (
		<Card>
			<Card.Content style={{ gap: 10 }}>
				<TextInput
					label="Nome"
					value={dados.nome}
					onChangeText={(text) => setDados({ ...dados, nome: text })}
				/>
				<TextInput
					label="Email"
					value={dados.email}
					onChangeText={(text) => setDados({ ...dados, email: text })}
				/>
			</Card.Content>
		</Card>
	);
};