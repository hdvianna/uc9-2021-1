package lecture10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TypePrepared {
    private Connection connection;

    public static void main(String[] args) throws SQLException {
        (new TypePrepared()).run();
    }

    public void run() throws SQLException {
        try (Connection connection = createConnection()) {
            this.connection = connection;

            int id = insert("Boné");
            update(id, "Boina");
            delete(id);

        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mariadb://localhost/catalogo", "root", null);
    }

    public int insert(String description) throws SQLException {
        return -1;
    }

    public void update(int id, String description) throws SQLException {

    }

    public void delete(int id) throws SQLException {

    }
}
