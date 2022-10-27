package pl.restmeans.exceptions;

public class WebScrapingException extends Exception{

    public WebScrapingException() {
        super();
    }

    public WebScrapingException(String message, Throwable cause) {
        super(message, cause);
    }
    public WebScrapingException(String message) {
        super(message);
    }

    public WebScrapingException(Throwable cause) {
        super(cause);
    }
}
