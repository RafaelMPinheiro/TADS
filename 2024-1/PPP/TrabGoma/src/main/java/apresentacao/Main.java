package apresentacao;

import negocio.Maquina;
import negocio.MaquinaState;
import negocio.MaquinaSemMoeda;
import negocio.MaquinaComMoeda;
import negocio.MaquinaSemGoma;

public class Main {
    public static void main(String[] args) {
        // Teste para Maquina1
        Maquina maquina1 = new Maquina(5); // Inicia com 5 gomas
        MaquinaState estado = maquina1.getEstado();
        System.out.println(estado instanceof MaquinaSemMoeda);

        // Verifica se a máquina não entrega goma sem moeda
        maquina1.acionaAlavanca();
        System.out.println(maquina1.getQtdGomas() == 5);

        // Verifica a transição de estado ao inserir moeda
        maquina1.inserirMoeda();
        estado = maquina1.getEstado();
        System.out.println(estado instanceof MaquinaComMoeda);

        // Verifica a entrega de goma ao acionar a alavanca com moeda
        maquina1.acionaAlavanca();
        estado = maquina1.getEstado();
        System.out.println(estado instanceof MaquinaSemMoeda && maquina1.getQtdGomas() == 4);

        // Teste para Maquina2
        Maquina maquina2 = new Maquina(0); // Inicia sem gomas
        estado = maquina2.getEstado();
        System.out.println(estado instanceof MaquinaSemGoma);

        // Verifica que não é possível inserir moeda sem goma
        maquina2.inserirMoeda();
        estado = maquina2.getEstado();
        System.out.println(estado instanceof MaquinaSemGoma);

        // Verifica a transição de estado ao adicionar goma
        maquina2.adicionarGoma(3); // Adiciona 3 gomas
        estado = maquina2.getEstado();
        System.out.println(estado instanceof MaquinaSemMoeda);

        // Verifica se a máquina entrega goma após adicionar
        maquina2.inserirMoeda();
        maquina2.acionaAlavanca();
        estado = maquina2.getEstado();
        System.out.println(estado instanceof MaquinaSemMoeda && maquina2.getQtdGomas() == 2);

        // Teste para Maquina3
        Maquina maquina3 = new Maquina(3); // Inicia com 3 gomas
        estado = maquina3.getEstado();
        System.out.println(estado instanceof MaquinaSemMoeda);

        // Insere moeda e verifica a transição de estado
        maquina3.inserirMoeda();
        estado = maquina3.getEstado();
        System.out.println(estado instanceof MaquinaComMoeda);

        // Ejeta a moeda e verifica a transição de estado
        maquina3.ejetaMoeda();
        estado = maquina3.getEstado();
        System.out.println(estado instanceof MaquinaSemMoeda);

        // Insere moeda novamente e aciona a alavanca
        maquina3.inserirMoeda();
        maquina3.acionaAlavanca();
        estado = maquina3.getEstado();
        System.out.println(estado instanceof MaquinaSemMoeda && maquina3.getQtdGomas() == 2);
    }
}
