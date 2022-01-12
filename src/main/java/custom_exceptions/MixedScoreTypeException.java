package custom_exceptions;

@SuppressWarnings("serial")
public class MixedScoreTypeException extends TXMLException {
    public MixedScoreTypeException(String message) {
        super(message);
    }
}
