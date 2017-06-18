package base.net;

/**
 * @author shsun
 */
public class XHttpResponse {
    /**
     *
     */
    private boolean error;
    /**
     * 1为Cookie失效
     */
    private int errorType;
    /**
     *
     */
    private String message;
    /**
     *
     */
    private String result;

    /**
     * 
     * @return
     */
    public boolean hasError() {
        return error;
    }

    public void setError(boolean hasError) {
        this.error = hasError;
    }

    public String getErrorMessage() {
        return message;
    }

    public void setErrorMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }
}
