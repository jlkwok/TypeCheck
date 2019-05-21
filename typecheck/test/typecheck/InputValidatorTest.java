package typecheck;

import org.junit.Test;
import parser.*;

import java.util.*;
import static org.junit.Assert.*;
import static parser.TerminalSymbol.DIVIDE;
import static parser.TerminalSymbol.PLUS;

public class InputValidatorTest {

	@Test
	public void inputValidator() {
		InputValidator iv = new InputValidator();
	}

	@Test
	public void isVarsInParseTree() {
		Type aType = Type.build("A");
		Type bType = Type.build("B");
		Type cType = Type.build("C");
		Variable aVar = Variable.build("a");
		Variable bVar = Variable.build("b");
		Variable cVar = Variable.build("c");
		Map<Variable, Type> varAssignMap = new HashMap<>();
		varAssignMap.put(aVar, aType);
		varAssignMap.put(bVar, bType);
		varAssignMap.put(cVar, cType);

		List<Token> nomTokenList = Parser.parseStringInput("a + b/c");
		Node nomRoot = Parser.parseInput(nomTokenList).orElseThrow(AssertionError::new);
		assertTrue(InputValidator.TestHook.isVarsInParseTree(varAssignMap, nomRoot));

		List<Token> badTokenList = Parser.parseStringInput("a + b/c - d");
		Node badRoot = Parser.parseInput(badTokenList).orElseThrow(AssertionError::new);
		assertFalse(InputValidator.TestHook.isVarsInParseTree(varAssignMap, badRoot));
	}

	@Test
	public void isRulesValid() {
		Type aType = Type.build("A");
		Type bType = Type.build("B");
		Type cType = Type.build("C");
		Type dType = Type.build("D");

		Connector minus = Connector.build(TerminalSymbol.MINUS);
		EquationMember minusOp = Operator.build(minus);
		Connector plus = Connector.build(TerminalSymbol.PLUS);
		EquationMember plusOp = Operator.build(plus);

		// A: good data
		List<EquationMember> list1 = new ArrayList<>();
		list1.add(aType);
		Equation eq1 = Equation.build(list1);
		// -C: good data
		List<EquationMember> list2 = new ArrayList<>();
		list2.add(minusOp);
		list2.add(cType);
		Equation eq2 = Equation.build(list2);
		// b + c => d: good data
		List<EquationMember> list3 = new ArrayList<>();
		list3.add(bType);
		list3.add(plusOp);
		list3.add(cType);
		Equation eq3 = Equation.build(list3);
		// +C: bad data
		List<EquationMember> list4 = new ArrayList<>();
		list4.add(plusOp);
		list4.add(cType);
		Equation eq4 = Equation.build(list4);
		// B A C: bad data
		List<EquationMember> list5 = new ArrayList<>();
		list5.add(bType);
		list5.add(aType);
		list5.add(cType);
		Equation eq5 = Equation.build(list5);
		// A B C D: bad data
		List<EquationMember> list6 = new ArrayList<>();
		list6.add(aType);
		list6.add(bType);
		list6.add(cType);
		list6.add(dType);
		Equation eq6 = Equation.build(list6);
		List<EquationMember> list7 = new ArrayList<>();
		list7.add(minusOp);
		Equation eq7 = Equation.build(list7);

		Map<Equation, Type> validMap = new HashMap<>();
		validMap.put(eq1, cType);
		validMap.put(eq2, cType);
		validMap.put(eq3, cType);
		assertTrue(InputValidator.TestHook.isRulesValid(validMap));
		Map<Equation, Type> invalidMapNegEq = new HashMap<>();
		invalidMapNegEq.put(eq1, cType);
		invalidMapNegEq.put(eq2, cType);
		invalidMapNegEq.put(eq3, cType);
		invalidMapNegEq.put(eq4, cType);
		assertFalse(InputValidator.TestHook.isRulesValid(invalidMapNegEq));
		Map<Equation, Type> invalidMapWrongFormat = new HashMap<>();
		invalidMapWrongFormat.put(eq1, cType);
		invalidMapWrongFormat.put(eq2, cType);
		invalidMapWrongFormat.put(eq3, cType);
		invalidMapWrongFormat.put(eq5, cType);
		assertFalse(InputValidator.TestHook.isRulesValid(invalidMapWrongFormat));
		Map<Equation, Type> invalidMapLong = new HashMap<>();
		invalidMapLong.put(eq1, cType);
		invalidMapLong.put(eq2, cType);
		invalidMapLong.put(eq3, cType);
		invalidMapLong.put(eq6, cType);
		assertFalse(InputValidator.TestHook.isRulesValid(invalidMapLong));
		Map<Equation, Type> invalidMapLength1 = new HashMap<>();
		invalidMapLength1.put(eq1, cType);
		invalidMapLength1.put(eq2, cType);
		invalidMapLength1.put(eq7, cType);
		assertFalse(InputValidator.TestHook.isRulesValid(invalidMapLength1));
	}

	@Test
	public void isNegativeEquation() {
		Type aType = Type.build("A");

		Connector minus = Connector.build(TerminalSymbol.MINUS);
		EquationMember minusOp = Operator.build(minus);
		Connector plus = Connector.build(TerminalSymbol.PLUS);
		EquationMember plusOp = Operator.build(plus);

		// TT
		List<EquationMember> listTT = new ArrayList<>();
		listTT.add(minusOp);
		listTT.add(aType);
		assertTrue(InputValidator.TestHook.isNegativeEquation(listTT));
		// TF
		List<EquationMember> listTF = new ArrayList<>();
		listTF.add(minusOp);
		listTF.add(minusOp);
		assertFalse(InputValidator.TestHook.isNegativeEquation(listTF));
		// FT
		List<EquationMember> listFT = new ArrayList<>();
		listFT.add(plusOp);
		listFT.add(aType);
		assertFalse(InputValidator.TestHook.isNegativeEquation(listFT));
		// FF
		List<EquationMember> listFF = new ArrayList<>();
		listFF.add(plusOp);
		listFF.add(minusOp);
		assertFalse(InputValidator.TestHook.isNegativeEquation(listFF));
	}

