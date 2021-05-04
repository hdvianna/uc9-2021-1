package lecture12.mappers;

import lecture12.domain.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CatalogMapper implements Mapper<Catalog> {

    private Connection connection;
    private Mapper<Brand> brandMapper;
    private Mapper<Color> colorMapper;
    private Mapper<Size> sizeMapper;
    private Mapper<Type> typeMapper;

    public CatalogMapper(Connection connection, Mapper<Brand> brandMapper, Mapper<Color> colorMapper, Mapper<Size> sizeMapper, Mapper<Type> typeMapper) {
        this.connection = connection;
        this.brandMapper = brandMapper;
        this.colorMapper = colorMapper;
        this.sizeMapper = sizeMapper;
        this.typeMapper = typeMapper;
    }

    @Override
    public Catalog findById(int id) throws SQLException {
        return null;
    }

    @Override
    public void insert(Catalog entity) throws SQLException {

    }

    @Override
    public void update(Catalog entity) throws SQLException {

    }

    @Override
    public void delete(Catalog entity) throws SQLException {

    }

    @Override
    public List<Catalog> findAll() throws SQLException {
        return null;
    }
}
