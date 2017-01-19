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

package cz.auderis.binbuilder;

import cz.auderis.binbuilder.api.element.IntegerElement;
import cz.auderis.binbuilder.api.element.LengthElement;
import cz.auderis.binbuilder.api.element.StringElement;
import cz.auderis.binbuilder.api.element.ElementId;
import cz.auderis.binbuilder.impl.MeasuringBinaryBuilder;
import cz.auderis.test.parameter.annotation.HexArray;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.nio.charset.Charset;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class MeasuringBinaryBuilderTest {

    private MeasuringBinaryBuilder binBuilder;
    private NamedSubsequenceRegistry registry;

    @Before
    public void prepareBuilder() throws Exception {
        this.binBuilder = new MeasuringBinaryBuilder();
        this.registry = binBuilder.getRegistry();
    }

    @Test
    @Parameters({
            " 0 | ",
            " 1 | 00",
            " 1 | FF",
            " 3 | 12 34 56",
            " 7 | AA 00 BB 11 CC 22 EE"
    })
    public void shouldMeasureByteSequences(long expectedLength, @HexArray byte[] bytes) throws Exception {
        binBuilder.addBytes(bytes);
        binBuilder.close();
        assertThat(binBuilder.isClosed(), is(true));
        assertThat(binBuilder.getSize().orElse(-1L), is(expectedLength));
    }

    @Test
    @Parameters({
            "1 | 1023 | 8",
            "2 | 1023 | 16",
            "3 | 1023 | 24",
            "4 | 1023 | 32",
            "8 | 1023 | 64",
    })
    public void shouldMeasureIntegers(long expectedLength, long value, int bitSize) throws Exception {
        final IntegerElement number = binBuilder.addNumber(bitSize);
        number.setLongValue(value);
        binBuilder.close();
        assertThat(binBuilder.isClosed(), is(true));
        assertThat(binBuilder.getSize().orElse(-1L), is(expectedLength));
    }

    @Test
    @Parameters({
            "0 | US-ASCII | ",
            "0 | UTF-8    | ",
            "1 | US-ASCII | x",
            "1 | UTF-8    | x",
            "3 | US-ASCII | 1+1",
            "3 | UTF-8    | 1+1",
            "6 | UTF-16BE | x+y",
    })
    public void shouldMeasureText(long expectedLength, String charset, String input) throws Exception {
        final StringElement text = binBuilder.addText(null);
        text.setCharset(Charset.forName(charset));
        text.appendText(input);
        binBuilder.close();
        assertThat(binBuilder.isClosed(), is(true));
        assertThat(binBuilder.getSize().orElse(-1L), is(expectedLength));
    }

    @Test
    @Parameters({
            " 1 |  8 | US-ASCII | ",
            " 2 | 16 | UTF-8    | ",
            " 4 | 24 | US-ASCII | x",
            " 2 |  8 | UTF-8    | x",
            " 4 |  8 | US-ASCII | 1+1",
            " 7 | 32 | UTF-8    | 1+1",
            "14 | 64 | UTF-16BE | x+y",
    })
    public void shouldMeasureTextWithLengthPrefix(long expectedLength, int lengthBits, String charset, String input) throws Exception {
        final NamedSubsequenceReference textReference = registry.getReferenceByName("textBlock");
        final LengthElement lengthElement = binBuilder.addSubsequenceLength(lengthBits);
        lengthElement.setSourceReference(Optional.of(textReference));
        final StringElement text = binBuilder.addText(null);
        text.setCharset(Charset.forName(charset));
        text.appendText(input);
        text.setId(registry.createId("textBlock"));
        binBuilder.close();
        assertThat(binBuilder.isClosed(), is(true));
        assertThat(binBuilder.getSize().orElse(-1L), is(expectedLength));
    }

    @Test
    @Parameters({
            " 1 |  8 | US-ASCII | ",
            " 2 | 16 | UTF-8    | ",
            " 4 | 24 | US-ASCII | x",
            " 2 |  8 | UTF-8    | x",
            " 4 |  8 | US-ASCII | 1+1",
            " 7 | 32 | UTF-8    | 1+1",
            "14 | 64 | UTF-16BE | x+y",
    })
    public void shouldMeasureTextWithLengthSuffix(long expectedLength, int lengthBits, String charset, String input) throws Exception {
        final ElementId textId = registry.createId("textBlock");
        final StringElement text = binBuilder.addText(null);
        text.withId(textId);
        text.setCharset(Charset.forName(charset));
        text.appendText(input);
        final NamedSubsequenceReference textReference = registry.getReferenceByName("textBlock");
        binBuilder.addSubsequenceLength(lengthBits).withSourceReference(Optional.ofNullable(textReference));
        binBuilder.close();
        assertThat(binBuilder.isClosed(), is(true));
        assertThat(binBuilder.getSize().orElse(-1L), is(expectedLength));
    }

}
