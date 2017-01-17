
package cz.auderis.binbuilder.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for byteOrderMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="byteOrderMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="big-endian"/>
 *     &lt;enumeration value="little-endian"/>
 *     &lt;enumeration value="platform"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "byteOrderMode", namespace = "http://auderis.cz/ns/binbuilder/1.0")
@XmlEnum
public enum ByteOrderMode {

    @XmlEnumValue("big-endian")
    BIG_ENDIAN("big-endian"),
    @XmlEnumValue("little-endian")
    LITTLE_ENDIAN("little-endian"),
    @XmlEnumValue("platform")
    PLATFORM("platform");
    private final String value;

    ByteOrderMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ByteOrderMode fromValue(String v) {
        for (ByteOrderMode c: ByteOrderMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
