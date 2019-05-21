package parser;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Used to represent a sequence of symbols stored in its production variable
 * @author Adam Stewart(axs1477)
 * @author Phan Trinh Ha(pxt177)
 */
final class SymbolSequence {

    private final List<Symbol> production;
    static final SymbolSequence EPSILON = SymbolSequence.build(new ArrayList<>());

    /**
     * A private constructor to create SymbolSequences
     * @param production A valid list of symbols
     */
    private SymbolSequence(List<Symbol> production){
        this.production = production;
    }

    /**
     * A method to create SymbolSequence objects
     * @param production must be a valid List&lt;Symbol&gt; that you wish to instantiate the SymbolSequence with
     * @return a new SymbolSequence with the given production
     * @throws NullPointerException if the inputted production is null
     */
    public static SymbolSequence build(List<Symbol> production) throws NullPointerException{
        Objects.requireNonNull(production,"Not a valid List<Symbol>");
        return new SymbolSequence(production);
    }

    /**
     * An alternative method to call when building new SymbolSequences
     * @param symbols any amount of valid symbols
     * @return a new SymbolSequence object with the given input as parameters
     * @throws NullPointerException if symbols is null
     */
    public static final SymbolSequence build(Symbol... symbols) throws NullPointerException{
        Objects.requireNonNull(symbols,"Not a Valid Array of Symbols");
        return new SymbolSequence(Arrays.asList(symbols));
    }
    
    /**
     * String representation of the SymbolSequence
     * @return String representation of the SymbolSequence
     */
    @Override
    public String toString(){
        return production.toString();
    }

    /**
     * A method to test whether or not all the symbols in the production can be matched with the input
     * @param input list of token to be matched
     * @return the first possible match with the input or Failure if no match is found
     */
    public ParseState match(List<Token> input) throws NullPointerException{
        Objects.requireNonNull(input,"Input is null");

        List<Token> remainder = new LinkedList<>(input); //defensive copy
        ParseState tempParseState;
        InternalNode.Builder builder = new InternalNode.Builder();

        for(Symbol symbol : production){
            tempParseState = symbol.parse(remainder);
            if (tempParseState.isSuccess()) {
                builder.addChild(tempParseState.getNode());
                remainder = tempParseState.getRemainder();
            }
            else {
                return ParseState.FAILURE;
            }
        }
        return ParseState.build(true, remainder, builder.build());
    }

}
