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

package cz.auderis.binbuilder.impl.element;

import cz.auderis.binbuilder.api.element.ElementFrame;
import cz.auderis.binbuilder.api.element.StringElement;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

public abstract class AbstractStringElement<T extends AbstractStringElement<T>> extends AbstractBuilderElement<T> implements StringElement<T> {

    protected Charset charset;
    protected CharsetEncoder encoder;
    protected boolean dataPrepared;

    protected AbstractStringElement(ElementFrame<?> parent, ElementContext context) {
        super(parent, context);
        this.charset = StandardCharsets.UTF_8;
    }

//    @Override
//    public T appendChar(char c) {
//        checkStateBeforeChange();
//        storeChar(c);
//        return (T) this;
//    }
//
//    @Override
//    public T appendText(CharSequence text) {
//        checkStateBeforeChange();
//        if ((null != text) && (0 != text.length())) {
//            storeText(text);
//        }
//        return (T) this;
//    }
//
    @Override
    public Charset getCharset() {
        assert (null == encoder) != (null == charset);
        return (null != encoder) ? encoder.charset() : charset;
    }

    @Override
    public void setCharset(Charset newCharset) {
        if (null == newCharset) {
            throw new NullPointerException();
        }
        this.charset = newCharset;
        this.encoder = null;
    }

    @Override
    public T withCharset(Charset newCharset) {
        setCharset(newCharset);
        return (T) this;
    }

    @Override
    public CharsetEncoder getEncoder() {
        return encoder;
    }

    @Override
    public void setEncoder(CharsetEncoder newEncoder) {
        if (null != newEncoder) {
            this.encoder = newEncoder;
            this.charset = null;
        } else if (null != encoder) {
            this.charset = encoder.charset();
            this.encoder = null;
        }
    }

    @Override
    public T withEncoder(CharsetEncoder newEncoder) {
        setEncoder(newEncoder);
        return (T) this;
    }

    @Override
    protected boolean prepareForClose() {
        if (dataPrepared) {
            return true;
        } else if (!super.prepareForClose()) {
            return false;
        }
        // Prepare encoder
        if (null == encoder ) {
            assert null != charset;
            encoder = charset.newEncoder();
        } else {
            encoder.reset();
        }
        dataPrepared = prepareOutputData();
        return dataPrepared;
    }

    @Override
    protected void fixLocation() {
        // Size must be calculated during prepareOutputData();
        assert size.isPresent();
    }

    protected abstract void storeChar(char c);

    protected abstract void storeText(CharSequence text);

    protected abstract boolean prepareOutputData();

}
