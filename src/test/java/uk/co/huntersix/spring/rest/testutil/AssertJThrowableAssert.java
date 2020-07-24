package uk.co.huntersix.spring.rest.testutil;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;


/**
 * @author Birgul Ayaz
 */
public class AssertJThrowableAssert {
    public static ThrowableAssert assertThrown( ExceptionThrower exceptionThrower) {
        try {
            exceptionThrower.throwException();
        } catch (Throwable throwable) {
            return (ThrowableAssert) Assertions.assertThat(throwable);
        }
        throw new ExceptionNotThrownAssertionError();
    }
}
