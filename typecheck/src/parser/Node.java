package parser;

import java.util.List;
import java.util.Optional;

/**
 * Gives the user who implements this interface the ability to convert the object to a list
 * @author Adam Stewart(axs1477)
 * @author Phan Trinh Ha(pxt177)
 */
public interface Node {

    /**
     * Returns a list representation of the object
     * @return the list representation of the subtree rooted at the given node
     */
    List<Token> toList();

    /**
     * Get all of a node's children
     * @return List of children nodes stored by this
     */
    List<Node> getChildren();

    /**
     * Check if the node is either a leaf or have at least one child (a.k.a isFruitful)
     * @return true if this is fruitful check
     */
    boolean isFruitful();

    /**
     * Check if the node is a leaf corresponding to an operator
     * @return check
     */
    boolean isOperator();

    /**
     * Check if the first child of the node is an operator
     * @return check
     */
    boolean isOperatorLed();

    /**
     * A method to check if this has a single leaf child
     * @return true if the node has a single child which is a leaf
     */
    boolean hasSingleLeafChild();

    /**
     * Retrieves the first child of the node
     * @return optional of the first child of the node
     */
    Optional<Node> firstChild();
}
