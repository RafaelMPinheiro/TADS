import { useEffect, useState } from "react";
import { FlatList, Text, View } from "react-native";
import { Button, Card } from "react-native-paper";

const questions = [
	{
		"pergunta": "Qual linguagem é usada principalmente para estruturar páginas da web?",
		"respostas": [
			{ "resposta": "HTML", "correta": true },
			{ "resposta": "Python", "correta": false },
			{ "resposta": "C++", "correta": false },
			{ "resposta": "Java", "correta": false }
		]
	},
	{
		"pergunta": "Qual linguagem é usada para estilizar páginas da web?",
		"respostas": [
			{ "resposta": "CSS", "correta": true },
			{ "resposta": "JavaScript", "correta": false },
			{ "resposta": "PHP", "correta": false },
			{ "resposta": "HTML", "correta": false }
		]
	},
	{
		"pergunta": "Qual linguagem é usada para adicionar interatividade em páginas web?",
		"respostas": [
			{ "resposta": "JavaScript", "correta": true },
			{ "resposta": "HTML", "correta": false },
			{ "resposta": "CSS", "correta": false },
			{ "resposta": "SQL", "correta": false }
		]
	},
	{
		"pergunta": "O que significa a sigla SQL?",
		"respostas": [
			{ "resposta": "Structured Query Language", "correta": true },
			{ "resposta": "Simple Question Line", "correta": false },
			{ "resposta": "Standard Query Level", "correta": false },
			{ "resposta": "System Queue Language", "correta": false }
		]
	},
	{
		"pergunta": "Qual símbolo é usado para comentários de linha única em Python?",
		"respostas": [
			{ "resposta": "#", "correta": true },
			{ "resposta": "//", "correta": false },
			{ "resposta": "/* */", "correta": false },
			{ "resposta": "<!-- -->", "correta": false }
		]
	},
	{
		"pergunta": "Qual destes é um loop em JavaScript?",
		"respostas": [
			{ "resposta": "for", "correta": true },
			{ "resposta": "repeat", "correta": false },
			{ "resposta": "foreach", "correta": false },
			{ "resposta": "loop", "correta": false }
		]
	},
	{
		"pergunta": "Em qual linguagem usamos 'print()' para exibir algo na tela?",
		"respostas": [
			{ "resposta": "Python", "correta": true },
			{ "resposta": "C", "correta": false },
			{ "resposta": "Java", "correta": false },
			{ "resposta": "Assembly", "correta": false }
		]
	},
	{
		"pergunta": "Qual tag HTML é usada para títulos?",
		"respostas": [
			{ "resposta": "<h1>", "correta": true },
			{ "resposta": "<title>", "correta": false },
			{ "resposta": "<head>", "correta": false },
			{ "resposta": "<header>", "correta": false }
		]
	},
	{
		"pergunta": "Qual operador lógico representa 'E' em muitas linguagens?",
		"respostas": [
			{ "resposta": "&&", "correta": true },
			{ "resposta": "||", "correta": false },
			{ "resposta": "!", "correta": false },
			{ "resposta": "&", "correta": false }
		]
	},
	{
		"pergunta": "Qual é a extensão padrão de arquivos JavaScript?",
		"respostas": [
			{ "resposta": ".js", "correta": true },
			{ "resposta": ".java", "correta": false },
			{ "resposta": ".jsx", "correta": false },
			{ "resposta": ".script", "correta": false }
		]
	},
	{
		"pergunta": "Em programação, uma variável é usada para?",
		"respostas": [
			{ "resposta": "Armazenar valores", "correta": true },
			{ "resposta": "Executar funções", "correta": false },
			{ "resposta": "Definir classes", "correta": false },
			{ "resposta": "Estilizar textos", "correta": false }
		]
	},
	{
		"pergunta": "Qual destes é um tipo de dado em JavaScript?",
		"respostas": [
			{ "resposta": "String", "correta": true },
			{ "resposta": "Texto", "correta": false },
			{ "resposta": "Caractere", "correta": false },
			{ "resposta": "Palavra", "correta": false }
		]
	},
	{
		"pergunta": "Qual palavra-chave é usada para criar uma função em JavaScript?",
		"respostas": [
			{ "resposta": "function", "correta": true },
			{ "resposta": "func", "correta": false },
			{ "resposta": "method", "correta": false },
			{ "resposta": "def", "correta": false }
		]
	},
	{
		"pergunta": "Qual é a função principal do CSS?",
		"respostas": [
			{ "resposta": "Definir estilos visuais", "correta": true },
			{ "resposta": "Processar dados", "correta": false },
			{ "resposta": "Executar lógica", "correta": false },
			{ "resposta": "Criar bancos de dados", "correta": false }
		]
	},
	{
		"pergunta": "Em Python, como declaramos uma lista?",
		"respostas": [
			{ "resposta": "[]", "correta": true },
			{ "resposta": "{}", "correta": false },
			{ "resposta": "<>", "correta": false },
			{ "resposta": "()", "correta": false }
		]
	},
	{
		"pergunta": "Qual destas é uma linguagem de programação tipada?",
		"respostas": [
			{ "resposta": "Java", "correta": true },
			{ "resposta": "HTML", "correta": false },
			{ "resposta": "CSS", "correta": false },
			{ "resposta": "Markdown", "correta": false }
		]
	},
	{
		"pergunta": "Qual comando Git é usado para clonar repositórios?",
		"respostas": [
			{ "resposta": "git clone", "correta": true },
			{ "resposta": "git copy", "correta": false },
			{ "resposta": "git fork", "correta": false },
			{ "resposta": "git init", "correta": false }
		]
	},
	{
		"pergunta": "Qual destas linguagens é orientada a objetos?",
		"respostas": [
			{ "resposta": "Java", "correta": true },
			{ "resposta": "HTML", "correta": false },
			{ "resposta": "CSS", "correta": false },
			{ "resposta": "SQL", "correta": false }
		]
	},
	{
		"pergunta": "Em JavaScript, qual palavra-chave é usada para declarar variáveis mutáveis?",
		"respostas": [
			{ "resposta": "let", "correta": true },
			{ "resposta": "const", "correta": false },
			{ "resposta": "var", "correta": false },
			{ "resposta": "mutable", "correta": false }
		]
	},
	{
		"pergunta": "O que significa IDE em programação?",
		"respostas": [
			{ "resposta": "Integrated Development Environment", "correta": true },
			{ "resposta": "Internal Data Execution", "correta": false },
			{ "resposta": "Interactive Design Editor", "correta": false },
			{ "resposta": "Internet Development Engine", "correta": false }
		]
	},
	{
		"pergunta": "Qual comando imprime no console em JavaScript?",
		"respostas": [
			{ "resposta": "console.log()", "correta": true },
			{ "resposta": "print()", "correta": false },
			{ "resposta": "echo()", "correta": false },
			{ "resposta": "out()", "correta": false }
		]
	},
	{
		"pergunta": "Qual destes é um banco de dados relacional?",
		"respostas": [
			{ "resposta": "MySQL", "correta": true },
			{ "resposta": "MongoDB", "correta": false },
			{ "resposta": "Redis", "correta": false },
			{ "resposta": "Firebase", "correta": false }
		]
	},
	{
		"pergunta": "Qual destes é um framework front-end?",
		"respostas": [
			{ "resposta": "React", "correta": true },
			{ "resposta": "Node.js", "correta": false },
			{ "resposta": "Django", "correta": false },
			{ "resposta": "Spring", "correta": false }
		]
	},
	{
		"pergunta": "Qual comando cria um repositório Git?",
		"respostas": [
			{ "resposta": "git init", "correta": true },
			{ "resposta": "git start", "correta": false },
			{ "resposta": "git new", "correta": false },
			{ "resposta": "git create", "correta": false }
		]
	},
	{
		"pergunta": "Qual destes é um tipo de dado em Python?",
		"respostas": [
			{ "resposta": "int", "correta": true },
			{ "resposta": "number", "correta": false },
			{ "resposta": "digit", "correta": false },
			{ "resposta": "num", "correta": false }
		]
	},
	{
		"pergunta": "Em HTML, qual tag é usada para links?",
		"respostas": [
			{ "resposta": "<a>", "correta": true },
			{ "resposta": "<link>", "correta": false },
			{ "resposta": "<href>", "correta": false },
			{ "resposta": "<url>", "correta": false }
		]
	},
	{
		"pergunta": "Em Python, como se define uma função?",
		"respostas": [
			{ "resposta": "def", "correta": true },
			{ "resposta": "function", "correta": false },
			{ "resposta": "func", "correta": false },
			{ "resposta": "method", "correta": false }
		]
	},
	{
		"pergunta": "Qual destes é um operador de comparação?",
		"respostas": [
			{ "resposta": "==", "correta": true },
			{ "resposta": "+", "correta": false },
			{ "resposta": "*", "correta": false },
			{ "resposta": "/", "correta": false }
		]
	},
	{
		"pergunta": "Qual destes é um framework back-end?",
		"respostas": [
			{ "resposta": "Django", "correta": true },
			{ "resposta": "React", "correta": false },
			{ "resposta": "Angular", "correta": false },
			{ "resposta": "Vue", "correta": false }
		]
	},
	{
		"pergunta": "Qual destes é um sistema de controle de versão?",
		"respostas": [
			{ "resposta": "Git", "correta": true },
			{ "resposta": "Node.js", "correta": false },
			{ "resposta": "NPM", "correta": false },
			{ "resposta": "Composer", "correta": false }
		]
	},
	{
		"pergunta": "Qual comando em Python pausa o programa esperando entrada?",
		"respostas": [
			{ "resposta": "input()", "correta": true },
			{ "resposta": "scan()", "correta": false },
			{ "resposta": "read()", "correta": false },
			{ "resposta": "get()", "correta": false }
		]
	},
	{
		"pergunta": "Qual destes é usado para versionar pacotes em Node.js?",
		"respostas": [
			{ "resposta": "npm", "correta": true },
			{ "resposta": "pip", "correta": false },
			{ "resposta": "composer", "correta": false },
			{ "resposta": "gem", "correta": false }
		]
	},
	{
		"pergunta": "Qual extensão é usada em arquivos Python?",
		"respostas": [
			{ "resposta": ".py", "correta": true },
			{ "resposta": ".java", "correta": false },
			{ "resposta": ".js", "correta": false },
			{ "resposta": ".c", "correta": false }
		]
	},
	{
		"pergunta": "Qual linguagem é executada no navegador?",
		"respostas": [
			{ "resposta": "JavaScript", "correta": true },
			{ "resposta": "Python", "correta": false },
			{ "resposta": "Java", "correta": false },
			{ "resposta": "C#", "correta": false }
		]
	},
	{
		"pergunta": "Em bancos de dados, o que significa CRUD?",
		"respostas": [
			{ "resposta": "Create, Read, Update, Delete", "correta": true },
			{ "resposta": "Control, Run, Upload, Download", "correta": false },
			{ "resposta": "Create, Remove, Update, Deploy", "correta": false },
			{ "resposta": "Control, Read, Use, Debug", "correta": false }
		]
	},
	{
		"pergunta": "Qual destes é um operador aritmético?",
		"respostas": [
			{ "resposta": "+", "correta": true },
			{ "resposta": "==", "correta": false },
			{ "resposta": "&&", "correta": false },
			{ "resposta": "||", "correta": false }
		]
	},
	{
		"pergunta": "Qual linguagem é fortemente usada em inteligência artificial?",
		"respostas": [
			{ "resposta": "Python", "correta": true },
			{ "resposta": "HTML", "correta": false },
			{ "resposta": "PHP", "correta": false },
			{ "resposta": "C", "correta": false }
		]
	},
	{
		"pergunta": "Em JavaScript, qual operador concatena strings?",
		"respostas": [
			{ "resposta": "+", "correta": true },
			{ "resposta": "&", "correta": false },
			{ "resposta": "*", "correta": false },
			{ "resposta": "%", "correta": false }
		]
	},
	{
		"pergunta": "Qual destes é usado para gerenciar pacotes em Python?",
		"respostas": [
			{ "resposta": "pip", "correta": true },
			{ "resposta": "npm", "correta": false },
			{ "resposta": "composer", "correta": false },
			{ "resposta": "gradle", "correta": false }
		]
	},
	{
		"pergunta": "Qual comando exibe o diretório atual no terminal?",
		"respostas": [
			{ "resposta": "pwd", "correta": true },
			{ "resposta": "ls", "correta": false },
			{ "resposta": "cd", "correta": false },
			{ "resposta": "dir", "correta": false }
		]
	},
	{
		"pergunta": "Qual destes é um loop em Python?",
		"respostas": [
			{ "resposta": "while", "correta": true },
			{ "resposta": "loop", "correta": false },
			{ "resposta": "repeat", "correta": false },
			{ "resposta": "until", "correta": false }
		]
	},
	{
		"pergunta": "Qual é o resultado de 5 % 2 em Python?",
		"respostas": [
			{ "resposta": "1", "correta": true },
			{ "resposta": "2", "correta": false },
			{ "resposta": "0", "correta": false },
			{ "resposta": "2.5", "correta": false }
		]
	},
	{
		"pergunta": "Qual destes é um sistema de gerenciamento de banco de dados?",
		"respostas": [
			{ "resposta": "PostgreSQL", "correta": true },
			{ "resposta": "Node.js", "correta": false },
			{ "resposta": "React", "correta": false },
			{ "resposta": "Angular", "correta": false }
		]
	},
	{
		"pergunta": "Qual tag é usada para parágrafos em HTML?",
		"respostas": [
			{ "resposta": "<p>", "correta": true },
			{ "resposta": "<br>", "correta": false },
			{ "resposta": "<span>", "correta": false },
			{ "resposta": "<div>", "correta": false }
		]
	},
	{
		"pergunta": "Qual destes é usado para versionamento de código?",
		"respostas": [
			{ "resposta": "GitHub", "correta": true },
			{ "resposta": "Slack", "correta": false },
			{ "resposta": "Figma", "correta": false },
			{ "resposta": "Trello", "correta": false }
		]
	},
	{
		"pergunta": "Qual destes é um tipo de dado booleano?",
		"respostas": [
			{ "resposta": "True", "correta": true },
			{ "resposta": "Yes", "correta": false },
			{ "resposta": "On", "correta": false },
			{ "resposta": "1", "correta": false }
		]
	},
	{
		"pergunta": "Qual linguagem é fortemente usada no desenvolvimento Android?",
		"respostas": [
			{ "resposta": "Kotlin", "correta": true },
			{ "resposta": "Swift", "correta": false },
			{ "resposta": "JavaScript", "correta": false },
			{ "resposta": "C#", "correta": false }
		]
	},
	{
		"pergunta": "Qual linguagem é usada no desenvolvimento iOS?",
		"respostas": [
			{ "resposta": "Swift", "correta": true },
			{ "resposta": "Kotlin", "correta": false },
			{ "resposta": "Java", "correta": false },
			{ "resposta": "Dart", "correta": false }
		]
	},
	{
		"pergunta": "Qual destes é usado para construir aplicações cross-platform?",
		"respostas": [
			{ "resposta": "Flutter", "correta": true },
			{ "resposta": "Angular", "correta": false },
			{ "resposta": "Vue", "correta": false },
			{ "resposta": "Laravel", "correta": false }
		]
	}
];

export const StepTwo = () => {
	const [questionsSelected, setQuestionsSelected] = useState<typeof questions>();

	useEffect(() => {
		let selecteds: typeof questions = [];
		for (let i = 0; i < 3; i++) {
			const randomIndex = Math.floor(Math.random() * questions.length);
			selecteds.push(questions[randomIndex]);
		}
		setQuestionsSelected(selecteds);
	}, []);

	return (
		<FlatList
			data={questionsSelected}
			renderItem={({ item }) => <Item pergunta={item.pergunta} respostas={item.respostas} />}
			keyExtractor={item => Math.random().toString()}
			ItemSeparatorComponent={() => <View style={{ height: 10 }} />}
			style={{ flex: 1 }}
		/>
	);
};

const Item = (props: any) => (
	<Card>
		<Card.Content>
			<Text>{props.pergunta}</Text>
			{props.respostas.map((resposta: any) => (
				<Button key={Math.random().toString()} mode="outlined" style={{ marginTop: 5 }}>
					{resposta.resposta}
				</Button>
			))}
		</Card.Content>
	</Card>
);