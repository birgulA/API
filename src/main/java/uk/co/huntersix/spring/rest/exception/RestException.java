package uk.co.huntersix.spring.rest.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Birgul Ayaz
 */
public class RestException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    private final HttpStatus status;

    public RestException( String code )
    {
        super( code );
        this.status = HttpStatus.BAD_REQUEST;
    }

    public RestException( String code, Throwable cause )
    {
        super( code, cause );
        this.status = HttpStatus.BAD_REQUEST;
    }

    public RestException( String code, HttpStatus status )
    {
        super( code );
        this.status = status;
    }

    public RestException( String code, Throwable cause, HttpStatus status )
    {
        super( code, cause );
        this.status = status;
    }

    public HttpStatus getStatus()
    {
        return status;
    }
}
