package conversion.impl;


import conversion.ChildrenConverter;
import conversion.Converter;
import conversion.annotation.ConvertTarget;
import conversion.exception.ConversionImpossibleException;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;

import static org.apache.commons.lang3.reflect.FieldUtils.getAllFields;

public class SimpleTypeConverter<S, T> implements Converter<S, T>, ChildrenConverter {

    private static final Logger log = Logger.getLogger(SimpleTypeConverter.class);

    private FieldConverter<S, T> fieldConverter = new FieldConverter<>(this);

    @SuppressWarnings("unchecked")
    @Override
    public T convert(S source) {
        if (canConvert(source)) {
            Class<S> sourceClass = (Class<S>)source.getClass();
            ConvertTarget convertTarget = sourceClass.getAnnotation(ConvertTarget.class);

            Class<T> targetClass = convertTarget.value();
            T target = createTypeInstance(targetClass);

            Field[] fields = getAllFields(sourceClass);
            for (Field field : fields) {
                fieldConverter.convertField(source, target, field);
            }
            return target;
        } else {
            throw new ConversionImpossibleException("ConvertTarget missing. Target class can not be instantiated.");
        }
    }

    @Override
    public boolean canConvert(S source) {

        return source.getClass().isAnnotationPresent(ConvertTarget.class);
    }

    private T createTypeInstance(Class<T> target) {
        try {
            return target.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.debug("Target class can not be instantiated.");
            throw new ConversionImpossibleException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object convertChild(Object child) {
        if (canConvert((S) child)) {
            return convert((S) child);
        }
        return child;
    }
}
