package base.adapter.recyclerview.support;

/**
 * @author shsun
 */
public interface SectionSupport<T> {
    /**
     * @return
     */
    public int sectionHeaderLayoutId();

    /**
     * @return
     */
    public int sectionTitleTextViewId();

    /**
     * @param t
     * @return
     */
    public String getTitle(T t);

}