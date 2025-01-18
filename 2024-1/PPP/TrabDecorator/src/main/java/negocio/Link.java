package negocio;

public class Link extends TagDecorator {

    public Link(Beverage beverage, String url, String title) {
        super(beverage);
        this.content = "<a href=\"" + url + "\">" + title + "</a>";
    }

}
