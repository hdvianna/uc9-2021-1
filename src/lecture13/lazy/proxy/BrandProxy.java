package lecture13.lazy.proxy;

import lecture13.lazy.domain.Brand;
import lecture13.lazy.mappers.Mapper;

import java.sql.SQLException;

public class BrandProxy extends Brand {
    private Mapper<Brand> mapper;
    private Brand brand = null;

    public BrandProxy(int id, Mapper<Brand> mapper) {
        setId(id);
        this.mapper = mapper;
    }

    public void setDescription(String description) {
        load();
        brand.setDescription(description);
    }

    public String getDescription()  {
        load();
        return brand.getDescription();
    }

    private void load() {
        if (brand == null) {
            try {
                brand = mapper.findById(getId());
            } catch (SQLException throwables) {
                throw new Error(throwables);
            }
        }
    }
}
