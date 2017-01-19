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

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BasicFutureElement<T extends BuilderElement<T>> implements Future<T>  {

    private Optional<BuilderElement<?>> element;

    public BasicFutureElement(BuilderElement<?> elem) {
        this.element = Optional.ofNullable(elem);
    }

    @Override
    public boolean isDone() {
        return element.isPresent();
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public T get() {
        if (!element.isPresent()) {
            synchronized (this) {
                try {
                    while (!element.isPresent()) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    throw new IllegalStateException();
                }
            }
        }
        return (T) element.get();
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws TimeoutException {
        if (!element.isPresent()) {
            synchronized (this) {
                long currentTime = System.currentTimeMillis();
                final long deadline = currentTime + unit.toMillis(timeout);
                try {
                    while ((currentTime <= deadline) && !element.isPresent()) {
                        final long millisToSleep = deadline - currentTime;
                        wait(millisToSleep);
                        currentTime = System.currentTimeMillis();
                    }
                    if (!element.isPresent()) {
                        throw new TimeoutException();
                    }
                } catch (InterruptedException e) {
                    throw new IllegalStateException();
                }
            }
        }
        return (T) element.get();
    }

    protected synchronized void set(T value) {
        this.element = Optional.ofNullable(value);
        notifyAll();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        throw new UnsupportedOperationException();
    }

}
