package negocio;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Professor")
public class Professor extends Pessoa {

    @Column(unique = true)
    private String siape;

    public String getSiape() {
        return siape;
    }

    public void setSiape(String siape) {
        this.siape = siape;
    }

    @Override
    public String toString() {
        return "Professor [siape=" + siape + ", getNome()=" + getNome() + "]";
    }

    
    
}
