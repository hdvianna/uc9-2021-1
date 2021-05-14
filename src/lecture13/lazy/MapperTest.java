package lecture13.lazy;


import lecture13.lazy.domain.*;
import lecture13.lazy.mappers.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@SuppressWarnings("unchecked")
public class MapperTest {

    private Connection connection;

    public void test()  {
        try (Connection connection = createConnection()) {
            this.connection = connection;

            BrandMapper brandMapper = new BrandMapper(connection);
            TypeMapper typeMapper = new TypeMapper(connection);
            ColorMapper colorMapper = new ColorMapper(connection);
            SizeMapper sizeMapper = new SizeMapper(connection);
            CatalogMapper catalogMapper = new CatalogMapper(connection,brandMapper,colorMapper,sizeMapper,typeMapper);

            Catalog catalog = catalogMapper.findById(47);
            testBrandProxy(catalog);
            //testSizeProxy(catalog);
            //testTypeProxy(catalog);
            //testColorProxy(catalog);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testBrandProxy(Catalog catalog) {
        assertTrue(catalog.getBrand().getDescription().equals("Jean Paul Gaultier"), "Brand description differs from 'Jean Paul Gaultier'");
        System.out.println("passed testBrandProxy");
    }

    private void testSizeProxy(Catalog catalog) {
        assertTrue(catalog.getSize().getDescription().equals("Grande"), "Size description differs from 'Grande'");
        System.out.println("passed testSizeProxy");
    }

    private void testTypeProxy(Catalog catalog) {
        assertTrue(catalog.getType().getDescription().equals("Calça"), "Type description differs from 'Calça'");
        System.out.println("passed testTypeProxy");
    }

    private void testColorProxy(Catalog catalog) {
        assertTrue(catalog.getBrand().getDescription().equals("Vermelha"), "Color description differs from 'Vermelha'");
        System.out.println("passed testColorProxy");
    }

    private Connection createConnection() throws SQLException, IOException {
        InputStream dbConnectionStream = MapperTest.class.getClassLoader().getResourceAsStream("dbconnection.properties");
        Properties properties = new Properties();
        properties.load(dbConnectionStream);
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        return DriverManager.getConnection(url, user, password);
    }

    private void assertTrue(boolean expected, String message) {
        if(!expected) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        (new MapperTest()).test();
    }
}
