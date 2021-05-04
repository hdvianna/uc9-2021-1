package lecture12.mappers;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecture12.domain.Brand;

public class BrandMapper implements Mapper<Brand> {

    private Connection connection;
    private Map<Integer, Brand> loadedBrands;

    public BrandMapper(Connection connection) {
        this.connection = connection;
        loadedBrands = new HashMap<>();
    }

    @Override
    public Brand findById(int id) throws SQLException {
        Brand brand = loadedBrands.get(id);
        if (brand == null) {
            String sql = "SELECT id, descricao FROM Marca WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.first()) {
                        brand = map(resultSet);
                    }
                }
            }
        }
        return brand;
    }

    @Override
    public void insert(Brand entity) throws SQLException {
        String sql = "INSERT INTO Marca(descricao) VALUES(?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getDescription());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                int id = keys.getInt(1);
                entity.setId(id);
                loadedBrands.put(id, entity);
            }
        }
    }

    @Override
    public void update(Brand entity) throws SQLException {
        String sql = "UPDATE Marca SET descricao = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getDescription());
            statement.setInt(2, entity.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Brand entity) throws SQLException {
        String sql = "DELETE FROM Marca  WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getId());
            statement.executeUpdate();
            loadedBrands.remove(entity.getId());
        }
    }

    @Override
    public List<Brand> findAll() throws SQLException {
        List<Brand> all = new ArrayList<>();
        String sql = "SELECT id, descricao FROM Marca";
        try (
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                Brand brand = loadedBrands.get(id);
                if (brand == null) {
                    brand = map(resultSet);
                }
                all.add(brand);
            }
        }
        return all;
    }

    private Brand map(ResultSet resultSet) throws SQLException {
        Brand brand = new Brand();
        brand.setId(resultSet.getInt(1));
        brand.setDescription(resultSet.getString(2));
        loadedBrands.put(brand.getId(), brand);
        return brand;
    }
}
