package parser;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;


public class SymbolSequenceTest {
    private SymbolSequence tester;
    private SymbolSequence tester2;
    private SymbolSequence tester3;

    @Before
    public void setUp()  {
      tester =  SymbolSequence.build(TerminalSymbol.TIMES);
      tester2 = SymbolSequence.build(TerminalSymbol.TIMES,TerminalSymbol.MINUS);
      tester3 = SymbolSequence.build(TerminalSymbol.MINUS, NonTerminalSymbol.TERM, NonTerminalSymbol.EXPRESSION_TAIL);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void buildTest() {
        SymbolSequence expected = tester;
        SymbolSequence result = SymbolSequence.build(TerminalSymbol.TIMES);

        Assert.assertEquals(expected.toString(),result.toString());


    }

    @Test
    public void build1Test() {
        SymbolSequence expected = tester2;
        SymbolSequence result = SymbolSequence.build(TerminalSymbol.TIMES,TerminalSymbol.MINUS);

        Assert.assertEquals(expected.toString(),result.toString());
    }

    @Test
    public void toStringTest() {
        String expected = "[*]";
        String result  = tester.toString();
        String expected2 = "[*, -]";
        String result2  = tester2.toString();
        String expected3 = "[-, TERM, EXPRESSION_TAIL]";
        String result3 = tester3.toString();

        Assert.assertEquals(expected,result);
        Assert.assertEquals(expected2,result2);
        Assert.assertEquals(expected3, result3);
    }

    @Test
    public void matchTest() {
        String expected ="[*]";
        String actual =  tester.match(Arrays.asList(Connector.build(TerminalSymbol.TIMES))).getNode().toString();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void longMatchTest(){
        String expected ="[*,-]";
        String actual =  tester2.match(Arrays.asList(Connector.build(TerminalSymbol.TIMES),Connector.build(TerminalSymbol.MINUS))).getNode().toString();
        Assert.assertEquals(expected,actual);
    }
}