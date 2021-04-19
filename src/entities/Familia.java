package entities;

import enums.Tipo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Familia {
    public List<Pessoa> pessoaList;
    private Double renda;
    private Status status;

    public List<Pessoa> pessoasList = new ArrayList<>();
    Pessoa person = new Pessoa();

    public Familia() {
    }

    public Familia(List<Pessoa> pessoa, Double renda, Status status) {
        this.pessoaList = (List<Pessoa>) pessoa;
        this.renda = renda;
        this.status = status;
    }

    public List<Pessoa> getPessoa() {
        return pessoaList;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoaList = (List<Pessoa>) pessoa;
    }

    public Double getRenda() {
        return renda;
    }

    public void setRenda(Double renda) {
        this.renda = renda;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double rendaTotalFamilia() {
        double soma = 0;
        for(Pessoa pessoa: pessoasList){
            soma += pessoa.getRenda().getValor();

        }
        return soma;
    }

    public Integer idadePretendente() {
        int idade = 0;
        if (person.getTipo() == Tipo.PRETENDENTE) {
            idade = person.calculaIdade(person.getDataNascimento());
        }
        return idade;
    }

    public Integer quantidadeDependentes() {
        int soma = 0;
        for (Pessoa pessoasList : pessoasList){
            if(pessoasList.getTipo() == Tipo.DEPENDENTE){
                soma += 1;
            }
        }
        return soma;
    }
    public Integer quantidadeCriterios() {
        int criterio1 = 0;
        int criterio2 = 0;
        int criterio3 = 0;
        int criterioTotal;

            if (idadePretendente() >= 45) {
                criterio1 = 1;
            } else if (idadePretendente() < 44 && idadePretendente() >= 30) {
                criterio1 = 1;
            } else if (idadePretendente() < 30) {
                criterio1 = 1;
            }

        int quantidadeDependentes = quantidadeDependentes();

        for (int i = 1; i <= quantidadeDependentes; i++){
            if (quantidadeDependentes >= 3) {
                criterio2 = 1;
            } else if (quantidadeDependentes <= 2) {
                criterio2 = 1;
            }
        }

        double rendaTotalFamilia = rendaTotalFamilia();

        if (rendaTotalFamilia <= 900.0) {

            criterio3 = 1;
        } else if (rendaTotalFamilia >= 901.0 && rendaTotalFamilia <= 1500.0) {

            criterio3 = 1;
        } else if (rendaTotalFamilia >= 1501.0 && rendaTotalFamilia <= 2000.0) {

            criterio3 = 1;
        } else {

            criterio3 = 0;
        }

        criterioTotal = criterio1 + criterio2 + criterio3;

        return criterioTotal;
    }
    public Integer pontuacao(){
        int pontosIdade = 0;
        int pontosDep = 0;
        int pontosRenda = 0;
        int pontosTotais = 0;

        if (idadePretendente() >= 45) {

            pontosIdade = 3;
        } else if (idadePretendente() < 44 && idadePretendente() >= 30) {
            pontosIdade = 2;
        } else if (idadePretendente() < 30) {
            pontosIdade = 1;
        }

        int quantidadeDependentes = quantidadeDependentes();

        for (int i = 1; i <= quantidadeDependentes; i++){
            if (quantidadeDependentes >= 3) {
                pontosDep = 3;
            } else if (quantidadeDependentes <= 2) {
                pontosDep = 2;
            }
        }

        double rendaTotalFamilia = rendaTotalFamilia();

        if (rendaTotalFamilia <= 900.0) {
            pontosRenda = 5;
        } else if (rendaTotalFamilia >= 901.0 && rendaTotalFamilia <= 1500.0) {
            pontosRenda = 3;
        } else if (rendaTotalFamilia >= 1501.0 && rendaTotalFamilia <= 2000.0) {
            pontosRenda = 1;
        } else {
            pontosRenda = 0;
        }

        pontosTotais = pontosIdade + pontosDep + pontosRenda;

        return pontosTotais;
    }


    public String toString() {
        return "Familia= " +
                "Pessoas: " + pessoasList +
                ", Renda total: " + renda +
                ", Status: " + status +
                '.';
    }
}