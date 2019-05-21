package parser;

import java.util.List;

/**
 * An interface used to give TerminalSymbol and NonTerminalSymbol the parse method
 * @author Adam Stewart(axs1477)
 * @author Phan Trinh Ha(pxt177)
 */
interface Symbol {

    /**
     * Parsing an input list of Token into a Node, with possibility of leaving a remainder
     * @param input list of Token to be parsed
     * @return ParseState object containing node and remainder
     */
    ParseState parse(List<Token> input);
}
