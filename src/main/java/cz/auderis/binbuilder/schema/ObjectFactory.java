
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

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cz.auderis.binbuilder.schema package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Include_QNAME = new QName("http://auderis.cz/ns/binbuilder/1.0", "include");
    private final static QName _String_QNAME = new QName("http://auderis.cz/ns/binbuilder/1.0", "string");
    private final static QName _Length_QNAME = new QName("http://auderis.cz/ns/binbuilder/1.0", "length");
    private final static QName _Short_QNAME = new QName("http://auderis.cz/ns/binbuilder/1.0", "short");
    private final static QName _Content_QNAME = new QName("http://auderis.cz/ns/binbuilder/1.0", "content");
    private final static QName _Int_QNAME = new QName("http://auderis.cz/ns/binbuilder/1.0", "int");
    private final static QName _Long_QNAME = new QName("http://auderis.cz/ns/binbuilder/1.0", "long");
    private final static QName _Group_QNAME = new QName("http://auderis.cz/ns/binbuilder/1.0", "group");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cz.auderis.binbuilder.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IncludeType }
     * 
     */
    public IncludeType createIncludeType() {
        return new IncludeType();
    }

    /**
     * Create an instance of {@link StringType }
     * 
     */
    public StringType createStringType() {
        return new StringType();
    }

    /**
     * Create an instance of {@link LengthType }
     * 
     */
    public LengthType createLengthType() {
        return new LengthType();
    }

    /**
     * Create an instance of {@link ShortType }
     * 
     */
    public ShortType createShortType() {
        return new ShortType();
    }

    /**
     * Create an instance of {@link ContentType }
     * 
     */
    public ContentType createContentType() {
        return new ContentType();
    }

    /**
     * Create an instance of {@link IntegerType }
     * 
     */
    public IntegerType createIntegerType() {
        return new IntegerType();
    }

    /**
     * Create an instance of {@link LongType }
     * 
     */
    public LongType createLongType() {
        return new LongType();
    }

    /**
     * Create an instance of {@link GroupType }
     * 
     */
    public GroupType createGroupType() {
        return new GroupType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IncludeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://auderis.cz/ns/binbuilder/1.0", name = "include")
    public JAXBElement<IncludeType> createInclude(IncludeType value) {
        return new JAXBElement<IncludeType>(_Include_QNAME, IncludeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://auderis.cz/ns/binbuilder/1.0", name = "string")
    public JAXBElement<StringType> createString(StringType value) {
        return new JAXBElement<StringType>(_String_QNAME, StringType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LengthType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://auderis.cz/ns/binbuilder/1.0", name = "length")
    public JAXBElement<LengthType> createLength(LengthType value) {
        return new JAXBElement<LengthType>(_Length_QNAME, LengthType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShortType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://auderis.cz/ns/binbuilder/1.0", name = "short")
    public JAXBElement<ShortType> createShort(ShortType value) {
        return new JAXBElement<ShortType>(_Short_QNAME, ShortType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContentType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://auderis.cz/ns/binbuilder/1.0", name = "content")
    public JAXBElement<ContentType> createContent(ContentType value) {
        return new JAXBElement<ContentType>(_Content_QNAME, ContentType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IntegerType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://auderis.cz/ns/binbuilder/1.0", name = "int")
    public JAXBElement<IntegerType> createInt(IntegerType value) {
        return new JAXBElement<IntegerType>(_Int_QNAME, IntegerType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LongType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://auderis.cz/ns/binbuilder/1.0", name = "long")
    public JAXBElement<LongType> createLong(LongType value) {
        return new JAXBElement<LongType>(_Long_QNAME, LongType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GroupType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://auderis.cz/ns/binbuilder/1.0", name = "group")
    public JAXBElement<GroupType> createGroup(GroupType value) {
        return new JAXBElement<GroupType>(_Group_QNAME, GroupType.class, null, value);
    }

}
