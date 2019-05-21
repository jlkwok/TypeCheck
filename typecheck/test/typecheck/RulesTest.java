package typecheck;

import static org.junit.Assert.*; 
import java.util.ArrayList;
import java.util.List;
import parser.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RulesTest {

	private Rules rules;

	@Before
	public void setUp() {
		rules = new Rules();
	}

	@After
	public void tearDown(){
		rules = null;
	}

	@Test
	public void addRule() {
		assertTrue(rules.getRuleMap().isEmpty());
	}

	@Test
	public void addRule_NominalCase(){
		EquationMember aType = Type.build("A");
		Connector minus = Connector.build(TerminalSymbol.MINUS);
		EquationMember minusOp = Operator.build(minus);
		EquationMember bType = Type.build("B");
		List<EquationMember> list = new ArrayList<>();
		list.add(aType);
		list.add(minusOp);
		list.add(bType);
		Equation eq = Equation.build(list);
		
		Type cType = Type.build("C");
		
		rules.addRule(eq, cType);
		assertFalse(rules.getRuleMap().isEmpty());
	}

	@Test
	public void infer() {
		EquationMember aType = Type.build("A");
		Connector minus = Connector.build(TerminalSymbol.MINUS);
		EquationMember minusOp = Operator.build(minus);
		EquationMember bType = Type.build("B");
		List<EquationMember> list = new ArrayList<>();
		list.add(aType);
		list.add(minusOp);
		list.add(bType);
		Equation eq = Equation.build(list);
		
		Type cType = Type.build("C");
		
		rules.addRule(eq, cType);
		assertEquals(cType, rules.infer(eq)); 
	}
	
	@Test
	public void inferWrongEq() {
		EquationMember aType = Type.build("A");
		Connector minus = Connector.build(TerminalSymbol.MINUS);
		EquationMember minusOp = Operator.build(minus);
		EquationMember bType = Type.build("B");
		List<EquationMember> list = new ArrayList<>();
		list.add(aType);
		list.add(minusOp);
		list.add(bType);
		Equation eq = Equation.build(list);
		
		Type cType = Type.build("C");
		
		rules.addRule(eq, cType);
		
		Connector plus = Connector.build(TerminalSymbol.PLUS);
		EquationMember plusOp = Operator.build(minus);
		List<EquationMember> list2 = new ArrayList<>();
		list.add(aType);
		list.add(plusOp);
		list.add(bType);
		Equation eq2 = Equation.build(list);
		assertEquals(null, rules.infer(eq2));
	}


}