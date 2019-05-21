package parser;

import java.util.EnumSet;
import java.util.Set;
import java.util.Objects;

import static parser.TerminalSymbol.*;

/**
 * A class to represent operators and connector methods
 *  @author Phan Trinh Ha (pxt177)
 *  @author Adam Stewart (axs1477)
 */
public final class Connector extends AbstractToken{
    private TerminalSymbol type;

    private static Set<TerminalSymbol> connectorSet = EnumSet.of(
            PLUS, MINUS, TIMES,
            DIVIDE, OPEN, CLOSE
    );

    private static Set<TerminalSymbol> operatorSet = EnumSet.of(
            PLUS, MINUS, TIMES, DIVIDE
    );

    /**
     * A private constructor used to instantiate connectors using the build method
     * @param type a valid TerminalSymbol
     */
    private Connector(TerminalSymbol type){
        this.type = type;
    }

    /**
     * Check for Connector type TerminalSymbol
     * @return confirmation of connector type
     */
    private static boolean isConnector(TerminalSymbol type){
        return connectorSet.contains(type);
    }

    /**
     * Check for Operator type TerminalSymbol
     * @return confirmation of operator type
     */
    public boolean isOperator(){
        return operatorSet.contains(this.type);
    }

    /**
     * A build method that calls the constructor and builds a Connector with the inputted parameters
     * should use cache to avoid creating Connectors of the same type (i.e. "+" and "+")
     * @param type TerminalSymbol representation of the Connector
     * @return Connector corresponding to each TerminalSymbol
     */
    public static final Connector build(TerminalSymbol type)
            throws NullPointerException, IllegalArgumentException{
        Objects.requireNonNull(type, "Inputted terminal symbol is null, please enter a nonnull TerminalSymbol");
        if(!isConnector(type))//move is connector into this class
            throw new IllegalArgumentException("Incorrect TerminalSymbol input");
        return new Connector(type);
    }

    /**
     * A toString method return string representations of TerminalSymbols
     * @return the string representation for the TerminalSymbol
     * @throws IllegalStateException If the type of connector is illegal
     */
    @Override
    public String toString() throws IllegalStateException{
        if(isConnector(type))
            return this.type.toString();
        else {
            throw new IllegalStateException("Illegal type of connector");
        }
    }

    /**
     * A method to get the type of the connector
     * @return the TerminalSymbol type of this
     */
    @Override
    public TerminalSymbol getType() {
        return type;
    }

}
