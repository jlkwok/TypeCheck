package parser;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class InternalNodeTest {
    private InternalNode root ;
    private LeafNode nodeA;
    private List<Token> input = new ArrayList<>();
    private InternalNode.Builder builder ;

    @Before
    public void setup(){
        Variable a = Variable.build("a");
        Variable b = Variable.build("b");
        Connector plus = Connector.build(TerminalSymbol.PLUS);
        Connector divide = Connector.build(TerminalSymbol.DIVIDE);
        Variable c = Variable.build("c");

        input.add(a);
        input.add(plus);
        input.add(b);
        input.add(divide);
        input.add(c);

        nodeA = LeafNode.build(a);
        LeafNode nodeB = LeafNode.build(b);
        LeafNode nodeC = LeafNode.build(c);
        LeafNode nodePlus = LeafNode.build(plus);
        LeafNode nodeDivide = LeafNode.build(divide);

        InternalNode levelDivC = InternalNode.build(Arrays.asList(nodeDivide,nodeC));
        InternalNode levelBDivC = InternalNode.build(Arrays.asList(nodeB,levelDivC));
        InternalNode levelPlusYDivZ = InternalNode.build(Arrays.asList(nodePlus,levelBDivC));

        root = InternalNode.build(Arrays.asList(nodeA,levelPlusYDivZ));

        builder = new InternalNode.Builder();
        builder.addChild(LeafNode.build(Variable.build("a")));

    }

    @After
    public void tearDown(){
        root = null;
        input = null;
        nodeA = null;
        builder = null;
    }

    @Test
    public void testToString() {
        String expected = "[a,[+,[b,[/,c]]]]";
        String result = root.toString();
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testBuild() {
        Token result = root.toList().get(0);
        Token expected = nodeA.getToken() ;

        Assert.assertSame(expected, result);
    }

    @Test
    public void testToList(){
        List<Token> result = root.toList();
        Assert.assertThat(input, is(result));
    }

    @Test
    public void isFruitful() {
        Assert.assertTrue(root.isFruitful());
    }

    @Test
    public void testBuilder(){
        InternalNode.Builder expected = builder;
        InternalNode.Builder result = builder.simplify();

        Assert.assertEquals(expected, result);

    }

    @Test
    public void isFruitful1() {
        Assert.assertTrue(root.isFruitful());
    }

    @Test
    public void isOperator() {
        Assert.assertFalse(root.isOperator());
    }

    @Test
    public void isOperatorLed() {
        Assert.assertFalse(root.isOperatorLed());
    }

    @Test
    public void hasSingleLeafChild() {
        Assert.assertFalse(root.hasSingleLeafChild());
    }

    @Test
    public void firstChild() {
        Assert.assertEquals(root.firstChild().get(),nodeA);
    }
}