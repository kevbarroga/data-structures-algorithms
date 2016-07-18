/**
 * An exception class that is thrown and caught by the program.
 */
@SuppressWarnings("serial")
public class InvalidEdgeException extends Exception {
	/**
     * Initializes a new instance of the class.
     * 
     * @param message
     *            The message.
     */
    public InvalidEdgeException(final String message) {
        super(message);
    }
    
    /**
     * Initializes a new instance of the class.
     * 
     * @param message
     *            The message.
     * @param cause
     *            The cause.
     */
    public InvalidEdgeException(final String message, final Throwable cause) {
        super(message, cause);
    }
}