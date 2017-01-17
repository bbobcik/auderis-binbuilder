package cz.auderis.binbuilder.schema;

import cz.auderis.test.rule.WorkFolder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.InputStream;
import java.net.URL;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by bbobcik on 1/17/17.
 */
public class SchemaParsingTest {

    private Unmarshaller xmlParser;

    @Before
    public void initializeParser() throws Exception {
        xmlParser = createValidatingParser();
    }


    @Test
    public void shouldParseDocument() throws Exception {
        final InputStream deploymentStream = getClass().getResourceAsStream("test1.xml");
        final Source deploymentSource = filterSourceNamespace(new InputSource(deploymentStream));

        // When
        final Object parsedObject = xmlParser.unmarshal(deploymentSource);

        // Then
        assertThat(parsedObject, instanceOf(ContentType.class));
    }


    public static Unmarshaller createValidatingParser() throws Exception {
        final String packages = "cz.auderis.binbuilder.schema";
        final JAXBContext jaxbCtx = JAXBContext.newInstance(packages);
        final Unmarshaller xmlParser = jaxbCtx.createUnmarshaller();
        // Enable validation
        final Schema schema = createXsdValidationSchema(jaxbCtx);
        xmlParser.setSchema(schema);
        return xmlParser;
    }

    public static Schema createXsdValidationSchema(JAXBContext context) throws Exception {
        final URL schemaURL = ContentType.class.getResource("/META-INF/binbuilder.xsd");
        final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        final Schema schema = factory.newSchema(schemaURL);
        return schema;
    }

    public static Source filterSourceNamespace(InputSource src) throws SAXException {
        if (null == src) {
            throw new NullPointerException();
        }
        // Create filter for XML namespace processing
        final XMLReader parentReader = XMLReaderFactory.createXMLReader();
        final NamespaceFilter filter = new NamespaceFilter("http://auderis.cz/ns/binbuilder/1.0", true);
        filter.setParent(parentReader);
        // Create filtered SAX source
        final SAXSource saxSource = new SAXSource(filter, src);
        return saxSource;
    }


    public static class NamespaceFilter extends XMLFilterImpl {
        final String usedNamespaceUri;
        final boolean addNamespace;
        boolean addedNamespace;

        public NamespaceFilter(String namespaceUri, boolean addNamespace) {
            super();
            this.addNamespace = addNamespace;
            if (addNamespace) {
                assert null != namespaceUri;
                this.usedNamespaceUri = namespaceUri;
            } else {
                this.usedNamespaceUri = "";
            }
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            if (addNamespace) {
                startControlledPrefixMapping();
            }
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException {
            super.startElement(this.usedNamespaceUri, localName, qName, attrs);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(this.usedNamespaceUri, localName, qName);
        }

        @Override
        public void startPrefixMapping(String prefix, String url) throws SAXException {
            if (addNamespace) {
                this.startControlledPrefixMapping();
            } else {
                //Remove the namespace, i.e. don´t call startPrefixMapping for parent!
            }
        }

        private void startControlledPrefixMapping() throws SAXException {
            if (addedNamespace || !addNamespace) {
                return;
            }
            // We should add namespace since it is set and has not yet been done.
            super.startPrefixMapping("", this.usedNamespaceUri);
            this.addedNamespace = true;
        }
    }

}
