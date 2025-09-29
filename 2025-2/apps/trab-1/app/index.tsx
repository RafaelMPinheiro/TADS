import { StepOne } from '@/components/stepOne';
import { StepThree } from '@/components/stepThree';
import { StepTwo } from '@/components/stepTwo';
import { useState } from 'react';
import { Keyboard, StyleSheet, Text, TouchableWithoutFeedback, View } from 'react-native';
import { Button } from 'react-native-paper';

export default function ModalScreen() {
  const [step, setStep] = useState(1);
  const [dados, setDados] = useState({
    nome: '',
    email: ''
  });

  const handleStepChange = (newStep: number) => {
    if (newStep < 1 || newStep > 3) return;
    setStep(newStep);
  }

  const renderStep = () => {
    switch (step) {
      case 1:
        return <StepOne dados={dados} function={setDados} />;
      case 2:
        return <StepTwo />;
      case 3:
        return <StepThree />;
      default:
        return null;
    }
  };

  return (
    <TouchableWithoutFeedback onPress={Keyboard.dismiss} accessible={false}>
      <View style={styles.container}>
        <View style={styles.box}>
          {renderStep()}
        </View>
        <View style={styles.footer}>
          <Button
            mode='contained'
            style={styles.button}
            onPress={() => handleStepChange(step - 1)}
          >
            <Text>Voltar</Text>
          </Button>
          <Text>Current Step: {step}</Text>
          <Button
            mode='contained'
            onPress={() => handleStepChange(step + 1)}
          >
            <Text>Avançar</Text>
          </Button>
        </View>
      </View>
    </TouchableWithoutFeedback>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 30,
    paddingBottom: 60,
  },
  box: {
    flex: 1,
    justifyContent: 'center',
  },
  button: {
    minWidth: 100,
  },
  footer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    width: '100%',
    gap: 10,
  }
});
