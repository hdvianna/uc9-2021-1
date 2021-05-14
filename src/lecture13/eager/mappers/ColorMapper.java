package lecture13.eager.mappers;

import lecture13.eager.domain.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColorMapper implements Mapper<Color> {

    private Connection connection;
    private Map<Integer, Color> loadedColors;

    public ColorMapper(Connection connection) {
        this.connection = connection;
        loadedColors = new HashMap<>();
    }

    @Override
    public Color findById(int id) throws SQLException {
        Color color = loadedColors.get(id);
        if (color == null) {
            String sql = "SELECT id, hexa, descricao FROM Cor WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.first()) {
                        color = map(resultSet);
                    }
                }
            }
        }
        return color;
    }

    @Override
    public void insert(Color entity) throws SQLException {
        String sql = "INSERT INTO Cor(hexa, descricao) VALUES(?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getHexa());
            statement.setString(2, entity.getDescription());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                int id = keys.getInt(1);
                entity.setId(id);
                loadedColors.put(id, entity);
            }
        }
    }

    @Override
    public void update(Color entity) throws SQLException {
        String sql = "UPDATE Cor SET hexa = ?, descricao = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getHexa());
            statement.setString(2, entity.getDescription());
            statement.setInt(3, entity.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Color entity) throws SQLException {
        String sql = "DELETE FROM Cor  WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getId());
            statement.executeUpdate();
            loadedColors.remove(entity.getId());
        }
    }

    @Override
    public List<Color> findAll() throws SQLException {
        List<Color> all = new ArrayList<>();
        String sql = "SELECT id, hexa, descricao FROM Cor";
        try (
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                Color color = loadedColors.get(id);
                if (color == null) {
                    color = map(resultSet);
                }
                all.add(color);
            }
        }
        return all;
    }

    private Color map(ResultSet resultSet) throws SQLException {
        Color color = new Color();
        color.setId(resultSet.getInt(1));
        color.setHexa(resultSet.getString(2));
        color.setDescription(resultSet.getString(3));
        loadedColors.put(color.getId(), color);
        return color;
    }
}
