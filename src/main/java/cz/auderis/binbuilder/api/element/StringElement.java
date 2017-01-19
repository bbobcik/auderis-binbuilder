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

package cz.auderis.binbuilder.api.element;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public interface StringElement<T extends StringElement<T>> extends BuilderElement<T>, Appendable, CharSequence, Readable {

    CharsetEncoder getEncoder();
    void setEncoder(CharsetEncoder newEncoder);
    T withEncoder(CharsetEncoder newEncoder);

    Charset getCharset();
    void setCharset(Charset newCharset);
    T withCharset(Charset newCharset);

    // Methods from java.lang.Appendable overridden to return correct type
    // and having IOException removed from their signature

    @Override T append(CharSequence csq);
    @Override T append(CharSequence csq, int start, int end);
    @Override T append(char c);

}
