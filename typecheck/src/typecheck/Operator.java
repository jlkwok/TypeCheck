package typecheck;

import parser.Token;

import java.util.Objects;
import java.util.Optional;

/**
 * Operator class is a component of an equation, that is represented by a operator-connector (+, -, *, /)
 * @author Phan Trinh Ha
 * @author Jessica Kwok
 */
public final class Operator implements EquationMember{
    private Token connector;	/** the connector that the Operator (plus, minus, times, divide) is created from */
    private String representation;	/** the string representation of the Operator, i.e. TerminalSymbol.PLUS : "+" */

    private Operator(Token connector){
        this.connector = connector;
    }

    /**
     * Gets the string representation of the Operator, i.e. TerminalSymbol.PLUS : "+"
     * @return string representation of this
     */
    @Override
    public final String getRepresentation() {
        if(representation == null)
            representation = connector.toString();
        return representation;
    }

    /**
     * Check if this member is an operator
     * @return true if is operator
     */
    @Override
    public boolean isOperator() {
        return true;
    }

    /**
     * A required method to build a new Operator using the private constructor
     * @param token token containing the Operator (plus, minus, times, divide)
     * @return The newly created Operator or null if the token is not an Operator
     * @throws NullPointerException input String representation is Null
     */
    public static Operator build(Token token){
    	Objects.requireNonNull(token);
        if(token.isOperator()){
            return new Operator(token);
        }
        else
            return null;
    }


    /**
     * Compare between this Operator and a given Object
     * @param o object to be compared
     * @return true if object is a Type and have the same representation
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operator operator = (Operator) o;
        return Objects.equals(getRepresentation(), operator.getRepresentation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRepresentation());
    }
}
