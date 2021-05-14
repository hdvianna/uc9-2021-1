package lecture13.lazy.mappers;

import lecture13.lazy.domain.*;
import lecture13.lazy.proxy.BrandProxy;
import lecture13.lazy.proxy.ColorProxy;
import lecture13.lazy.proxy.SizeProxy;
import lecture13.lazy.proxy.TypeProxy;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatalogMapper implements Mapper<Catalog> {

    private Connection connection;
    private Mapper<Brand> brandMapper;
    private Mapper<Color> colorMapper;
    private Mapper<Size> sizeMapper;
    private Mapper<Type> typeMapper;
    private Map<Integer, Catalog> loadedCatalog;

    public CatalogMapper(Connection connection, Mapper<Brand> brandMapper, Mapper<Color> colorMapper, Mapper<Size> sizeMapper, Mapper<Type> typeMapper) {
        this.connection = connection;
        this.brandMapper = brandMapper;
        this.colorMapper = colorMapper;
        this.sizeMapper = sizeMapper;
        this.typeMapper = typeMapper;
        loadedCatalog = new HashMap<>();
    }

    @Override
    public Catalog findById(int id) throws SQLException {
        Catalog catalog = loadedCatalog.get(id);
        if (catalog == null) {
            String sql = "SELECT id, id_marca, id_tamanho, id_cor, id_tipo, preco, estoque FROM Catalogo WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.first()) {
                        catalog = map(resultSet);
                    }
                }
            }
        }
        return catalog;
    }

    @Override
    public void insert(Catalog entity) throws SQLException {
        String sql = "INSERT INTO Catalogo(id_marca, id_tamanho, id_cor, id_tipo, preco, estoque) VALUES(?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, entity.getBrand().getId());
            statement.setInt(2, entity.getSize().getId());
            statement.setInt(3, entity.getColor().getId());
            statement.setInt(4, entity.getType().getId());
            statement.setDouble(5, entity.getPrice());
            statement.setInt(6, entity.getStock());

            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                int id = keys.getInt(1);
                entity.setId(id);
                loadedCatalog.put(id, entity);
            }
        }
    }

    @Override
    public void update(Catalog entity) throws SQLException {
        String sql = "UPDATE Catalogo SET id_marca = ?, id_tamanho = ?, id_cor = ?, id_tipo = ?, preco = ?, estoque = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, entity.getBrand().getId());
            statement.setInt(2, entity.getSize().getId());
            statement.setInt(3, entity.getColor().getId());
            statement.setInt(4, entity.getType().getId());
            statement.setDouble(5, entity.getPrice());
            statement.setInt(6, entity.getStock());
            statement.setInt(7, entity.getId());

            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Catalog entity) throws SQLException {
        String sql = "DELETE FROM Catalogo  WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getId());
            statement.executeUpdate();
            loadedCatalog.remove(entity.getId());
        }
    }

    @Override
    public List<Catalog> findAll() throws SQLException {
        List<Catalog> all = new ArrayList<>();
        String sql = "SELECT id, id_marca, id_tamanho, id_cor, id_tipo, preco, estoque FROM Catalogo";
        try (
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                Catalog catalog = loadedCatalog.get(id);
                if (catalog == null) {
                    catalog = map(resultSet);
                }
                all.add(catalog);
            }
        }
        return all;
    }

    private Catalog map(ResultSet resultSet) throws SQLException {
        Brand brand = new BrandProxy(resultSet.getInt("id_marca"), brandMapper);
        Size size = new SizeProxy(resultSet.getInt("id_tamanho"), sizeMapper);
        Color color = new ColorProxy(resultSet.getInt("id_cor"), colorMapper);
        Type type = new TypeProxy(resultSet.getInt("id_tipo"), typeMapper);

        Catalog catalog = new Catalog();
        catalog.setId(resultSet.getInt("id"));
        catalog.setBrand(brand);
        catalog.setColor(color);
        catalog.setSize(size);
        catalog.setType(type);
        catalog.setPrice(resultSet.getDouble("preco"));
        catalog.setStock(resultSet.getInt("estoque"));

        loadedCatalog.put(catalog.getId(), catalog);
        return catalog;
    }

}
