package personagens;

public class Rainha extends Personagem {
    public Rainha() {
        this.nome = "Rainha";
    }

    @Override
    public int getForca() {
        return 8;
    }

    @Override
    public int getPontaria() {
        return 12;
    }
}