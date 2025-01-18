package negocio;

public class Image extends MarkdownDecorator {

    public Image(Markdown markdown, String alt, String url) {
        super(markdown);
        this.code = "!["+alt+"]("+url+")<br>";

    }

    // public Markdown removeItem(){
    //     return super.markdown;
    // }
}
