package conversion.impl;


import conversion.ChildrenConverter;
import conversion.Converter;
import conversion.annotation.TargetProperty;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class FieldConverter<S, T> {

    private static final Logger log = Logger.getLogger(FieldConverter.class);

    private Converter<S, T> converter;

    protected FieldConverter(Converter<S, T> converter) {
        this.converter = converter;
    }

    @SuppressWarnings("unchecked")
    public void convertField(S source, T target, Field field) {
        if (skipField(field)) {
            return;
        }
        Class<S> sourceClass = (Class<S>)source.getClass();
        Class<T> targetClass = (Class<T>) target.getClass();
        try {
            Method getMethod = obtainGetMethodByField(field, sourceClass);
            if (getMethod == null) {
                logFieldWasNotConverted(field);
                return;
            }
            Object result = getMethod.invoke(source);
            if (result == null) {
                logFieldWasNotConverted(field);
                return;
            }

            Class<?> argType = field.getType();
            if (complexField(field)) {
                result = ((ChildrenConverter)converter).convertChild(result);
                argType = result.getClass();
            }

            Method setMethod = obtainSetMethodByField(field, targetClass, argType);
            if (setMethod == null) {
                logFieldWasNotConverted(field);
                return;
            }
            setMethod.invoke(target, result);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logFieldWasNotConverted(field, e);
        }
    }

    private Method obtainGetMethodByField(Field field, Class<?> clazz) {
        String fieldName = getFieldName(field);
        String getMethodName = MethodPrefix.GET + capitalize(fieldName);
        if (field.getType().equals(boolean.class)) {
            getMethodName = MethodPrefix.IS + capitalize(fieldName);
        }
        try {
            return clazz.getMethod(getMethodName);
        } catch (NoSuchMethodException e) {
            System.out.println(String.format("No such method: %s() in class %s",
                    getMethodName, clazz.getName()));
            log.debug(String.format("No such method: %s() in class %s",
                    getMethodName, clazz.getName()));
        }
        return null;
    }

    private Method obtainSetMethodByField(Field field, Class<?> clazz, Class<?> argType) {
        String fieldName = getFieldName(field);
        String setMethodName = MethodPrefix.SET + capitalize(fieldName);

        try {
            return clazz.getMethod(setMethodName, argType);
        } catch (NoSuchMethodException e) {
            System.out.println(String.format("No such method: %s(%s arg) in class %s",
                    MethodPrefix.SET + capitalize(fieldName), field.getType(), clazz.getName()));
            log.debug(String.format("No such method: %s(%s arg) in class %s",
                    MethodPrefix.SET + capitalize(fieldName), field.getType(), clazz.getName()));
        }
        return null;
    }

    private String getFieldName(Field field) {
        if (field.isAnnotationPresent(TargetProperty.class)) {
            TargetProperty targetProperty = field.getAnnotation(TargetProperty.class);
            if (!isEmpty(targetProperty.name())) {
                return targetProperty.name();
            }
        }
        return field.getName();
    }

    private boolean skipField(Field field) {
        if (field.isAnnotationPresent(TargetProperty.class)) {
            TargetProperty targetProperty = field.getAnnotation(TargetProperty.class);
            return targetProperty.exclude();
        }
        return false;
    }

    private boolean complexField(Field field) {
        if (field.isAnnotationPresent(TargetProperty.class)) {
            TargetProperty targetProperty = field.getAnnotation(TargetProperty.class);
            return targetProperty.complex();
        }
        return false;
    }

    private static void logFieldWasNotConverted(Field field) {
        log.debug(String.format("Field %s was not converted", field.getName()));
    }

    private static void logFieldWasNotConverted(Field field, Throwable e) {
        log.debug(String.format("Field %s was not converted", field.getName()), e);
    }

    private enum MethodPrefix {
        SET("set"), GET("get"), IS("is");

        private String value;
        private MethodPrefix(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

}
