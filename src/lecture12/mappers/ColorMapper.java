package lecture12.mappers;

import lecture12.domain.Color;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ColorMapper implements Mapper<Color> {

    private Connection connection;

    public ColorMapper(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Color findById(int id) throws SQLException {
        return null;
    }

    @Override
    public void insert(Color entity) throws SQLException {

    }

    @Override
    public void update(Color entity) throws SQLException {

    }

    @Override
    public void delete(Color entity) throws SQLException {

    }

    @Override
    public List<Color> findAll() throws SQLException {
        return null;
    }
}
