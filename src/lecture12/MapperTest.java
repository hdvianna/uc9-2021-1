package lecture12;

import lecture12.domain.*;
import lecture12.mappers.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@SuppressWarnings("unchecked")
public class MapperTest {

    private Connection connection;

    public void test()  {
        try (Connection connection = createConnection()) {
            this.connection = connection;
            testBrand();
            //testColor();
            //testSize();
            //testType();
            //testCatalog();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testBrand() throws SQLException {
        Brand brand = new Brand();
        brand.setDescription("Lorem Ipsum");

        BrandMapper brandMapper = new BrandMapper(connection);
        brandMapper.insert(brand);
        brand.setDescription("Lorem Ipsum Dolor");
        brandMapper.update(brand);
        brandMapper.delete(brand);
        brand = brandMapper.findById(1);
        assertTrue(brand.getDescription().equals("Jean Paul Gaultier"),"Error: Description differs from 'Jean Paul Gaultier'");
        List<Brand> brandList = brandMapper.findAll();
        assertTrue(brandList.size() == 4, "Error: More than 4 brands");
        System.out.println("Passed testBrand!");

    }

    private void testType() throws SQLException {
        Type type = new Type();
        type.setDescription("Where is the galactic space?");

        TypeMapper typeMapper = new TypeMapper(connection);

        typeMapper.insert(type);
        type.setDescription("Turbulence at the universe that is when futile particles walk.");
        typeMapper.update(type);
        typeMapper.delete(type);
        type = typeMapper.findById(1);
        assertTrue(type.getDescription().equals("Camiseta"),"Error: Description differs from 'Camiseta'");
        List<Type> typeList = typeMapper.findAll();
        assertTrue(typeList.size() == 4,"Error: More than 4 types");
        System.out.println("Passed testType!");
    }

    private void testColor() throws SQLException {
        Color color = new Color();
        color.setDescription("How dark. You lead like a mainland.");
        color.setHexa("FCFCFC");
        ColorMapper colorMapper = new ColorMapper(connection);

        colorMapper.insert(color);
        color.setDescription("Scream oppressively like an addled jack.");
        colorMapper.update(color);
        colorMapper.delete(color);
        color = colorMapper.findById(1);
        assertTrue(color.getDescription().equals("Branca"),"Error: Description differs from 'Branca'");
        List<Color> colorList = colorMapper.findAll();
        assertTrue(colorList.size() == 4,"Error: More than 4 colors");
        System.out.println("Passed testColor!");
    }

    private void testSize() throws SQLException {
        Size size = new Size();
        size.setDescription("Everything we do is connected with acceptance: blessing, ascension, fear, freedom.");
        size.setSigla("OFA");
        SizeMapper sizeMapper = new SizeMapper(connection);

        sizeMapper.insert(size);
        size.setDescription("Egos, visitors, and fraternal selfs will always protect them.");
        sizeMapper.update(size);
        sizeMapper.delete(size);
        size = sizeMapper.findById(1);
        assertTrue(size.getSigla().equals("M"),"Error: Description differs from 'Médio'");
        List<Size> sizeList = sizeMapper.findAll();
        assertTrue(sizeList.size() == 3,"Error: More than 3 sizes");
        System.out.println("Passed testSize!");
    }

    private void testCatalog() throws SQLException {
        BrandMapper brandMapper = new BrandMapper(connection);
        TypeMapper typeMapper = new TypeMapper(connection);
        ColorMapper colorMapper = new ColorMapper(connection);
        SizeMapper sizeMapper = new SizeMapper(connection);
        CatalogMapper catalogMapper = new CatalogMapper(connection,brandMapper,colorMapper,sizeMapper,typeMapper);

        Brand brand = new Brand();
        brand.setDescription("Lorem Ipsum");
        brandMapper.insert(brand);

        Type type = new Type();
        type.setDescription("Where is the galactic space?");
        typeMapper.insert(type);

        Color color = new Color();
        color.setDescription("How dark. You lead like a mainland.");
        color.setHexa("FCFCFC");
        colorMapper.insert(color);

        Size size = new Size();
        size.setDescription("Everything we do is connected with acceptance: blessing, ascension, fear, freedom.");
        size.setSigla("OFA");
        sizeMapper.insert(size);

        Catalog catalog = new Catalog();
        catalog.setBrand(brand);
        catalog.setColor(color);
        catalog.setSize(size);
        catalog.setType(type);
        catalog.setPrice(20);
        catalog.setStock(100);

        catalogMapper.insert(catalog);
        catalogMapper.update(catalog);
        catalogMapper.delete(catalog);

        brandMapper.delete(brand);
        typeMapper.delete(type);
        colorMapper.delete(color);
        sizeMapper.delete(size);

        catalog = catalogMapper.findById(1);
        assertTrue(catalog.getPrice() == 250,"Error: Price differs from '250");

        List<Catalog> catalogList = catalogMapper.findAll();
        assertTrue(catalogList.size() == 192,"Error: More than 192 items");
        System.out.println("Passed testCatalog!");

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
