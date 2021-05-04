package lecture12.mappers;

import lecture12.domain.Type;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TypeMapper implements Mapper<Type> {

    private Connection connection;

    public TypeMapper(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Type findById(int id) throws SQLException {
        return null;
    }

    @Override
    public void insert(Type entity) throws SQLException {

    }

    @Override
    public void update(Type entity) throws SQLException {

    }

    @Override
    public void delete(Type entity) throws SQLException {

    }

    @Override
    public List<Type> findAll() throws SQLException {
        return null;
    }
}
