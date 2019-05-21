package parser;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;

public class LeafNodeTest {
    private LeafNode test;
    private Variable a;

    @Before
    public void setup(){
        a  = Variable.build("a");
        test =  LeafNode.build(a);
    }

    @Test
    public void testGetToken() {
        Assert.assertSame(a,test.getToken());
    }

    @Test (expected = NullPointerException.class)
    public void testNullBuild() {
        LeafNode.build(null);
        Assert.fail();
    }

    @Test
    public void testBuild(){
        LeafNode result =  LeafNode.build(a);

        Assert.assertSame(a, result.getToken());
    }

    @Test
    public void testToList() {
        List<Token> expected = Arrays.asList(a);
        List<Token> result = test.toList();

        Assert.assertThat(result, is(expected));
    }

    @Test
    public void isOperatorLed() {
        Assert.assertFalse(test.isOperatorLed());
    }

    @Test
    public void hasSingleLeafChild() {
        Assert.assertFalse(test.hasSingleLeafChild());
    }

    @Test
    public void firstChild() {
        Assert.assertEquals(test.firstChild(), Optional.empty());
    }
}