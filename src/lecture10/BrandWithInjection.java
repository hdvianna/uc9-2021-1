package lecture11;

import java.sql.*;

public class BrandWithInjection {

    private Connection connection;

    public static void main(String[] args) throws SQLException {
        (new BrandWithInjection()).run();
    }

    public void run() throws SQLException {
        try(Connection connection = createConnection()) {
            this.connection = connection;

            int id = insert("Versace");
            update(id, "Dolce & Gabbana");
            printList();
            delete(id);

            id = insert("Versace'); CREATE TABLE IF NOT EXISTS injection (field int); -- ");
            delete(id);
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mariadb://localhost/catalogo?allowMultiQueries=true", "root", null);
    }

    public int insert(String description) throws SQLException {
        String sql = "INSERT INTO Marca(descricao) VALUES('" + description + "')";
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            try(ResultSet generatedKeys = statement.getGeneratedKeys()) {
                generatedKeys.next();
                return generatedKeys.getInt(1);
            }
        }
    }

    public void update(int id, String description) throws SQLException {
        String sql = "UPDATE Marca SET descricao = '" + description + "' WHERE id = " + id;
        try(
                Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(sql);
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Marca WHERE id = " + id;
        try(
                Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(sql);
        }
    }

    public void printList() throws SQLException {
        String sql = "SELECT id, descricao FROM Marca";
        try(
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {
            System.out.println("-".repeat(30)); //Imprime o caractere '-' 30 vezes
            System.out.println("Printing Brands"); //Imprime o número da linha

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
}
