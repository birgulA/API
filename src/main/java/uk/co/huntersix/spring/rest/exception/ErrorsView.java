package uk.co.huntersix.spring.rest.exception;

/**
 * @author Birgul Ayaz
 */
public class ErrorsView
{
    private String code;

    public ErrorsView()
    {
    }

    public ErrorsView( String code )
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

}
