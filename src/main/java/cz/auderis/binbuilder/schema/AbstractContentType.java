
package cz.auderis.binbuilder.schema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for abstractContentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="abstractContentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{http://auderis.cz/ns/binbuilder/1.0}include"/>
 *         &lt;element ref="{http://auderis.cz/ns/binbuilder/1.0}int"/>
 *         &lt;element ref="{http://auderis.cz/ns/binbuilder/1.0}short"/>
 *         &lt;element ref="{http://auderis.cz/ns/binbuilder/1.0}long"/>
 *         &lt;element ref="{http://auderis.cz/ns/binbuilder/1.0}string"/>
 *         &lt;element ref="{http://auderis.cz/ns/binbuilder/1.0}group"/>
 *         &lt;element ref="{http://auderis.cz/ns/binbuilder/1.0}length"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "abstractContentType", namespace = "http://auderis.cz/ns/binbuilder/1.0", propOrder = {
    "content"
})
@XmlSeeAlso({
    ContentType.class,
    GroupType.class
})
public abstract class AbstractContentType {

    @XmlElementRefs({
        @XmlElementRef(name = "int", namespace = "http://auderis.cz/ns/binbuilder/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "short", namespace = "http://auderis.cz/ns/binbuilder/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "include", namespace = "http://auderis.cz/ns/binbuilder/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "string", namespace = "http://auderis.cz/ns/binbuilder/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "group", namespace = "http://auderis.cz/ns/binbuilder/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "long", namespace = "http://auderis.cz/ns/binbuilder/1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "length", namespace = "http://auderis.cz/ns/binbuilder/1.0", type = JAXBElement.class, required = false)
    })
    @XmlMixed
    protected List<Serializable> content;

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link IntegerType }{@code >}
     * {@link JAXBElement }{@code <}{@link ShortType }{@code >}
     * {@link String }
     * {@link JAXBElement }{@code <}{@link IncludeType }{@code >}
     * {@link JAXBElement }{@code <}{@link StringType }{@code >}
     * {@link JAXBElement }{@code <}{@link GroupType }{@code >}
     * {@link JAXBElement }{@code <}{@link LongType }{@code >}
     * {@link JAXBElement }{@code <}{@link LengthType }{@code >}
     * 
     * 
     */
    public List<Serializable> getContent() {
        if (content == null) {
            content = new ArrayList<Serializable>();
        }
        return this.content;
    }

}
