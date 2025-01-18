package personagens;

public class Rei extends Personagem {
    public Rei(){
        this.nome = "Rei";
    }

    @Override
    public int getForca() {
        return 10;
    }

    @Override
    public int getPontaria() {
        return 10;
    }
}