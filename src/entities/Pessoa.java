package entities;

import enums.Tipo;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;

public class Pessoa {
    private String nome;
    protected LocalDate dataNascimento;
    private Tipo tipo;
    private Renda renda;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public Pessoa() {}

    public Pessoa(String nome, Tipo tipo, LocalDate dataNascimento, Renda renda) {
        this.nome = nome;
        this.tipo = tipo;
        this.dataNascimento = dataNascimento;
        this.renda = renda;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Renda getRenda() {
        return renda;
    }

    public void setRenda(Renda renda) {
        this.renda = renda;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer calculaIdade(LocalDate birthDate){
        LocalDate today = LocalDate.now();
        birthDate = LocalDate.of(birthDate.getYear(), birthDate.getMonth(), birthDate.getDayOfMonth());
        int years = Period.between(birthDate, today).getYears();
        return years;
    }

    @Override
    public String toString() {
        return "Pessoa: " +
                ", NOME = " + nome +
                ", SITUACAO = " + tipo +
                ", IDADE = " + calculaIdade(getDataNascimento()) +
                ", RENDA = " + renda.getValor() +
                '.';
    }
}