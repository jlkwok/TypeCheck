package parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TerminalSymbolTest {
    private TerminalSymbol test;
    @Before
    public void setUp() {
        test = TerminalSymbol.MINUS;
    }

    @After
    public void tearDown() {
    }


    @Test
    public void toStringTest() {
        String expected = "-";
        String actual = test.toString();

        assertEquals(expected,actual);
    }

    @Test
    public void parseTest() {
        ParseState expected = ParseState.FAILURE;
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(Connector.build(TerminalSymbol.TIMES));
        ParseState actual = test.parse(tokens);

        assertSame(expected.getNode(),actual.getNode());

        ArrayList<Token> tokens2 = new ArrayList<>();
        tokens2.add(Connector.build(TerminalSymbol.MINUS));
        ParseState actual2 = test.parse(tokens2);

        assertTrue(actual2.isSuccess());
        assertSame("-",actual2.getNode().toString());
    }
}