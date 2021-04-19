package entities;

import enums.Tipo;

public class Status {
    private Integer id;
    private String nome;

    Pessoa pessoa = new Pessoa();

    public Status(){}
    public Status(Integer id, String nome){
        this.id = id;
        this.nome = nome;
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
    public void checkStatus(int id){
        if(id == 1) {

            System.out.println("O pretendente afirma ja possuir uma residencia em seu nome.");
            codigoInvalido();
        }else if(id == 2){
             System.out.println("O pretendente afirma ja estar participando de um outro processo de selecao.");
             codigoInvalido();
        }else if(id == 4) {
             codigoValido();
             }
        }
            public void codigoValido() {
                int id = 0;
                setId(id);
                System.out.println("Cadastro Valido!");
            }
            public void codigoInvalido() {
                int id = 3;
                setId(id);
                System.out.println("Cadastro Invalido!");
    }

    public String toString() {
        return "Status (" +
                "NOME: " + getNome() +
                ", ID: " + getId() +
                ')';
    }
}


