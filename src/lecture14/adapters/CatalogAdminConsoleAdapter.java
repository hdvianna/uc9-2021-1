package lecture14.adapters;

import lecture13.lazy.mappers.Mapper;
import lecture13.lazy.MapperTest;
import lecture13.lazy.domain.Brand;
import lecture13.lazy.mappers.BrandMapper;
import lecture14.inputs.BrandInput;
import lecture14.outputs.DefaultOutput;
import lecture14.ports.CatalogAdmin;
import lecture14.ports.CatalogAdminInterface;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class CatalogAdminConsoleAdapter {


    public void run() throws IOException, SQLException {
        try (Connection connection = createConnection()){
            Mapper<Brand> brandMapper = new BrandMapper(connection);
            CatalogAdminInterface catalogAdmin = new CatalogAdmin(brandMapper);
            showGreeting();
            newBrandForm(catalogAdmin);
        }
    }

    private Connection createConnection() throws SQLException, IOException {
        try (InputStream dbConnectionStream = MapperTest.class.getClassLoader().getResourceAsStream("dbconnection.properties")) {
            Properties properties = new Properties();
            properties.load(dbConnectionStream);
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");

            return DriverManager.getConnection(url, user, password);
        }
    }

    private void showGreeting() {
        String greeting = "Welcome to the Catalog Manager!";
        int numberOfDashes = 50;
        int padding = (numberOfDashes - greeting.length())/2;
        System.out.println("-".repeat(numberOfDashes));
        System.out.println(" ".repeat(padding) + greeting + " ".repeat(padding));
        System.out.println("-".repeat(numberOfDashes));
    }

    private void newBrandForm(CatalogAdminInterface catalogAdmin) {
        Scanner input = new Scanner(System.in);
        System.out.print("Type the brand name: ");
        String description = input.nextLine();
        BrandInput brandInput = new BrandInput();
        brandInput.setDescription(description);
        DefaultOutput defaultOutput = catalogAdmin.newBrand(brandInput);
        if (defaultOutput.isSuccess()) {
            System.out.println("[SUCCESS]: " + defaultOutput.getMessage());
        } else {
            System.out.println("[ERROR]: " + defaultOutput.getMessage());
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        (new CatalogAdminConsoleAdapter()).run();
    }
}
