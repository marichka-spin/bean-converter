package conversion.exception;

public class ConversionImpossibleException extends RuntimeException {

    public ConversionImpossibleException() {

    }

    public ConversionImpossibleException(String message) {
        super(message);
    }

    public ConversionImpossibleException(String message, Object... args) {
        super(String.format(message, args));
    }

    public ConversionImpossibleException(Throwable e) {
        super(e);
    }

    public ConversionImpossibleException(String message, Throwable e) {
        super(message, e);
    }

}
