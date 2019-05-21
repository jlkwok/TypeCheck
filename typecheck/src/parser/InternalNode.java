package parser;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class for internal nodes of connecting each leaf node
 * @author Phan Trinh Ha (pxt177)
 * @author Adam Stewart (axs1477)
 */
public final class InternalNode implements Node {

    /**
     * A class to represent builders for InternalNode
     * @author Phan Trinh Ha (pxt177)
     * @author Adam Stewart (axs1477)
     */
    public static class Builder{
        private List<Node> children = new LinkedList<>();

        /**
         * Adds children to the List&lt;Node&gt; of children
         * @param node children Node
         * @return addition confirmation
         */
        boolean addChild(Node node){
            boolean status = children.add(node);
            this.simplify();
            return status;
        }

        /**
         * Simplifies a children list of nodes
         * @return Builder with simplified children's list
         */
        Builder simplify(){
            return this.filterChildlessNodes()
                        .filterSingleInternalChild()
                        .filterOpLedNodes()
                        .filterSingleLeafParentNodes();
        }

        /**
         * A helper method to filter for operator led nodes
         * @return a Builder object with its children list filtered for operator led nodes
         */
        private Builder filterOpLedNodes(){
            Iterator<Node> childIter = this.children.iterator();
            List<Node> newChildren = new LinkedList<>();
            Node currentChild = null;
            Node prevChild;
            while(childIter.hasNext()){
                prevChild = currentChild;
                currentChild = childIter.next();
                if(currentChild.isOperatorLed() && prevChild != null && !prevChild.isOperator()){
                    newChildren.addAll(currentChild.getChildren());
                }
                else{
                    newChildren.add(currentChild);
                }
            }
            this.children = newChildren;
            return this;
        }

        /**
         * A helper method to filter for single internal children
         * @return a Builder object with the children of the children of the Node if there are any, else return the object calling
         */
        private Builder filterSingleInternalChild(){
            if(hasSingleInternalChild()) {
                // replace children with the child list, or itself if there is no first child
                this.children = getFirstChild().map(Node::getChildren).orElse(children);
            }
            else{
                //contains more than one child or a single leaf child
            }
            return this;
        }

        /**
         * A helper method to filter for childless nodes
         * @return a Builder object with the children nodes that are not childless
         */
        private Builder filterChildlessNodes(){
            this.children = this.children.stream()
                    .filter(Node::isFruitful)//filters the list of children for those that are fruitful
                    .collect(Collectors.toList());//Collects the fruitful nodes and makes a list of them
            return this;
        }

        /**
         * A helper method to filter for single leaf parent nodes
         * @return a Builder with the edited children list of the of the Builder that calls this method
         */
        private Builder filterSingleLeafParentNodes(){
            List<Node> tempChildren = new LinkedList<>();
            for(Node child : this.children){
                if(child.hasSingleLeafChild()){
                    tempChildren.add(child.firstChild().orElse(child));
                }
                else
                    tempChildren.add(child);
            }
            this.children = tempChildren;
            return this;
        }

        /**
         * Get the first child of the children list
         * @return first child in the list
         */
        private Optional<Node> getFirstChild(){
            return this.children.stream().findFirst();
        }

        /**
         * Check for single internal child node
         * @return true if this has a single child node which is also an instance of an internal node
         */
        private boolean hasSingleInternalChild(){
            return this.children.size() == 1 && getFirstChild().filter(InternalNode.class::isInstance)
                                                                .isPresent();
        }

        /**
         * Builds an Internal Node with simplified children
         * @return Internal Node with simplified children
         */
        public InternalNode build(){
            return InternalNode.build(this.children);
        }
    }

    private final List<Node> children;
    private String representation = "";
    private List<Token> storedList;

    /**
     * A private constructor for creating internal nodes
     * @param children a list of valid Nodes
     */
    private InternalNode(List<Node> children) {
        this.children = children;
        storedList = new ArrayList<>();
    }

    /**
     * A method to concatenate the children’s lists and calculate it only once.
     * @return the concatenation of the children’s lists.
     */
    @Override
    public List<Token> toList() {
        if (storedList.isEmpty()) {
            storedList = children.stream()
                    .map(Node::toList)//map each node in children to it's list
                    .flatMap(List::stream)//take each list and stream them
                    .collect(Collectors.toList());//collect each of these streams and convert them to a list
        } else {
            //No other action is required
        }
        return storedList;
    }

    /**
     * A method to build an InternalNode the input must be a List of nodes with a list size of 2
     * @param children expected children of InternalNode
     * @return an InternalNode object using the inputted list of children
     * @throws NullPointerException if the inputted list of children is null
     */
    public static InternalNode build(List<Node> children) throws NullPointerException {
        Objects.requireNonNull(children, "children list inputted is null, please enter a nonnull List<Node>");
        return new InternalNode(children);
    }

    /**
     * Getter method for the children list
     * @return a list of children of this
     */
    @Override
    public List<Node> getChildren() {
        return new ArrayList<>(this.children);
    }

    /**
     * Implements the fruitful check method
     * @return if the internal node any children
     */
    @Override
    public boolean isFruitful(){
        return this.children.size() > 0;
    }

    /**
     * Check if the node is a leaf corresponding to an operator
     * @return always false as Internal Nodes do not carry Tokens
     */
    @Override
    public boolean isOperator() {
        return false;
    }

    /**
     * Check if the first child of the node is an operator
     * @return true if the first child in the list is an operator
     */
    @Override
    public boolean isOperatorLed() {
        return this.firstChild().filter(Node::isOperator).isPresent();
    }

    /**
     * returns the string representation of the InternalNode with the specific format "[a,[+,[b,[/,c]]]]"
     * @return a string representation of the internal node
     */
    @Override
    public String toString(){
        if (representation.isEmpty()) {
            StringBuilder output = new StringBuilder();
            getBracketsRepresentation(this, output);
            representation = output.toString();
        }
        else{
            //representation is stored already
        }
        return representation;
    }

    /**
     * Recursive algorithm for pre-order traversal down the tree of Nodes
     * @param currentNode currentNode node being used
     * @param output StringBuilder object to be modified
     */
    private void getBracketsRepresentation(InternalNode currentNode, StringBuilder output){
        output.append("[")//begins by appending an open bracket
                .append(currentNode.getChildren().stream()//streams the children of the current node
                        .map(Node::toString)//maps each of those nodes to their string representations
                        .collect(Collectors.joining(",")))//it then joins these string representations
                .append("]");//finally append the close bracket
    }

    /**
     *
     * @return true if this internal node has a single leaf node as a child
     */
    public boolean hasSingleLeafChild(){
        return this.children.size() == 1 && firstChild().filter(LeafNode.class::isInstance).isPresent();
    }

    /**
     * Get the first child of the children list
     * @return first child in the list
     */
    public Optional<Node> firstChild(){
        return this.children.stream().findFirst();
    }
}
