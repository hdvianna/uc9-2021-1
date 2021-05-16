package lecture14.ports;

import lecture14.inputs.BrandInput;
import lecture14.outputs.DefaultOutput;

public interface CatalogAdminInterface {
    DefaultOutput newBrand(BrandInput brandInput);
}
