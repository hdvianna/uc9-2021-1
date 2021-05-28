package lecture10;

import java.sql.*;

public class ColorPrepared {
    private Connection connection;

    public static void main(String[] args) throws SQLException {
        (new ColorPrepared()).run();
    }

    public void run() throws SQLException {
        try (Connection connection = createConnection()) {
            this.connection = connection;

            int id = insert("CCCCCC", "Gray");
            update(id, "CCCCCC", "Light Gray");
            delete(id);

        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mariadb://localhost/catalogo", "root", null);
    }

    public int insert(String hexa, String description) throws SQLException {
        return -1;
    }

    public void update(int id, String hexa, String description) throws SQLException {

    }

    public void delete(int id) throws SQLException {

    }

}
