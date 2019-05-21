package typecheck;

import java.util.ArrayList; 
import java.util.List;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parser.Connector;
import parser.TerminalSymbol;

public class EquationTest {

	private List<EquationMember> equationMemberList = new ArrayList<>();
	private Connector minus = Connector.build(TerminalSymbol.MINUS);
	private EquationMember minusOp = Operator.build(minus);
	private EquationMember aType = Type.build("A");
	private EquationMember bType = Type.build("B");


	@Before
	public void setUp(){
		equationMemberList.add(minusOp);
		equationMemberList.add(aType);
	}
	@Test(expected = NullPointerException.class)
    public void buildNullTest(){
        Equation.build(null);
        Assert.fail();
    }

    @Test
    public void buildTest(){
        assertFalse(Equation.build(equationMemberList).getEquationMembers().isEmpty());
    }
    
    @Test
    public void hashCodeTest() {
    	assertFalse(Equation.build(equationMemberList).hashCode() == 0);
    } 
    
    @Test
    public void equalsTest() {
    	assertTrue(Equation.build(equationMemberList).equals(Equation.build(equationMemberList)));
    	List<EquationMember> list2 = new ArrayList<>();
    	list2.add(minusOp);
    	list2.add(bType);
    	assertFalse(Equation.build(equationMemberList).equals(Equation.build(list2)));
    }
}

