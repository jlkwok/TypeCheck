package parser;

import org.junit.Assert;
import org.junit.Test;

public class VariableTest {

    @Test(expected = NullPointerException.class)
    public void buildNullTest(){
        Variable.build(null);
        Assert.fail();
    }

    @Test
    public void buildTest(){
        Assert.assertSame(Variable.build("a"), Variable.build("a"));
    }

    @Test
    public void getTypeTest(){
        TerminalSymbol expected = TerminalSymbol.VARIABLE;
        TerminalSymbol actual = Variable.build("b").getType();

        Assert.assertEquals(actual, expected);
    }
}
