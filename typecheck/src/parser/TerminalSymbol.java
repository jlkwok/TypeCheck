package parser;

import java.util.List;
import java.util.Objects;
import java.util.LinkedList;

/**
 * An enum to store all the different types of TerminalSymbols
 * @author Adam Stewart(axs1477)
 * @author Phan Trinh Ha(pxt177)
 */
public enum TerminalSymbol implements Symbol{
    VARIABLE("var"), PLUS("+"), MINUS("-"), TIMES("*"),
    DIVIDE("/"), OPEN("("), CLOSE(")");

    private String representation;

    /**
     * Package private constructor for TerminalSymbol
     * @param representation Expected string representation
     */
    TerminalSymbol(String representation){
        this.representation = representation;
    }

    /**
     * Return the String representation of the TerminalSymbol enum
     * @return String representation of TerminalSymbol enum
     */
    @Override
    public String toString(){
        return this.representation;
    }

    /**
     * Parses the inputted List&lt;Token&gt; for the object calling this method
     * @param tokens a List&lt;Token&gt; that you wish to parse
     * @return a ParseState of the given input with success set to true if it worked and false otherwise
     */
    @Override
    public ParseState parse(List<Token> tokens){
        Objects.requireNonNull(tokens, "Input is null, please enter a valid nonnull List<Token>");
        LinkedList<Token> remainder = new LinkedList<>(tokens) ;

        if (!remainder.isEmpty() && (remainder.getFirst().matches(this))) {
            Token token = remainder.removeFirst();
            return ParseState.build(true, remainder, LeafNode.build(token));
        }
        else {
            return ParseState.FAILURE;
        }
    }
}
