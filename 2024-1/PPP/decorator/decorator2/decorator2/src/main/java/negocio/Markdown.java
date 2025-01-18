package negocio;

public abstract class Markdown {
    protected String code;

    public abstract Markdown removeItem();
    
    public String getCode() {
        return this.code;
        
    }

    
}
