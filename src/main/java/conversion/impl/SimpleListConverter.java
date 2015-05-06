package conversion.impl;


import conversion.ListConverter;
import conversion.exception.ConversionImpossibleException;

import java.util.List;

public class SimpleListConverter<S, T> implements ListConverter<S, T> {

    private SimpleTypeConverter<S, T> simpleTypeConverter = new SimpleTypeConverter<>();//FIXME replace with Factory

    @SuppressWarnings("unchecked")
    @Override
    public List<T> convert(List<S> source) {
        try {
            List<T> target = source.getClass().newInstance();
            if (!source.isEmpty()) {
                for (S sourceItem : source) {
                    if (simpleTypeConverter.canConvert(sourceItem)) {
                        target.add(simpleTypeConverter.convert(sourceItem));
                    }
                }
            }
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ConversionImpossibleException("Target list can not be instantiated ", e);
        }
    }

    @Override
    public boolean canConvert(List<S> source) {
        return !source.isEmpty() && simpleTypeConverter.canConvert(source.iterator().next());
    }

}
