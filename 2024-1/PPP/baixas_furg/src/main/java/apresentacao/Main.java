package apresentacao;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import negocio.GeradorBaixas;

public class Main {
    private static JFrame frame;
    private static JPanel panel;
    private static JLabel label_1;
    private static JFileChooser fileChooser;
    private static ArrayList<String> baixas;
    private static JList<String> list;
    private static JButton buttonGenerate;
    private static JLabel labelSuccess;

    public static void main(String[] args) {
        initializeUI();
        setupListeners();
    }

    private static void initializeUI() {
        frame = new JFrame("Gerador de baixas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        label_1 = new JLabel("Selecione o arquivo .csv");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(label_1, constraints);

        fileChooser = new JFileChooser();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(fileChooser, constraints);

        baixas = new ArrayList<>();
        list = new JList<>(baixas.toArray(new String[0]));
        JScrollPane scrollPane = new JScrollPane(list);
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, constraints);

        buttonGenerate = new JButton("Gerar");
        buttonGenerate.setPreferredSize(new Dimension(87, 28));
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(buttonGenerate, constraints);

        labelSuccess = new JLabel("Arquivo baixas.xlsx gerado com sucesso!");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        labelSuccess.setVisible(false);
        panel.add(labelSuccess, constraints);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void setupListeners() {
        fileChooser.addActionListener(e -> {
            if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                labelSuccess.setVisible(false);
                baixas.clear();
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                GeradorBaixas gerador = new GeradorBaixas(path, ";");
                try {
                    gerador.lerArquivo();
                    baixas.addAll(gerador.getLinhas());
                    list.setListData(baixas.toArray(new String[0]));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao ler o arquivo: " + ex.getMessage(),
                            "Erro de Leitura", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonGenerate.addActionListener(e -> {
            if (!baixas.isEmpty()) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                GeradorBaixas gerador = new GeradorBaixas(path, ";");
                try {
                    gerador.gerarArquivo();
                    labelSuccess.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao gerar o arquivo: " + ex.getMessage(),
                            "Erro de Geração", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Não há dados para gerar o arquivo.",
                        "Sem Dados", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}
