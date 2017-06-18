package base.net;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

/**
 * @author shsun
 */
public class XSerializableCookie implements Cookie, Externalizable {

    private static final int COMMENT = 0x04;
    private static final int COMMENT_URL = 0x08;
    private static final int DOMAIN = 0x20;
    private static final int EXPIRY_DATE = 0x10;
    private static final int NAME = 0x01;
    private static final int PATH = 0x40;
    private static final int PORTS = 0x80;
    private static final int VALUE = 0x02;

    private transient Cookie cookie;
    private transient int nullMask = 0;

    public XSerializableCookie() {
        super();
    }

    public XSerializableCookie(final Cookie cookie) {
        super();
        this.cookie = cookie;
    }

    public String getComment() {
        return cookie.getComment();
    }

    public String getCommentURL() {
        return cookie.getCommentURL();
    }

    public String getDomain() {
        return cookie.getDomain();
    }

    public Date getExpiryDate() {
        return cookie.getExpiryDate();
    }

    public String getName() {
        return cookie.getName();
    }

    public String getPath() {
        return cookie.getPath();
    }

    public int[] getPorts() {
        return cookie.getPorts();
    }

    public String getValue() {
        return cookie.getValue();
    }

    public int getVersion() {
        return cookie.getVersion();
    }

    public boolean isExpired(final Date date) {
        return cookie.isExpired(date);
    }

    public boolean isPersistent() {
        return cookie.isPersistent();
    }

    public boolean isSecure() {
        return cookie.isSecure();
    }

    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        nullMask = in.readInt();
        String name = null;
        String value = null;
        String comment = null;
        Date expiryDate = null;
        String domain = null;
        String path = null;
        int[] ports = null;
        boolean isSecure = false;
        int version = 0;

        if ((nullMask & XSerializableCookie.NAME) == 0) {
            name = in.readUTF();
        }

        if ((nullMask & XSerializableCookie.VALUE) == 0) {
            value = in.readUTF();
        }

        if ((nullMask & XSerializableCookie.COMMENT) == 0) {
            comment = in.readUTF();
        }

        if ((nullMask & XSerializableCookie.COMMENT_URL) == 0) {
            in.readUTF();
        }

        if ((nullMask & XSerializableCookie.EXPIRY_DATE) == 0) {
            expiryDate = new Date(in.readLong());
        }

        in.readBoolean();

        if ((nullMask & XSerializableCookie.DOMAIN) == 0) {
            domain = in.readUTF();
        }

        if ((nullMask & XSerializableCookie.PATH) == 0) {
            path = in.readUTF();
        }

        if ((nullMask & XSerializableCookie.PORTS) == 0) {
            final int len = in.readInt();
            ports = new int[len];
            for (int i = 0; i < len; i++) {
                ports[i] = in.readInt();
            }
        }

        isSecure = in.readBoolean();
        version = in.readInt();
        final BasicClientCookie bc = new BasicClientCookie(name, value);
        bc.setComment(comment);
        bc.setDomain(domain);
        bc.setExpiryDate(expiryDate);
        bc.setPath(path);
        bc.setSecure(isSecure);
        bc.setVersion(version);
        cookie = bc;
    }

    public String toString() {
        if (cookie == null) {
            return "null";
        } else {
            return cookie.toString();
        }
    }

    public void writeExternal(final ObjectOutput out) throws IOException {
        nullMask |= (getName() == null) ? XSerializableCookie.NAME : 0;
        nullMask |= (getValue() == null) ? XSerializableCookie.VALUE : 0;
        nullMask |= (getComment() == null) ? XSerializableCookie.COMMENT : 0;
        nullMask |= (getCommentURL() == null) ? XSerializableCookie.COMMENT_URL : 0;
        nullMask |= (getExpiryDate() == null) ? XSerializableCookie.EXPIRY_DATE : 0;
        nullMask |= (getDomain() == null) ? XSerializableCookie.DOMAIN : 0;
        nullMask |= (getPath() == null) ? XSerializableCookie.PATH : 0;
        nullMask |= (getPorts() == null) ? XSerializableCookie.PORTS : 0;

        out.writeInt(nullMask);

        if ((nullMask & XSerializableCookie.NAME) == 0) {
            out.writeUTF(getName());
        }

        if ((nullMask & XSerializableCookie.VALUE) == 0) {
            out.writeUTF(getValue());
        }

        if ((nullMask & XSerializableCookie.COMMENT) == 0) {
            out.writeUTF(getComment());
        }

        if ((nullMask & XSerializableCookie.COMMENT_URL) == 0) {
            out.writeUTF(getCommentURL());
        }

        if ((nullMask & XSerializableCookie.EXPIRY_DATE) == 0) {
            out.writeLong(getExpiryDate().getTime());
        }

        out.writeBoolean(isPersistent());

        if ((nullMask & XSerializableCookie.DOMAIN) == 0) {
            out.writeUTF(getDomain());
        }

        if ((nullMask & XSerializableCookie.PATH) == 0) {
            out.writeUTF(getPath());
        }

        if ((nullMask & XSerializableCookie.PORTS) == 0) {
            if (getPorts() != null) {
                out.writeInt(getPorts().length);
                for (final int p : getPorts()) {
                    out.writeInt(p);
                }
            }
        }

        out.writeBoolean(isSecure());
        out.writeInt(getVersion());
    }
}
