package negocio;

import java.io.FileWriter;
import java.io.IOException;

public abstract class Beverage {
    protected String title;
    protected String content;

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public void build() {
        try {
            FileWriter myWriter = new FileWriter("index.html");
            myWriter.write("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "<meta charset=\"UTF-8\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0\">\n" +
                    "<title>" + this.getTitle() + "</title>\n" +
                    "</head>\n" +
                    "<body>" +
                    this.getContent() +
                    "\n</body>\n" +
                    "</html>");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
