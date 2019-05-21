package typecheck;

import org.junit.Assert;
import static org.junit.Assert.*;   
import org.junit.Test;

public class TypeTest {
	@Test(expected = NullPointerException.class)
    public void buildNullTest(){
        Type.build(null);
        Assert.fail();
    }

    @Test
    public void buildTest(){
        assertEquals("A", Type.build("A").getRepresentation());
    }
    
    @Test
    public void isOperatorTest() {
    	assertFalse(Type.build("A").isOperator());
    }
    
    @Test
    public void hashCodeTest() {
    	assertTrue(Type.build("a").hashCode() != 0); 
    }
    
    @Test
    public void equalsTest() {
    	Type t1 = Type.build("a");
    	Type t2 = Type.build("a");
    	assertTrue(t1.equals(t2));
    	
    	Type t3 = Type.build("b");
    	assertFalse(t1.equals(t3));
    }
} 
