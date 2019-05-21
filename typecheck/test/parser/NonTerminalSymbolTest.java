package parser;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.LinkedList;
import java.util.List;

public class NonTerminalSymbolTest {

    private List<Token> input = new LinkedList<>();

    @Before
    public void setUp() {
        input.add(Variable.build("a"));
        input.add(Connector.build(TerminalSymbol.PLUS));
        input.add(Variable.build("b"));
        input.add(Connector.build(TerminalSymbol.DIVIDE));
        input.add(Variable.build("c"));
    }

    @After
    public void tearDown(){
        input = null;
    }

    @Test
    public void testToString(){
        String expected = "EXPRESSION";
        Assert.assertEquals(expected, NonTerminalSymbol.EXPRESSION.toString());
    }

    @Test
    public void testParse() {
        ParseState result = NonTerminalSymbol.EXPRESSION.parse(input);
        Assert.assertTrue(result.isSuccess());
        Assert.assertTrue(result.hasNoRemainder());
    }

    @Test
    public void parseInputDemo() {
        try {
            Node result = NonTerminalSymbol.parseInput(input).orElseThrow(NullPointerException::new);
            System.out.println("Pruned and simplified tree after map implementation. " +
                                "\nPruned representation is:");
            System.out.println(result);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            Assert.fail();
        }
    }
}