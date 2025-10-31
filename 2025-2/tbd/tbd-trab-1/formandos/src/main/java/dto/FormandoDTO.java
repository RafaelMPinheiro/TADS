package dto;

import negocio.Formando;

public class FormandoDTO {
    private Long id;
    private String curso;
    private String turma;
    private String nomeAluno;

    public FormandoDTO() {
    }

    public FormandoDTO(Formando formando) {
        this.id = formando.getId();
        this.curso = formando.getCurso();
        this.turma = formando.getTurma();

        // Extrair dados do aluno se existir
        if (formando.getMatricula() != null && formando.getMatricula().getAluno() != null) {
            this.nomeAluno = formando.getMatricula().getAluno().getNome();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }
}