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

import cz.auderis.binbuilder.api.element.DecimalElement;

import java.math.BigDecimal;
import java.util.OptionalLong;

public class BasicDecimalElement
        extends AbstractNumericElement<BasicDecimalElement>
        implements DecimalElement<BasicDecimalElement>
{

    private BigDecimal value;

    protected BasicDecimalElement(AbstractElementFrame<?> parent, ElementContext context) {
        super(parent, context);
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public void setValue(BigDecimal newValue) {
        checkStateBeforeChange();
        if (((null == newValue) && (null != value)) || ((null != newValue) && !newValue.equals(value))) {
            this.value = newValue;
            numericParametersChanged();
        }
    }

    @Override
    protected void numericParametersChanged() {
        if (null == value) {
            setSize(OptionalLong.empty());
        } else {
            final NumberRepresentationEncoder encoder = context.getEncoder(getRepresentation());
            final int byteSize = encoder.getDecimalByteCount(value);
            setSize(OptionalLong.of(byteSize));
        }
    }

}
