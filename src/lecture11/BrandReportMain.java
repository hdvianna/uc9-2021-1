package lecture12;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class BrandReportMain {
    public static void main(String[] args) throws SQLException, IOException {
        try (Connection conn = createConnection() ) {
            try (BrandReportPager report = new BrandReportPager(conn, 10)) {
               while(report.next()) {
                   ResultSet page = report.getResultSet();
                   page.beforeFirst();
                   while (page.next()) {

                       System.out.println("-".repeat(30)); //Imprime o caractere '-' 30 vezes
                       System.out.println("Printing row #" + page.getRow() + " from page " + report.getPage()); //Imprime o número da linha
                       System.out.println("-".repeat(30)); //Imprime o caractere '-' 30 vezes

                       System.out.println("Brand        : " + page.getString("brand")); //Imprime a marca
                       System.out.println("Type         : " + page.getString("type")); //Imprime a marca
                       System.out.println("Size         : " + page.getString("size")); //Imprime a marca
                       System.out.println("Color        : " + page.getString("color")); //Imprime a marca
                       System.out.println("Price        : " + page.getDouble("price")); //Imprime a marca
                       System.out.println("Stock        : " + page.getDouble("stock")); //Imprime a marca
                       System.out.println("Stock Average : " + page.getDouble("stock_avg")); //Imprime a marca


                   }
               }
            }
        }
    }

    private static Connection createConnection() throws SQLException {
        InputStream dbConnectionStream = BrandReportMain.class.getClassLoader().getResourceAsStream("dbconnection.properties");
        /**
         * Carregue as informações de conexão a partir do arquivo de configuração
         */
        String url = "jdbc:mariadb://localhost/catalogo";
        String user = "root";
        String password = null;

        return DriverManager.getConnection(url, user, password);
    }
}
