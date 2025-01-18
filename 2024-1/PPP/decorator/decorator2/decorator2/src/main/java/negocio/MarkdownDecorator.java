package negocio;

public abstract class MarkdownDecorator extends Markdown  {

    protected Markdown markdown;

    public MarkdownDecorator(Markdown markdown){        
        this.markdown = markdown;
    }

    public Markdown removeItem(){
        return this.markdown;
    }
    
    public String getCode(){
        return this.markdown.getCode()+"\n"+this.code;
    }

   



}
