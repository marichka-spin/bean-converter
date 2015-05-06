package conversion;

public interface ConverterFactory<T, S> {

    Converter<T, S> getConverter(Class<T> targetType);
}
