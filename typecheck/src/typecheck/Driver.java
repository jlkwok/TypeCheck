package typecheck;

import parser.Node;
import parser.Variable;

import java.util.*;

/**
 * Driver class for Type checking of a given expression in parse tree format
 * @author Phan Trinh Ha
 * @author Jessica Kwok
 */
public final class Driver {

    /**
     * Driver constructor should not be invoked to enforce noninstantiability
     */
    private Driver(){
        throw new AssertionError("Constructor should not be invoked");
    }

    /**
     * Synthesize type of the given parse tree from the given rules and variable assignments
     * @param rules Conversion rule from an equation to a Type
     * @param varAssignments Assignments of Variables to Type
     * @param root root of the given parse tree
     * @throws IllegalArgumentException if the inputs are not in correct format or null is synthesized
     * @throws NullPointerException if any inputs are null
     * @return Type of the given parse tree
     */
    public static Type synthesize(Map<Equation, Type> rules,
                                  Map<Variable, Type> varAssignments, Node root) throws IllegalArgumentException, NullPointerException{
        Objects.requireNonNull(rules);
        Objects.requireNonNull(varAssignments);
        Objects.requireNonNull(root);
        return InputValidator.typeOf(rules, varAssignments, root)
                .filter(Objects::nonNull).orElseThrow(IllegalArgumentException::new);
    }

    static class TestHook{
        static Driver constructDriver() throws AssertionError{
            return new Driver();
        }
    }

}