	@Test
	public void isOperationEquation() {
		Type aType = Type.build("A");
		Type bType = Type.build("B");

		Connector minus = Connector.build(TerminalSymbol.MINUS);
		EquationMember minusOp = Operator.build(minus);
		Connector plus = Connector.build(TerminalSymbol.PLUS);
		EquationMember plusOp = Operator.build(plus);
		// TTT
		List<EquationMember> listTTT = new ArrayList<>();
		listTTT.add(bType);
		listTTT.add(minusOp);
		listTTT.add(aType);
		assertTrue(InputValidator.TestHook.isOperationEquation(listTTT)); 
		// TFT
		List<EquationMember> listTFT = new ArrayList<>();
		listTFT.add(bType);
		listTFT.add(aType);
		listTFT.add(bType);
		assertFalse(InputValidator.TestHook.isOperationEquation(listTFT));
		// TFF
		List<EquationMember> listTFF = new ArrayList<>();
		listTFF.add(bType);
		listTFF.add(aType);
		listTFF.add(minusOp);
		assertFalse(InputValidator.TestHook.isOperationEquation(listTFF));
		// FTT
		List<EquationMember> listFTT = new ArrayList<>();
		listFTT.add(plusOp);
		listFTT.add(plusOp);
		listFTT.add(aType);
		assertFalse(InputValidator.TestHook.isOperationEquation(listFTT));
		// FFT
		List<EquationMember> listFFT = new ArrayList<>();
		listFFT.add(plusOp);
		listFFT.add(bType);
		listFFT.add(aType);
		assertFalse(InputValidator.TestHook.isOperationEquation(listFFT));
		// FFF
		List<EquationMember> listFFF = new ArrayList<>();
		listFFF.add(plusOp);
		listFFF.add(aType);
		listFFF.add(minusOp);
		assertFalse(InputValidator.TestHook.isOperationEquation(listFFF));
		// TTF
		List<EquationMember> listTTF = new ArrayList<>();
		listTTF.add(bType);
		listTTF.add(plusOp);
		listTTF.add(plusOp);
		assertFalse(InputValidator.TestHook.isOperationEquation(listTTF));
		// FTF
		List<EquationMember> listFTF = new ArrayList<>();
		listFTF.add(plusOp);
		listFTF.add(plusOp);
		listFTF.add(plusOp);
		assertFalse(InputValidator.TestHook.isOperationEquation(listFTF));
	}

	@Test
	public void findTypeOf() {
		Type aType = Type.build("A");
		Type bType = Type.build("B");
		Type cType = Type.build("C");
		Type dType = Type.build("D");
		Variable aVar = Variable.build("a");
		Variable bVar = Variable.build("b");
		Variable cVar = Variable.build("c");
		Variable dVar = Variable.build("d");
		Set<Type> types = new HashSet<>();
		types.add(aType);
		types.add(bType);
		types.add(cType);
		Map<Variable, Type> varAssignMap = new HashMap<>();
		varAssignMap.put(aVar, aType);
		varAssignMap.put(bVar, bType);
		varAssignMap.put(cVar, cType);

		Map<Equation, Type> ruleMap = new HashMap<>();
		ruleMap.put(
				Equation.build(Arrays.asList(Type.build("A"), Operator.build(Connector.build(PLUS)), Type.build("B"))),
				Type.build("A"));
		ruleMap.put(
				Equation.build(Arrays.asList(Type.build("A"), Operator.build(Connector.build(PLUS)), Type.build("C"))),
				Type.build("B"));
		ruleMap.put(
				Equation.build(
						Arrays.asList(Type.build("B"), Operator.build(Connector.build(DIVIDE)), Type.build("C"))),
				Type.build("A"));
		ruleMap.put(
				Equation.build(
						Arrays.asList(Type.build("A"), Operator.build(Connector.build(DIVIDE)), Type.build("C"))),
				Type.build("A"));
		ruleMap.put(
				Equation.build(Arrays.asList(Type.build("A"), Operator.build(Connector.build(PLUS)), Type.build("A"))),
				Type.build("A"));

		List<Token> nomTokenList = Parser.parseStringInput("a + b/c");
		Node nomRoot = Parser.parseInput(nomTokenList).orElseThrow(AssertionError::new);
		List<Token> badTokenList = Parser.parseStringInput("d + e");
		Node badRoot = Parser.parseInput(badTokenList).orElseThrow(AssertionError::new);
		Node connectorRoot = LeafNode.build(Connector.build(PLUS));

		//TTT
		assertEquals(aType, InputValidator.typeOf(ruleMap, varAssignMap, nomRoot).orElseThrow(AssertionError::new));

		//FTT
		assertFalse(InputValidator.typeOf(ruleMap, varAssignMap, connectorRoot).isPresent());

		//TFT
		assertEquals(Optional.empty(), InputValidator.typeOf(ruleMap, varAssignMap, badRoot));

		//TTF
		ruleMap.put(
				Equation.build(Arrays.asList(Type.build("A"), Type.build("B"), Type.build("A"))),
				Type.build("C"));
		assertEquals(Optional.empty(), InputValidator.typeOf(ruleMap, varAssignMap, nomRoot));
		}
}
