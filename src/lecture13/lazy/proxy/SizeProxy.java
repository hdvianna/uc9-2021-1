package lecture13.lazy.proxy;

import lecture13.lazy.domain.Size;
import lecture13.lazy.mappers.Mapper;

public class SizeProxy extends Size {
    private Mapper<Size> mapper;

    public SizeProxy(int id, Mapper<Size> mapper) {
        setId(id);
        this.mapper = mapper;
    }
}
