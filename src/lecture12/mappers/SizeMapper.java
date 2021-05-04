package lecture12.mappers;

import lecture12.domain.Size;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SizeMapper implements Mapper<Size> {

    private Connection connection;

    public SizeMapper(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Size findById(int id) throws SQLException {
        return null;
    }

    @Override
    public void insert(Size entity) throws SQLException {

    }

    @Override
    public void update(Size entity) throws SQLException {

    }

    @Override
    public void delete(Size entity) throws SQLException {

    }

    @Override
    public List<Size> findAll() throws SQLException {
        return null;
    }
}
