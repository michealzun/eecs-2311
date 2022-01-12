package custom_exceptions;

@SuppressWarnings("serial")
public abstract class TXMLException extends Exception {
    TXMLException(String message) {
        super(message);
    }
}
