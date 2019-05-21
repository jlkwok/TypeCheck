package parser;


import typecheck.Driver; 
import typecheck.Equation;
import typecheck.Operator;
import typecheck.Type;

import java.util.*;

import static parser.TerminalSymbol.*;

/**
 * Main GUI class for the Parser project
 * @author pxt177 (Phan Trinh Ha)
 * @author axs1477 (Adam Stewart)
 */
public final class Parser{
    private static Map<String, TerminalSymbol> connectorMap = new HashMap<>();

    static{
        List<TerminalSymbol> connectorList = Arrays.asList(
                PLUS, MINUS, TIMES,
                DIVIDE, OPEN, CLOSE
        );
        for(TerminalSymbol connector : connectorList){
            connectorMap.put(connector.toString(), connector);
        }
    }

    /**
     * Check if representation is in Variable type format
     * @param representation string input representation
     * @return variable check
     */
    static boolean isConnectorFormat(String representation){
        if(representation.length() == 1){
            return representation.matches("[()+\\-*/]");
        }
        else {
            return false;
        }
    }

    /**
     * Convert from string to token
     * @param representation string input inputrepresentation
     * @return corresponding token
     */
    static Token stringToToken(String representation){
        if(isConnectorFormat(representation)){
            return Connector.build(connectorMap.get(representation));
        }
        else{
            return Variable.build(representation);
        }
    }

    /**
     * Parse in string input to get the corresponding list of token
     * @param rawInput raw string input
     * @return list of token
     */
    public static List<Token> parseStringInput(String rawInput){
        String input = rawInput.replaceAll("[^()+\\-*/A-Za-z]" // regex that filters out characters other than variables
                                                , "");    // and connectors
        //regex to keep and separate variables and connectors from the input
        String[] tokenRepresentations = input.split("(?<=[()+\\-*/])|(?=[()+\\-*/])");
        List<Token> tokenList = new LinkedList<>();
        for(String tokenRepresentation : tokenRepresentations){
            tokenList.add(stringToToken(tokenRepresentation));
        }
        return tokenList;
    }

    /**
     * Print method for the representation of the parse tree
     * @param node current node
     * @param prefix prefix spacing and pointer to the current node
     * @param isTail is the tail element of a level
     */
    private static void printTreeBody(Node node, String prefix, boolean isTail) {
        Objects.requireNonNull(node, "Null node input");
        Objects.requireNonNull(prefix, "prefix input is null");
        System.out.println(prefix + getBranchRepresentation(isTail, true) + node.toString());
        if (!Objects.isNull(node.getChildren())){
            LinkedList<Node> mutableChildrenList = new LinkedList<>(node.getChildren());
            if(mutableChildrenList.size() > 0){
                Node lastChild = mutableChildrenList.getLast();
                mutableChildrenList.removeLast();
                for(Node child : mutableChildrenList){
                    printTreeBody(child, prefix + getBranchRepresentation(isTail, false), false);
                }
                printTreeBody(lastChild, prefix + getBranchRepresentation(isTail, false), true);
            }
        }
    }

    /**
     * Get the print branch representation
     * @param isTail last of the child level
     * @param isNodePointer points to a node and stops
     * @return branch representation
     */
    private static String getBranchRepresentation(boolean isTail, boolean isNodePointer){
        if(isNodePointer){
            return isTail ? "└── " : "├── ";
        }
        else {
            return isTail ? "    " : "│   ";
        }
    }

    /**
     * Wrapper method to print the general representation of the tree
     * @param root Root node of the parse tree
     */
    static void printRepresentation(Node root){
        System.out.println("root");
        printTreeBody(root, "", true);
    }

    public static final Optional<Node> parseInput(List<Token> input){
        return NonTerminalSymbol.parseInput(input);
    }

    public static void main(String[] args){
        /**
         * Input type will required to be put in the comma "[Equation]"
         * Equation format: "variables, and connectors"
         */
        try {
            String rawInput = Arrays.stream(args).findFirst().orElseThrow(IllegalArgumentException::new);
            List<Token> tokenList = parseStringInput(rawInput);
            Node root = parseInput(tokenList).orElseThrow(IllegalArgumentException::new);
            System.out.println("The parsed raw input looks like so: " + tokenList.toString());
            printRepresentation(root);

        }
        catch(IllegalArgumentException e){
            System.out.println("Invalid argument");
        }
        catch(IllegalStateException e){
            System.out.println("Equation is in wrong format");
        }
    }
}
