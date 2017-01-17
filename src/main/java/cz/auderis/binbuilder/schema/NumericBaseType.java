
package cz.auderis.binbuilder.schema;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for numericBaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="numericBaseType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://auderis.cz/ns/binbuilder/1.0>intValue">
 *       &lt;attribute name="bits" type="{http://auderis.cz/ns/binbuilder/1.0}wordSize" />
 *       &lt;attribute name="byte-order" type="{http://auderis.cz/ns/binbuilder/1.0}byteOrderMode" />
 *       &lt;attribute name="signed" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "numericBaseType", namespace = "http://auderis.cz/ns/binbuilder/1.0", propOrder = {
    "value"
})
@XmlSeeAlso({
    ShortType.class,
    IntegerType.class,
    LongType.class
})
public abstract class NumericBaseType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "bits")
    protected BigInteger bits;
    @XmlAttribute(name = "byte-order")
    protected ByteOrderMode byteOrder;
    @XmlAttribute(name = "signed")
    protected Boolean signed;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the bits property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBits() {
        return bits;
    }

    /**
     * Sets the value of the bits property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBits(BigInteger value) {
        this.bits = value;
    }

    /**
     * Gets the value of the byteOrder property.
     * 
     * @return
     *     possible object is
     *     {@link ByteOrderMode }
     *     
     */
    public ByteOrderMode getByteOrder() {
        return byteOrder;
    }

    /**
     * Sets the value of the byteOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link ByteOrderMode }
     *     
     */
    public void setByteOrder(ByteOrderMode value) {
        this.byteOrder = value;
    }

    /**
     * Gets the value of the signed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isSigned() {
        if (signed == null) {
            return true;
        } else {
            return signed;
        }
    }

    /**
     * Sets the value of the signed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSigned(Boolean value) {
        this.signed = value;
    }

}
