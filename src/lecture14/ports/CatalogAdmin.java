package lecture14.ports;

import lecture13.lazy.domain.Brand;
import lecture13.lazy.mappers.Mapper;
import lecture14.inputs.BrandInput;
import lecture14.outputs.DefaultOutput;


import java.util.List;

public class CatalogAdmin implements CatalogAdminInterface {

    private Mapper<Brand> brandMapper;

    public CatalogAdmin(Mapper<Brand> brandMapper) {
        this.brandMapper = brandMapper;
    }

    @Override
    public DefaultOutput newBrand(BrandInput brandInput) {
        DefaultOutput result = new DefaultOutput();
        try {
            Brand brand = new Brand();
            String description = brandInput.getDescription();
            brand.setDescription(description);
            checkBrandDescription(brand);
            brandMapper.insert(brand);
            result.setSuccess(true);
            result.setMessage("Brand successfully created.");
        } catch (Exception exception) {
            result.setSuccess(false);
            result.setMessage("It was not possible to create the brand (" + exception.getMessage() + ")");
        }
        return result;
    }

    private void checkBrandDescription(Brand newBrand) throws Exception {
        if (newBrand.getDescription() == null && newBrand.getDescription().length() == 0) {
            throw new Exception("Description is mandatory!");
        }

        List<Brand> brands = brandMapper.findAll();
        for(Brand brand:brands) {
            if (brand.getDescription().equals(newBrand.getDescription())) {
                throw new Exception("Description already exists!");
            }
        }
    }

}
