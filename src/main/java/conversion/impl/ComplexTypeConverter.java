package conversion.impl;


import conversion.ChildrenConverter;
import conversion.Converter;
import conversion.annotation.ConvertTarget;
import conversion.exception.ConversionImpossibleException;

import java.lang.reflect.Field;
import java.util.List;

public class ComplexTypeConverter<S, T> implements Converter<S, T>, ChildrenConverter {

    private SimpleTypeConverter<S, T> simpleTypeConverter = new SimpleTypeConverter<>();
    private SimpleListConverter<S, T> simpleListConverter = new SimpleListConverter<>();
    private FieldConverter<S, T> fieldConverter = new FieldConverter<>(this);

    @Override
    @SuppressWarnings("unchecked")
    public T convert(S source) {
        if (canConvert(source)) {
            T target = simpleTypeConverter.convert(source);
            Class<S> sourceClass = (Class<S>)source.getClass();

            Field[] fields = sourceClass.getDeclaredFields();
            for (Field field : fields) {
                fieldConverter.convertField(source, target, field);
            }
            return null;
        } else {
            throw new ConversionImpossibleException("ConvertTarget missing. Target class can not be instantiated.");
        }
    }

    @Override
    public boolean canConvert(S source) {
        return source.getClass().isAnnotationPresent(ConvertTarget.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object convertChild(Object child) {
        if (child instanceof List) {
            if (simpleListConverter.canConvert((List<S>) child)) {
                return simpleListConverter.convert((List<S>) child);
            }
        }
        return child;
    }
}
