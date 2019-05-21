package parser;

import java.util.Objects;

/**
 * A class to represent variables
 * @author Adam Stewart(axs1477)
 * @author Phan Trinh Ha(pxt177)
 */
public final class Variable extends AbstractToken{

    private static Cache<String, Variable> cache = new Cache<>(); //Used to avoid redundant variables
    private final String representation; // represents variable name (i.e. returns "a")

    /**
     * Required Private constructor for variable
     * @param representation must be a valid string
     */
    private Variable(String representation) throws NullPointerException{
        Objects.requireNonNull(representation, "Null representation");
        this.representation = representation;
    }

    /**
     * A getter for the representation variable using the defensive copy method
     * @return String representation
     */
    public String getRepresentation() {
        return this.representation;
    }

    /**
     * A required method to build a new variable this must also check through the cache
     * and it does not return a new variable with a different address in memory
     * @param representation string representation of variable
     * @return The newly created or fetched variable
     * @throws NullPointerException input String representation is Null
     */
    public static final Variable build(String representation) throws NullPointerException{
        Objects.requireNonNull(representation, "String input is null");
        return cache.get(representation, Variable::new);
    }

    /**
     * An override of the base toString method so that it returns the representation of the variable
     * @return the variable's representation given when created
     */
    @Override
    public String toString(){

        return this.getRepresentation();
    }

    /**
     * Overrides the method stub in the AbstractToken Class that returns the TerminalSymbol type of the given object.
     * This will return TerminalSymbol type variable
     * @return TerminalSymbol type VARIABLE
     */
    @Override
    public TerminalSymbol getType() {
        return TerminalSymbol.VARIABLE;
    }

    /**
     * Check if the Token has the type +, -, *, /
     * @return always false
     */
    @Override
    public boolean isOperator() {
        return false;
    }
}
