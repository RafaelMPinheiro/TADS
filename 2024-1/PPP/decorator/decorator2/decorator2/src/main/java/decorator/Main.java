package decorator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import negocio.*;


public class Main {
    public static void main(String[] args) {
        Markdown m1 = new DocumentMarkdown();
        m1 = new H1(m1, "Meu MD");
        m1 = new Image(m1, "dog", "https://pipz.com/static/images/blog/eddie.png");
        // m1 = m1.removeItem();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("meu_md.md"))) {
            writer.write(m1.getCode());    
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
