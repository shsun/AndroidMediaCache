package base.net;

/**
 * 
 */
public class XHttpRequestParameter implements java.io.Serializable, Comparable<Object> {

    private static final long serialVersionUID = 1274906854152052510L;

    private String name;

    private String value;

    /**
     * 
     * @param name
     * @param value
     */
    public XHttpRequestParameter(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public int compareTo(final Object another) {
        int compared;
        // 值比较
        final XHttpRequestParameter p = (XHttpRequestParameter) another;
        compared = name.compareTo(p.name);
        if (compared == 0) {
            compared = value.compareTo(p.value);
        }
        return compared;
    }

    @Override
    public boolean equals(final Object another) {
        if (null == another) {
            return false;
        }

        if (this == another) {
            return true;
        }

        if (another instanceof XHttpRequestParameter) {
            final XHttpRequestParameter p = (XHttpRequestParameter) another;
            return name.equals(p.name) && value.equals(p.value);
        }

        return false;
    }

}
