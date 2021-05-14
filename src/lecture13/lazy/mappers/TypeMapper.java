package lecture13.lazy.mappers;

import lecture13.lazy.domain.Type;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeMapper implements Mapper<Type> {

    private Connection connection;
    private Map<Integer, Type> loadedTypes;

    public TypeMapper(Connection connection) {
        this.connection = connection;
        loadedTypes = new HashMap<>();
    }

    @Override
    public Type findById(int id) throws SQLException {
        Type type = loadedTypes.get(id);
        if (type == null) {
            String sql = "SELECT id, descricao FROM Tipo WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.first()) {
                        type = map(resultSet);
                    }
                }
            }
        }
        return type;
    }

    @Override
    public void insert(Type entity) throws SQLException {
        String sql = "INSERT INTO Tipo(descricao) VALUES(?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getDescription());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                int id = keys.getInt(1);
                entity.setId(id);
                loadedTypes.put(id, entity);
            }
        }
    }

    @Override
    public void update(Type entity) throws SQLException {
        String sql = "UPDATE Tipo SET descricao = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getDescription());
            statement.setInt(2, entity.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Type entity) throws SQLException {
        String sql = "DELETE FROM Tipo  WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getId());
            statement.executeUpdate();
            loadedTypes.remove(entity.getId());
        }
    }

    @Override
    public List<Type> findAll() throws SQLException {
        List<Type> all = new ArrayList<>();
        String sql = "SELECT id, descricao FROM Tipo";
        try (
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                Type type = loadedTypes.get(id);
                if (type == null) {
                    type = map(resultSet);
                }
                all.add(type);
            }
        }
        return all;
    }

    private Type map(ResultSet resultSet) throws SQLException {
        Type type = new Type();
        type.setId(resultSet.getInt(1));
        type.setDescription(resultSet.getString(2));
        loadedTypes.put(type.getId(), type);
        return type;
    }
}
