package uk.co.huntersix.spring.rest.testutil;

/**
 * @author Birgul Ayaz
 */
public class ExceptionNotThrownAssertionError extends AssertionError {
    private static final long serialVersionUID = 1L;

    public ExceptionNotThrownAssertionError() {
        super("Expected exception was not thrown.");
    }
}
