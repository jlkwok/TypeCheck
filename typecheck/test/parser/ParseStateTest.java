package parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ParseStateTest {
    private ParseState test;
    private ArrayList<Token> remainder;
    private LeafNode node;

    @Before
    public void setUp() {
        remainder = new ArrayList<>();
        remainder.add(Connector.build(TerminalSymbol.TIMES));
        remainder.add(Connector.build(TerminalSymbol.MINUS));
        node = LeafNode.build(Connector.build(TerminalSymbol.TIMES));
        test = ParseState.build(true,remainder ,node);

    }

    @After
    public void tearDown(){
    }

    @Test
    public void getRemainderTest() {
        ArrayList<Token> expected = remainder;
        ArrayList<Token> actual = new ArrayList<>();
        actual.addAll(test.getRemainder());
        assertEquals(expected,actual);
    }

    @Test
    public void isSuccessTest() {
        assertTrue(test.isSuccess());
    }

    @Test
    public void getNodeTest() {
        Node expected  = node;
        Node actual = test.getNode();
        assertSame(expected,actual);

    }

    @Test
    public void hasNoRemainderTest() {
        assertFalse(test.hasNoRemainder());
    }

    @Test
    public void buildTest() {
        ParseState expected = test;
        ArrayList<Token> remain = new ArrayList<>();
        remain.add(Connector.build(TerminalSymbol.TIMES));
        remain.add(Connector.build(TerminalSymbol.MINUS));
        Node nod = node;
        ParseState actual = ParseState.build(true,remain,nod);

        assertEquals(expected.isSuccess(),actual.isSuccess());
        assertEquals(expected.getNode(),actual.getNode());
    }
}