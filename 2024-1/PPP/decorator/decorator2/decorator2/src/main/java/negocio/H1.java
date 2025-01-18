package negocio;

public class H1 extends MarkdownDecorator {

    public H1(Markdown markdown, String title) {
        super(markdown);
        this.code = "# "+title+"<br>";
    }

    // public Markdown removeItem(){
    //     return super.markdown;
    // }



   
    
}
