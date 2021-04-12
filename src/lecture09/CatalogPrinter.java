package lecture09;

import java.sql.*;

public class CatalogPrinter {

    public static void main(String[] args) throws SQLException {
        (new CatalogPrinter()).run();
    }

    public void run() throws SQLException {
        try(    //Efetua a conexão
                Connection connection = createConnection();
                //Cria o objeto de declaração de expressão
                Statement statement = createStatement(connection);
                //Executa uma expressão SQL e obtem seu resultado
                ResultSet brandResultSet = createResultSet(statement, "SELECT id, descricao FROM Marca ORDER BY id")) {

            //Imprime o resultado da expressão SQL
            printBrandResultSet(brandResultSet);
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mariadb://localhost/catalogo", "root", null);
    }

    private Statement createStatement(Connection connection) throws SQLException {
        return connection.createStatement();
    }

    private ResultSet createResultSet(Statement statement, String sql) throws SQLException {
        return statement.executeQuery(sql);
    }

    private void printBrandResultSet(ResultSet resultSet) throws SQLException {
        resultSet.beforeFirst();
        while (resultSet.next()) {
            int rowNumber = resultSet.getRow(); //Obtem o número da linha
            int id = resultSet.getInt("id"); //Obtem o valor da coluna id
            String brand = resultSet.getNString("descricao"); //Obtem o valor da coluna descricao

            System.out.println("-".repeat(30)); //Imprime o caractere '-' 30 vezes
            System.out.println("Printing row #" + rowNumber); //Imprime o número da linha
            System.out.println("-".repeat(30)); //Imprime o caractere '-' 30 vezes

            System.out.println("Id    : " + id); //Imprime o id
            System.out.println("Brand : " + brand); //Imprime a marca
        }
    }

}
