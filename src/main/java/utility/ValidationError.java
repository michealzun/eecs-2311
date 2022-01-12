package utility;

import java.util.ArrayList;
import java.util.List;

public class ValidationError {
    int priority;
    List<Range> positions = new ArrayList<>();
    String message;

    public ValidationError(String message, int priority, List<Range> positions) {
        this.message = message;
        this.priority = priority;
        this.positions = positions;
    }
    
    public int getPriority() {
    	return this.priority;
    }
    
    public List<Range> getPositions() {
    	return positions;
    }
    
    public String getMessage() {
    	return this.message;
    }
}
