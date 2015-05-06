package conversion;

public interface Converter<S, T> {

    T convert(S source);
    boolean canConvert(S source);
}
