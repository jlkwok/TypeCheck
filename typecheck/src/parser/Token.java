package parser;

/**
 * The Token interface provides two methods to help manage and assess TerminalSymbol types
 * @author Adam Stewart(axs1477)
 * @author Phan Trinh Ha(pxt177)
 */
public interface Token {
    /**
     * A method that returns the TerminalSymbol type of the Token.
     * @return type of TerminalSymbol: VARIABLE, PLUS, MINUS, TIMES, DIVIDE, OPEN, CLOSE
     */
    TerminalSymbol getType();

    /**
     * Returns a boolean answer of whether or not the inputted TerminalSymbol type matches the TerminalSymbol type
     * of the object calling this method
     * @param type a TerminalSymbol which will be compared to the terminal symbol of the object calling this method
     * @return True if the input TerminalSymbol matches the TerminalSymbol of the object calling this method and false
     * otherwise
     */
    boolean matches(TerminalSymbol type);

    /**
     * Check if the Token has the type +, -, *, /
     * @return true if is, false otherwise
     */
    boolean isOperator();

    default boolean isVariable(){
        return this.getType() == TerminalSymbol.VARIABLE;
    }
}
