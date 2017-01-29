package cz.auderis.binbuilder.impl.element;

import junitparams.JUnitParamsRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

@RunWith(JUnitParamsRunner.class)
public class BasicIntegerElementTest {

    @Rule public MockitoRule mock = MockitoJUnit.rule();

    BasicIntegerElement intElement;


    @Test
    public void shouldStoreInteger(int value) throws Exception {
        // Given

        // When
        intElement.setValue(value);

        // Then

    }


}
