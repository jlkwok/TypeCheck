package parser;

import java.util.List;

/**
 * A class to create and maintain the current state of a parsed object
 * @author Adam Stewart(axs1477)
 * @author Phan Trinh Ha(pxt177)
 */
final class ParseState {
    private final boolean success;
    private final Node node;
    private final List<Token> remainder;
    final static ParseState FAILURE = new ParseState(false,null,null);

    /**
     * Private constructor of ParseState object with the given parameters
     * @param success the boolean value to be stored in success
     * @param remainder the list of tokens to be stored
     * @param node the node that was parsed
     */
    private ParseState(boolean success, List<Token> remainder, Node node){
        this.node = node;
        this.remainder = remainder;
        this.success = success;
    }

    /**
     * A method to access the remainder
     * @return the remainder of the parse node
     */
    List<Token> getRemainder() {
        return remainder;
    }

    /**
     * whether or not the parsing of this node was a success or not
     * @return the current value of success
     */
    boolean isSuccess() {
        return success;
    }

    /**
     * Retrieves the node of the ParseState
     * @return the node saved by the parse state
     */
    Node getNode() {
        return node;
    }

    final boolean hasNoRemainder(){
        return remainder.isEmpty();
    }

    /**
     * A method to create a new ParseState object
     * @param success the passed through input of whether or not the parse was a success
     * @param remainder returns what is left in the List&lt;Token&gt; after it is parsed
     * @param node the node parsed out of the List&lt;Token&gt;
     * @return a new ParseState object with the given success,remainder, and node
     */
    static ParseState build(boolean success, List<Token> remainder, Node node){
        return new ParseState(success,remainder,node);
    }

}
