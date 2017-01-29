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

import cz.auderis.binbuilder.api.element.BuilderElement;
import cz.auderis.binbuilder.api.element.ElementId;

import java.util.Optional;

public class ElementReference {

    private final ElementId<?> dependantId;
    private BasicFutureElement<?> futureDependant;
    private BuilderElement<?> dependant;

    public ElementReference(ElementId<?> dependantId) {
        if (null == dependantId) {
            throw new NullPointerException();
        }
        this.dependantId = dependantId;
    }

    public <U extends ElementId<U>> U getReferenceId() {
        return (U) dependantId;
    }

    public <U extends BuilderElement<U>> Optional<U> getDependant() {
        if (null != dependant) {
            return Optional.of((U) dependant);
        } else if ((null != futureDependant) && futureDependant.isDone()) {
            this.dependant = futureDependant.get();
            if (null != dependant) {
                this.futureDependant = null;
                dependantResolved(this.dependant);
                return Optional.of((U) dependant);
            }
        }
        return Optional.empty();
    }

    protected void changeDependant(BasicFutureElement<?> futureDependant) {
        assert null != futureDependant;
        if (null != dependant) {
            throw new IllegalStateException();
        } else if (futureDependant.isDone()) {
            this.dependant = futureDependant.get();
            assert null != dependant;
            this.futureDependant = null;
            dependantResolved(this.dependant);
        } else {
            this.dependant = null;
            this.futureDependant = futureDependant;
        }
    }

    protected void changeDependant(BuilderElement<?> dependant) {
        assert null != dependant;
        if ((null != this.dependant) && (this.dependant != dependant)) {
            throw new IllegalStateException();
        }
        this.dependant = dependant;
        this.futureDependant = null;
        dependantResolved(this.dependant);
    }

    protected <U extends BuilderElement<U>> void dependantResolved(BuilderElement<U> dependantElement) {
        // No action
    }

}
