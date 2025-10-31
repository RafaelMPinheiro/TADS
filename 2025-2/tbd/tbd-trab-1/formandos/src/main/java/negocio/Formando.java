package negocio;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = "Formando.findByCurso", query = "SELECT f FROM Formando f WHERE f.curso = :curso"),
		@NamedQuery(name = "Formando.findByTurma", query = "SELECT f FROM Formando f WHERE f.turma = :turma"),
		@NamedQuery(name = "Formando.findByCursoOrTurma", query = "SELECT f FROM Formando f WHERE f.curso = :curso OR f.turma = :turma"),
		@NamedQuery(name = "Formando.countByCurso", query = "SELECT f.curso, COUNT(f) FROM Formando f GROUP BY f.curso")
})
public class Formando {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "matricula_id")
	private Matricula matricula;

	private String curso;
	private String turma;

	public Formando() {
	}

	public Formando(Matricula matricula) {
		this.matricula = matricula;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getTurma() {
		return turma;
	}

	public void setTurma(String turma) {
		this.turma = turma;
	}

	@Override
	public String toString() {
		return "Formando [id=" + id + ", matricula=" + matricula + "]";
	}
}