package typecheck;

import parser.Node;
import parser.Parser;
import parser.Variable;

import java.util.*;

/**
 * Barricade class used to Validate the inputs given by the Driver class
 * @author Phan Trinh Ha
 * @author Jessica Kwok
 */
final class InputValidator {

	/**
	 * Validate and find the type of given the input params
	 * @param rules conversion rules from equation to type
	 * @param varAssignments assignment of each variable to a type
	 * @param rawRoot a root node of a parse tree
	 * @return an Optional of Type, empty if the validation failed
	 */
	static Optional<Type> typeOf(Map<Equation, Type> rules,
										Map<Variable, Type> varAssignments, Node rawRoot) {
		// re-parse the root to check for correct and simplified tree
		Optional<Node> rootOptional = Parser.parseInput(rawRoot.toList());

		//check for a correct and simplified parse
		if(rootOptional.isPresent()) {
			Node root = rootOptional.get();
			if (isVarsInParseTree(varAssignments, root) && isRulesValid(rules)) {
				Synthesizer synthesizer = Synthesizer.build(rules, varAssignments);
				return Optional.ofNullable(synthesizer.typeOf(root));
			}
		}
		return Optional.empty();
	}


	/**
	 * Check if all variable in parse tree is declared in the variable assignments
	 * @param varAssignments variable assignments
	 * @param root root of tree
	 * @return true if all vars in tree is declared, false otherwise
	 */
	private static boolean isVarsInParseTree(Map<Variable, Type> varAssignments, Node root) {
		return root.toList().stream().filter(token -> !token.isOperator())
				.allMatch(varAssignments::containsKey);
	}

	/**
	 * Check if given rule is valid (all the equations must be in correct format)
	 * @param rules
	 * @return
	 */
	private static boolean isRulesValid(Map<Equation, Type> rules) {
		return rules.keySet().stream().allMatch(InputValidator::isCorrectFormat);
	}

	/**
	 * Check for format correctness of an equation
	 * <ul>
	 *   <li>Case of a single variable equation: Must be a type</li>
	 * 	 <li>Case of a two variable: allow for negative numbers only</li>
	 * 	 <li>Case of a three variable: allow for "type, operator, type" format</li>
	 * </ul>
	 * @param equation
	 * @return
	 */
	private static boolean isCorrectFormat(Equation equation) {
		List<EquationMember> equationMembers = equation.getEquationMembers();
		switch(equationMembers.size()){
			case 1: return !equationMembers.get(0).isOperator();
			case 2: return isNegativeEquation(equationMembers);
			case 3: return isOperationEquation(equationMembers);
			default: return false;
		}
	}

	/**
	 * Check if given 2-sized equation is in negative format
	 * @param equationMembers list of equation members
	 * @return true if this is in "-, Type" format
	 */
	private static boolean isNegativeEquation(List<EquationMember> equationMembers) {
		//assert equationMembers.size() == 2 : "Invalid list input type";
		return equationMembers.get(0).getRepresentation().equals("-") && !equationMembers.get(1).isOperator();
	}

	/**
	 * Check if given 3-sized equation is in negative format
	 * @param equationMembers list of equation members
	 * @return true if this is in "Type, Operator, Type" format
	 */
	private static boolean isOperationEquation(List<EquationMember> equationMembers) {
		//assert equationMembers.size() == 3 : "Invalid list input type";
		return !equationMembers.get(0).isOperator() && equationMembers.get(1).isOperator() && !equationMembers.get(2).isOperator();
	} 

	public static class TestHook {

		public static boolean isVarsInParseTree(Map<Variable, Type> vars, Node root) {
			return InputValidator.isVarsInParseTree(vars, root);
		}

		public static boolean isRulesValid(Map<Equation, Type> rules) {
			return InputValidator.isRulesValid(rules);
		}
		
		public static boolean isNegativeEquation(List<EquationMember> list) {
			return InputValidator.isNegativeEquation(list);
		}
		
		public static boolean isOperationEquation(List<EquationMember> list) {
			return InputValidator.isOperationEquation(list);
		}
	}
}
