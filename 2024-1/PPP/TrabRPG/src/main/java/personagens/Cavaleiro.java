package personagens;

public class Cavaleiro extends Personagem {
    public Cavaleiro(){
        this.nome = "Cavaleiro";
    }

    @Override
    public int getForca() {
        return 12;
    }

    @Override
    public int getPontaria() {
        return 8;
    }
}
