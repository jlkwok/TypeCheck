package parser;

import java.util.Objects;

/**
 * Abstract implementation of Token interface
 * @author Phan Trinh Ha (pxt177)
 * @author Adam Stewart (axs1477)
 */
public abstract class AbstractToken implements Token {

    /**
     * A method to check whether or not a TerminalSymbol type is equal to this objects TerminalSymbol
     * @param type type of TerminalSymbol to be matched to
     * @return whether the TerminalSymbol of this object matches the TerminalSymbol of the input
     */
    @Override
    public final boolean matches(TerminalSymbol type) {
        Objects.requireNonNull(type, "Null input, please enter a valid, nonnull TerminalSymbol");
        return type == getType();
    }

}
