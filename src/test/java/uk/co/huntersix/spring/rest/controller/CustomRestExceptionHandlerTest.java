package uk.co.huntersix.spring.rest.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.Assert.assertEquals;

import uk.co.huntersix.spring.rest.exception.CustomRestExceptionHandler;
import uk.co.huntersix.spring.rest.exception.ErrorsView;
import uk.co.huntersix.spring.rest.exception.RestException;

/**
 * @author Birgul Ayaz
 */
@RunWith( MockitoJUnitRunner.class )
public class CustomRestExceptionHandlerTest
{

    @InjectMocks
    private CustomRestExceptionHandler handler;

    @Test
    public void shouldHandleRestException()
    {
        final RestException ex = new RestException( "code", HttpStatus.BAD_REQUEST );
        final WebRequest request = null;
        final ResponseEntity<Object> objectResponseEntity = handler.handleRestException( ex, request );
        ErrorsView view = ( ErrorsView ) objectResponseEntity.getBody();
        assertEquals( HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode() );
        assertEquals( "code", view.getCode() );
    }

    @Test
    public void shouldHandleAllException()
    {
        final Exception ex = new Exception( "message", new Throwable() );
        final WebRequest request = null;
        final ResponseEntity<Object> objectResponseEntity = handler.handleAllException( ex, request );
        ErrorsView view = ( ErrorsView ) objectResponseEntity.getBody();

        assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, objectResponseEntity.getStatusCode() );
        assertEquals( "error.global", view.getCode() );
    }
}
