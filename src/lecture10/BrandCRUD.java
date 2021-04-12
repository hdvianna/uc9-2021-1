package lecture10;

import java.sql.*;
import java.util.Calendar;

public class BrandCRUD {

    public static void main(String[] args) throws SQLException {
        (new BrandCRUD()).run();
    }

    public void run() throws SQLException {
        try(Connection connection = createConnection()) {

            int id = -1;
            String insertSQL = "INSERT INTO Marca(descricao) VALUES ('Example Brand')";
            try(Statement statement = connection.createStatement()) {
                statement.executeUpdate(insertSQL, Statement.RETURN_GENERATED_KEYS);
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    keys.next();
                    id = keys.getInt(1);
                }

                String updateSQL = "UPDATE Marca SET descricao='Example Brand (Updated)' WHERE id = 999999";
                statement.executeUpdate(updateSQL);

                String deleteSQL = "DELETE FROM Marca WHERE descricao = 'Example Brand'";
                statement.executeUpdate(deleteSQL);

            }


        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mariadb://localhost/catalogo", "root", null);
    }


}
