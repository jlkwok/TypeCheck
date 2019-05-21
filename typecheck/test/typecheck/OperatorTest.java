package typecheck;
  
import org.junit.Assert;
import org.junit.Test;
import parser.*;

public class OperatorTest {
	@Test(expected = NullPointerException.class)
    public void buildNullTest(){
        Operator.build(null);
        Assert.fail();
    }

    @Test
    public void buildTest(){
    	Connector minus = Connector.build(TerminalSymbol.MINUS);
    	Variable a = Variable.build("a");
        Assert.assertEquals("-", Operator.build(minus).getRepresentation());
        Assert.assertEquals(null, Operator.build(a));
    }

    @Test
    public void getRepresentation(){
	    Operator testOperator = Operator.build(Connector.build(TerminalSymbol.PLUS));
	    Assert.assertEquals(testOperator.getRepresentation(), "+");
	    Assert.assertSame(testOperator.getRepresentation(), "+");
    }

    @Test
    public void isOperatorTest(){
	    Assert.assertTrue(Operator.build(Connector.build(TerminalSymbol.PLUS)).isOperator()); 
    }
    
    @Test
    public void equalsTest() {
    	Connector minus = Connector.build(TerminalSymbol.MINUS);
    	Operator minus1 = Operator.build(minus);
    	Operator minus2 = Operator.build(minus);
    	Assert.assertTrue(minus1.equals(minus2));
    	Assert.assertTrue(minus1.equals(minus1));
    	Assert.assertFalse(minus1.equals(null));
    	Assert.assertFalse(minus1.equals(new String("hello")));
    } 
}
