package lecture13.lazy.proxy;

import lecture13.lazy.domain.Color;
import lecture13.lazy.mappers.Mapper;

public class ColorProxy extends Color {
    private Mapper<Color> mapper;

    public ColorProxy(int id, Mapper<Color> mapper) {
        setId(id);
        this.mapper = mapper;
    }
}
