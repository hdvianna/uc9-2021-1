package lecture13.lazy.proxy;

import lecture13.lazy.domain.Type;
import lecture13.lazy.mappers.Mapper;

public class TypeProxy extends Type {
    private Mapper<Type> mapper;

    public TypeProxy(int id, Mapper<Type> mapper) {
        setId(id);
        this.mapper = mapper;
    }
}
