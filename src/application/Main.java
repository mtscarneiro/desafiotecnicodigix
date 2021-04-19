package application;

import db.DB;
import db.DbException;
import entities.*;
import enums.Tipo;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;


@SuppressWarnings("SpellCheckingInspection")
public class Main {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Locale.setDefault(Locale.US);

        Connection conn = DB.getConnection();
        PreparedStatement st;

        Status status = new Status();
        Pessoa p = new Pessoa();
        Familia familia = new Familia();

        System.out.println("Sua familia tem quantos integrantes? ");
        int n = input.nextInt();

        for(int i = 1; i <= n; i++) {
            try {
                System.out.println("Insira os dados do " + i + " familiar: ");
                System.out.println("Nome: ");
                String nome = input.next();
                System.out.println(nome + ", o Sr(Sra) é Pretendente, Conjuge ou Dependente: ");
                Tipo tipo = Tipo.valueOf(input.next().toUpperCase());
                System.out.print("Digite o dia, mês e ano do nasc (dd MM yyyy): ");
                int dia = input.nextInt();
                int mes = input.nextInt();
                int ano = input.nextInt();
                LocalDate birthDate = LocalDate.of(ano, mes, dia);
                p.setDataNascimento(birthDate);

                if (tipo == Tipo.PRETENDENTE) {
                    status.setNome(nome);
                }

                // somente se o familiar tiver mais ou igual a 18, pede-se renda.;
                if (p.calculaIdade(birthDate) >= 18) {
                    System.out.println("Insira renda: ");
                    double renda = input.nextDouble();
                    Pessoa pessoa = new Pessoa(nome, tipo, p.getDataNascimento(), new Renda(renda));
                    familia.pessoasList.add(pessoa);

                    // inserção no database;
                    try {
                        st = conn.prepareStatement(
                                "INSERT INTO pessoas"
                                        + "(nome, dataNascimento, tipo)"
                                        + "VALUES "
                                        + "(?, ?, ?)");
                        st.setString(1, nome);
                        st.setObject(2, birthDate);
                        st.setString(3, String.valueOf(tipo));
                        int rowsAffected = st.executeUpdate();
                        System.out.println("Done! Rows in pessoas affected: " + rowsAffected);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    double renda = 0;
                    Pessoa pessoa = new Pessoa(nome, tipo, p.getDataNascimento(), new Renda(renda));
                    familia.pessoasList.add(pessoa);
                    try { // insercao dos menores de 18 sem renda no database
                        st = conn.prepareStatement(
                                "INSERT INTO pessoas"
                                        + "(nome, dataNascimento, tipo)"
                                        + "VALUES "
                                        + "(?, ?, ?)");
                        st.setString(1, nome);
                        st.setObject(2, birthDate);
                        st.setString(3, String.valueOf(tipo));
                        int rowsAffected = st.executeUpdate();
                        System.out.println("Done! Rows in pessoas affected: " + rowsAffected);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }catch(InputMismatchException e){
                e.getMessage();
            }
        }
        // Setagem do status da familia, com o id passa pelo json do exemplo.
        System.out.println("""
                Insira o codigo que a sua familia se encaixa:\s
                1 - Ja possui uma casa.\s
                2 - Sua familia ja foi selecionada em outro processo de selecao.\s
                4 - Nenhuma das acima.\s"""
        );
        int id3 = input.nextInt();
        status.checkStatus(id3);
        familia.setStatus(status);

        Familia familiaCompleta = new Familia(familia.pessoasList, familia.rendaTotalFamilia(), status);
        try{ // insercao da familia no database;
            st = conn.prepareStatement(
                    "INSERT INTO familias"
                            + "(nomePretendente, statusid, pontuacao)"
                            + "VALUES "
                            + "(?, ?, ?)");
            st.setString(1, status.getNome());
            st.setInt(2, status.getId());
            st.setInt(3, familia.pontuacao());

            int rowsAffected = st.executeUpdate();
            System.out.println("Done! Rows in familias affected: " + rowsAffected);
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }

        // resgate da informacao das familias com id correspondente ao cadastro valido,
        // e ja ordenados por ordem de maior pontuacao na lista de Contemplados.

        Statement st1 = null;
        ResultSet rs = null;
        try {
            int idfamilia = 0;
            String nomePretendente = "";
            int statusid = 0;
            int pontuacao = 0;

            conn = DB.getConnection();
            st1 = conn.createStatement();
            rs = st1.executeQuery("select * from familias");
            System.out.println("CONTEMPLADOS: ");
            List<Contemplados> cp1 = new ArrayList<>();
            while (rs.next()) {
                idfamilia = rs.getInt("idfamilia");
                nomePretendente = rs.getString("nomePretendente");
                statusid = rs.getInt("statusid");
                pontuacao = rs.getInt("pontuacao");

                Contemplados contemplados = new Contemplados(idfamilia, nomePretendente, statusid, pontuacao);
                if (statusid == 0) {
                    cp1.add(contemplados);
                }
            }
            Collections.sort(cp1);
            System.out.println(String.format(String.valueOf(cp1), "\n"));
        }
        catch(SQLException e) {
            throw new DbException(e.getMessage());
        }
        // setando o id da familia no nome do pretendente ao processo de selecao;
        try{
            st = conn.prepareStatement(
                    "UPDATE pessoas SET familias_idfamilia = (SELECT idfamilia from familias where nomePretendente like nome )");
            int rowsAffected = st.executeUpdate();
            System.out.println("Done! Rows in familias affected: " + rowsAffected);
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }

        finally {

            DB.closeResultSet(rs);
            DB.closeStatements(st);
            DB.closeStatements(st1);
            DB.closeConnection();

        }

        input.close();
    }

}
