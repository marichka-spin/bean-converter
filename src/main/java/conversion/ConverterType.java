package conversion;

public class ConverterType {

    public static final ConverterType SIMPLE;

    public static final ConverterType COMPLEX;

    static {
        SIMPLE = new ConverterType(1);
        COMPLEX = new ConverterType(2);
    }

    private int value;

    public ConverterType(int value) {
        this.value = value;
    }

    public int type() {
        return value;
    }

}
