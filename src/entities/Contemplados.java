package entities;

public class Contemplados implements Comparable<Contemplados>{
    private Integer id;
    private String nome;
    private Integer status;
    private Integer pontuacao;

    public Contemplados(){}

    public Contemplados(Integer id, String nome, Integer status, Integer pontuacao) {
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.pontuacao = pontuacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    @Override
    public String toString() {
        return
                "id= " + id +
                ", nome= " + nome +
                ", status= " + status +
                ", pontuacao= " + pontuacao +
                '\n';
    }

    @Override
    public int compareTo(Contemplados contemplados) {
        if (this.pontuacao > contemplados.getPontuacao()) {
            return -1;
        } if (this.pontuacao < contemplados.getPontuacao()){
            return 1;
        }
        return 0;
    }
}
