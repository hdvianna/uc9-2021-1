package lecture13.eager.mappers;

import lecture13.eager.domain.Size;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SizeMapper implements Mapper<Size> {

    private Connection connection;
    private Map<Integer, Size> loadedSizes;

    public SizeMapper(Connection connection) {
        this.connection = connection;
        loadedSizes = new HashMap<>();
    }

    @Override
    public Size findById(int id) throws SQLException {
        Size size = loadedSizes.get(id);
        if (size == null) {
            String sql = "SELECT id, sigla, descricao FROM Tamanho WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.first()) {
                        size = map(resultSet);
                    }
                }
            }
        }
        return size;
    }

    @Override
    public void insert(Size entity) throws SQLException {
        String sql = "INSERT INTO Tamanho(sigla,descricao) VALUES(?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getSigla());
            statement.setString(2, entity.getDescription());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                int id = keys.getInt(1);
                entity.setId(id);
                loadedSizes.put(id, entity);
            }
        }
    }

    @Override
    public void update(Size entity) throws SQLException {
        String sql = "UPDATE Tamanho SET sigla = ?, descricao = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getSigla());
            statement.setString(2, entity.getDescription());
            statement.setInt(3, entity.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Size entity) throws SQLException {
        String sql = "DELETE FROM Tamanho WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getId());
            statement.executeUpdate();
            loadedSizes.remove(entity.getId());
        }
    }

    @Override
    public List<Size> findAll() throws SQLException {
        List<Size> all = new ArrayList<>();
        String sql = "SELECT id, sigla, descricao FROM Tamanho";
        try (
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                Size size = loadedSizes.get(id);
                if (size == null) {
                    size = map(resultSet);
                }
                all.add(size);
            }
        }
        return all;
    }

    private Size map(ResultSet resultSet) throws SQLException {
        Size size = new Size();
        size.setId(resultSet.getInt(1));
        size.setSigla(resultSet.getString(2));
        size.setDescription(resultSet.getString(3));
        loadedSizes.put(size.getId(), size);
        return size;
    }
}
