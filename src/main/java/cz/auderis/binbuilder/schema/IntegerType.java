
package cz.auderis.binbuilder.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for integerType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="integerType">
 *   &lt;simpleContent>
 *     &lt;restriction base="&lt;http://auderis.cz/ns/binbuilder/1.0>numericBaseType">
 *       &lt;attribute name="bits" type="{http://auderis.cz/ns/binbuilder/1.0}wordSize" fixed="32" />
 *     &lt;/restriction>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "integerType", namespace = "http://auderis.cz/ns/binbuilder/1.0")
public class IntegerType
    extends NumericBaseType
{


}
