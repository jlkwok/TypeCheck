package parser;

import java.util.List;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Arrays;
import java.util.Optional;

/**
 * A class to represent LeafNodes in the Tree representation
 * leaf nodes are not cached
 * @author Adam Stewart(axs1477)
 * @author Phan Trinh Ha(pxt177)
 */
public final class LeafNode implements Node{
    private final Token token;
    private  List<Token> tokenList = new LinkedList<>();

    /**
     * Constructor to create a LeafNode and assigns the inputted token value
     * @param token The Token to be stored in the LeafNode
     */
    private LeafNode(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    /**
     * Constructs a new LeafNode object using the inputted Token
     * @param token token to be carried in LeafNode
     * @return a LeafNode object with the given Token
     * @throws NullPointerException  inserted token is null
     */
    public static LeafNode build(Token token)throws NullPointerException{
        Objects.requireNonNull(token, "Null token");
        return new LeafNode(token);
    }

    /**
     * Returns the stored Token in a new List&lt;Token&gt; as the first element in the list
     * @return the stored Token as a list
     */
    @Override
    public List<Token> toList() {
        if(tokenList.isEmpty())
            tokenList = Arrays.asList(this.token);
        else {
            //there is already a list
        }

        return tokenList;
    }

    @Override
    /**
     * Implements the Node class fruitful check
     * @return true as this is a leaf node
     */
    public boolean isFruitful(){
        return true;
    }

    /**
     * Check if the node is a leaf corresponding to an operator
     * @return check
     */
    @Override
    public boolean isOperator() {
        return this.token.isOperator();
    }

    /**
     * Check if the first child of the node is an operator
     * @return false as LeafNodes do not have children
     */
    @Override
    public boolean isOperatorLed() {
        return false;
    }

    /**
     * Implements the Node class children return method
     * @return null as a Leaf has no children
     */
    @Override
    public List<Node> getChildren(){
        return null;
    }
    /**
     * toString method for the LeafNode
     * @return the string representation of its single Token
     */
    @Override
    public String toString(){
        return token.toString();
    }

    /**
     *  States whether or not the leaf node has a single leaf child
     * @return false since a leaf node cannot have children
     */
    @Override
    public boolean hasSingleLeafChild(){
        return false;
    }

    /**
     *  an empty Optional since the the leaf node has no first child
     * @return an empty optional
     */
    @Override
    public Optional<Node> firstChild(){
        return Optional.empty();
    }
}
