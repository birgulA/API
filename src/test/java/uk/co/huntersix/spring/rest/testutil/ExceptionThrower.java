package uk.co.huntersix.spring.rest.testutil;

/**
 * @author Birgul Ayaz
 */
@FunctionalInterface
public interface ExceptionThrower {
    void throwException() throws Throwable;
}
