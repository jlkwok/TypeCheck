package parser;

import org.junit.Assert;
import org.junit.Test;

public class ConnectorTest {

    @Test(expected = NullPointerException.class)
    public void buildNullTest(){
        Connector.build(null);
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildIllegalTest(){
        Connector.build(TerminalSymbol.VARIABLE);
        Assert.fail();
    }

    @Test
    public void buildTest(){
        Assert.assertEquals(TerminalSymbol.MINUS, Connector.build(TerminalSymbol.MINUS).getType());
    }
}
