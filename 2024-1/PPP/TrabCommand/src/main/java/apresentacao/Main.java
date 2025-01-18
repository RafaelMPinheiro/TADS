package apresentacao;

import games.*;
import negocio.*;

public class Main {
    public static void main(String[] args) {
        Fifa fifa22 = new Fifa("2022");
        StreetFighter streetFighter = new StreetFighter(6);
        NeedforSpeed needforSpeed = new NeedforSpeed("2014");

        FifaCommand fifaCommand = new FifaCommand(fifa22);
        StreetFighterCommand streetFighterCommand = new StreetFighterCommand(streetFighter);
        NeedforSpeedCommand needforSpeedCommand = new NeedforSpeedCommand(needforSpeed);

        Controle controle = new Controle();
        controle.setGame(0, fifaCommand);
        controle.setGame(1, streetFighterCommand);
        controle.setGame(2, needforSpeedCommand);

        for (int i = 0; i < 3; i++) {
            controle.buttonAPressed(i);
            controle.buttonBPressed(i);
        }
    }
}