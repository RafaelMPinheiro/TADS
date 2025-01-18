package negocio;

public class Image extends TagDecorator {
    public Image(Beverage beverage, String url, String alt) {
        super(beverage);
        this.content = "<img src=\"" + url + "\" alt=\"" + alt + "\">";
    }
}