package conversion;

import java.util.List;

public interface ListConverter<S, T> {

    List<T> convert(List<S> source);
    boolean canConvert(List<S> source);

}
