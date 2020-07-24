package uk.co.huntersix.spring.rest.exception;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Birgul Ayaz
 */
@ControllerAdvice
@RestControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler
{
    private static final Logger LOG = LoggerFactory.getLogger( CustomRestExceptionHandler.class );

    @ExceptionHandler( { RestException.class } )
    public ResponseEntity<Object> handleRestException( RestException ex, WebRequest request )
    {
        LOG.info( "resolveRestException::{}", ex.getMessage() );
        ErrorsView view = new ErrorsView( ex.getMessage() );
        return ResponseEntity.status( ex.getStatus() ).body( view );
    }

    @ExceptionHandler( { Exception.class } )
    public ResponseEntity<Object> handleAllException( Exception ex, WebRequest request )
    {
        LOG.error( "Global error occurred::{}", ex.getMessage(), ex );
        ErrorsView view = new ErrorsView( "error.global" );
        return ResponseEntity.status( HttpServletResponse.SC_INTERNAL_SERVER_ERROR ).body( view );
    }
}

