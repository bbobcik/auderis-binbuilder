
/*
 * Copyright 2017 Boleslav Bobcik - Auderis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cz.auderis.binbuilder.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for contentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="contentType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://auderis.cz/ns/binbuilder/1.0}abstractContentType">
 *       &lt;attribute name="byte-order" type="{http://auderis.cz/ns/binbuilder/1.0}byteOrderMode" default="big-endian" />
 *       &lt;attribute name="string-encoding" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" default="UTF-8" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contentType", namespace = "http://auderis.cz/ns/binbuilder/1.0")
public class ContentType
    extends AbstractContentType
{

    @XmlAttribute(name = "byte-order")
    protected ByteOrderMode byteOrder;
    @XmlAttribute(name = "string-encoding")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String stringEncoding;

    /**
     * Gets the value of the byteOrder property.
     * 
     * @return
     *     possible object is
     *     {@link ByteOrderMode }
     *     
     */
    public ByteOrderMode getByteOrder() {
        if (byteOrder == null) {
            return ByteOrderMode.BIG_ENDIAN;
        } else {
            return byteOrder;
        }
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
     * Gets the value of the stringEncoding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStringEncoding() {
        if (stringEncoding == null) {
            return "UTF-8";
        } else {
            return stringEncoding;
        }
    }

    /**
     * Sets the value of the stringEncoding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStringEncoding(String value) {
        this.stringEncoding = value;
    }

}
