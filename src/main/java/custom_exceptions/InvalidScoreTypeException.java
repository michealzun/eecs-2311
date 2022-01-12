package custom_exceptions;

@SuppressWarnings("serial")
public class InvalidScoreTypeException extends TXMLException {
    public InvalidScoreTypeException(String message) {
        super(message);
    }
}
