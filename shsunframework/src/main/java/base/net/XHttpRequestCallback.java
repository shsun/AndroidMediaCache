package base.net;

/**
 * @author shsun
 *
 *         run on UI thread
 */
public interface XHttpRequestCallback<T> {
    /**
     *
     * @param content
     */
    void onSuccess(T content);

    /**
     *
     * @param message
     */
    void onFail(String message);

    /**
     */
    void onCookieExpired();
}
