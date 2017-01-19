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

package cz.auderis.binbuilder.impl;

import cz.auderis.binbuilder.api.element.ElementId;

public class BasicSubsequenceId implements ElementId {

    private final String name;

    public BasicSubsequenceId(String name) {
        if (null == name) {
            throw new NullPointerException();
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof ElementId)) {
            return false;
        }
        final ElementId other = (ElementId) obj;
        return name.equals(other.getName());
    }

    @Override
    public String toString() {
        return "SubseqId{" + name + '}';
    }

}
