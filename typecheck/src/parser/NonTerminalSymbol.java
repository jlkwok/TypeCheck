package parser;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.EnumMap;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * An enum to represent the Non-Terminal Symbols EXPRESSION, EXPRESSION_TAIL, TERM, TERM_TAIL, UNARY, and FACTOR
 * @author Adam Stewart(axs1477)
 * @author Phan Trinh Ha(pxt177)
 */
public enum NonTerminalSymbol implements Symbol{
    EXPRESSION, EXPRESSION_TAIL, TERM, TERM_TAIL, UNARY, FACTOR;

    private static final Map<NonTerminalSymbol, Map<TerminalSymbol, SymbolSequence>> productions = new EnumMap<>(NonTerminalSymbol.class);
    private static final MultipleKeyMap<TerminalSymbol,SymbolSequence> expressionMap = new MultipleKeyMap<>();
    private static final MultipleKeyMap<TerminalSymbol,SymbolSequence> expressionTailMap = new MultipleKeyMap<>();
    private static final MultipleKeyMap<TerminalSymbol,SymbolSequence> termMap = new MultipleKeyMap<>();
    private static final MultipleKeyMap<TerminalSymbol,SymbolSequence> termTailMap= new MultipleKeyMap<>();
    private static final MultipleKeyMap<TerminalSymbol,SymbolSequence> unaryMap = new MultipleKeyMap<>();
    private static final MultipleKeyMap<TerminalSymbol,SymbolSequence> factorMap = new MultipleKeyMap<>();

    static class MultipleKeyMap<K,V> {
        private final HashMap<K,V> map = new HashMap<>();

        /**
         * Method for adding multiple keys to a map
         * @param keys List of Keys
         * @param value expected value to be mapped to
         */
        void putMultipleKeys(List<K> keys, V value){
            keys.forEach(key -> map.put(key, value));
        }

        /**
         * Wrapper method for putting key and value pair
         * @param key key
         * @param value value
         * @return Value that was put into
         */
        V put(K key, V value){
            return map.put(key, value);
        }

        /**
         * Return the built Map of type K, V
         * @return built map
         */
        Map<K, V> build(){
            return this.map;
        }
    }

    /**
     * Implementation by table driven design
     * Map<NonTerminalSymbol, Map<TerminalSymbol, SymbolSequence>> that maps the input NonTerminalSymbol to each Map
     * containing possible TerminalSymbol, SymbolSequence pairs
     */
    static{
        //A map for expressions
        expressionMap.putMultipleKeys(Arrays.asList(TerminalSymbol.OPEN,TerminalSymbol.MINUS,TerminalSymbol.VARIABLE),
                SymbolSequence.build(NonTerminalSymbol.TERM, NonTerminalSymbol.EXPRESSION_TAIL));

        //A map for expressionTails
        expressionTailMap.put(TerminalSymbol.PLUS, SymbolSequence.build(TerminalSymbol.PLUS, NonTerminalSymbol.TERM, NonTerminalSymbol.EXPRESSION_TAIL));
        expressionTailMap.put(TerminalSymbol.MINUS,  SymbolSequence.build(TerminalSymbol.MINUS, NonTerminalSymbol.TERM, NonTerminalSymbol.EXPRESSION_TAIL));
        expressionTailMap.putMultipleKeys(Arrays.asList(TerminalSymbol.CLOSE,null), SymbolSequence.EPSILON);

        //A map for term
        termMap.putMultipleKeys(Arrays.asList(TerminalSymbol.OPEN,TerminalSymbol.MINUS,TerminalSymbol.VARIABLE),
                SymbolSequence.build(NonTerminalSymbol.UNARY, NonTerminalSymbol.TERM_TAIL));

        //A map for Term Tail
        termTailMap.put(TerminalSymbol.TIMES, SymbolSequence.build(TerminalSymbol.TIMES, NonTerminalSymbol.UNARY, NonTerminalSymbol.TERM_TAIL));
        termTailMap.put(TerminalSymbol.DIVIDE,SymbolSequence.build(TerminalSymbol.DIVIDE, NonTerminalSymbol.UNARY, NonTerminalSymbol.TERM_TAIL));
        termTailMap.putMultipleKeys(Arrays.asList(TerminalSymbol.CLOSE,TerminalSymbol.PLUS,TerminalSymbol.MINUS,null),  SymbolSequence.EPSILON );

        //A map for unary
        unaryMap.put(TerminalSymbol.MINUS, SymbolSequence.build(TerminalSymbol.MINUS, NonTerminalSymbol.FACTOR));
        unaryMap.putMultipleKeys(Arrays.asList(TerminalSymbol.OPEN,TerminalSymbol.VARIABLE),SymbolSequence.build(NonTerminalSymbol.FACTOR));

        //A map for factor
        factorMap.put(TerminalSymbol.OPEN,  SymbolSequence.build(TerminalSymbol.OPEN, NonTerminalSymbol.EXPRESSION, TerminalSymbol.CLOSE));
        factorMap.put(TerminalSymbol.VARIABLE,SymbolSequence.build(TerminalSymbol.VARIABLE));


        //Creating the map for EXPRESSION create a uniform map method for cases like this
        productions.put(NonTerminalSymbol.EXPRESSION, expressionMap.build());
        productions.put(NonTerminalSymbol.EXPRESSION_TAIL,expressionTailMap.build());
        productions.put(NonTerminalSymbol.TERM,termMap.build());
        productions.put(NonTerminalSymbol.TERM_TAIL,termTailMap.build());
        productions.put(NonTerminalSymbol.UNARY,unaryMap.build());
        productions.put(NonTerminalSymbol.FACTOR,factorMap.build());
    }

    /**
     * Parser method for NonTerminalSymbol enum objects, by matching with the first possible
     * @param input the List of tokens you wish to parse
     * @return a new ParseState object with the inputted list
     */
    @Override
    public ParseState parse(List<Token> input) throws NullPointerException{
        Objects.requireNonNull(input, "Null input list was supplied into parse()");
        TerminalSymbol lookahead = input.isEmpty() ? null : input.get(0).getType();
        return Optional.of(productions.get(this))
                .filter(map -> map.containsKey(lookahead))
                .map(map -> map.get(lookahead).match(input))
                .filter(ParseState::isSuccess)
                .orElse(ParseState.FAILURE);
    }

    /**
     * Implementation of the toString method for NonTerminalSymbol
     * @return name of the current enums
     */
    @Override
    public String toString(){
        return this.name();
    }

    /**
     * Parse an input list based on
     * @param input the list to be parsed
     * @return Optional&lt;Node&gt; object of the symbol
     */
    static final Optional<Node> parseInput(List<Token> input) throws NullPointerException{
        Objects.requireNonNull(input, "Null input list was supplied into parseInput()");
        return Optional.of(EXPRESSION.parse(input))
                .filter(parseState -> parseState.isSuccess() && parseState.hasNoRemainder())
                .map(ParseState::getNode);
    }
}
