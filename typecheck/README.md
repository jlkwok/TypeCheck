To compile the code simply open the src folder in your preferred IDE (works best on IntelliJ) and press compile

This code runs through command prompt arguments and takes inputs according to the comments
above the methods.

Do not instantiate variables directly and instead use the given build methods

Error Convention: "possible explanation"

# TODO:
  - Wrap high complexity boolean statements to methods
  - Make public classes final - DONE
  - findTypeOf -> typeOf - DONE
  - consider removing the set of types as we can create that from the rules/variables
  - Try for full coverage

## Considerations:
    - How do you work with the new type when implementing it
    - Any code repeat or more than 4 on complxity is a C
    - Make the changes in the comment from PA3
    - Add as much package private, private classes as possible
    - Add as many private methods as possible
    - Explain the lambdas, it might be confusing to people
## GUI
The input type of this Parser will be a string of "{equation}" comprised of string variables and connectors +,-,*,/,(,)

### Example
    Command input: ..\EECS_293_A2\out\production\EECS_293_A2>java parser.Parser "a / (d+ b)  * c"
    input: "a / (d+ b)  * c"
    The parsed raw input looks like so: [a, /, (, d, +, b, ), *, c]
    root
    └── [a,/,[(,[d,+,b],)],*,c]
        ├── a
        ├── /
        ├── [(,[d,+,b],)]
        │   ├── (
        │   ├── [d,+,b]
        │   │   ├── d
        │   │   ├── +
        │   │   └── b
        │   └── )
        ├── *
        └── c
## Parse Tree Simplification
    - Technique 1: Remove all internal Nodes with no children
    - Technique 2: Replace all internal nodes with one child to it's child
    - To implement in Node: (Done)
        - getChildren(): returns null if no child, or a copy of the children
        - isFruitful(): True if node has at least one child, or is an Leaf (false if is a childless internal)
    - To implement in InternalNode: (Done)
        - public static nested class Builder
            - private List<Node> children
            - public boolean addChild(Node node)
            - public Builder simplify():
                - Remove all childless nodes
                - If only one single internal node then replace it with it's children
            - public InternalNode build(): returns Node with simplified children list
    - Technique 3: Replace Internal Nodes with single leaf child with itself
    - Technique 4: Replace operator led nodes with its children
        - Use Builder to simplify the children's list after adding each child
## Simplified Productions
    - Changing into a Map<NonTerminalSymbol, Map<TerminalSymbol, SymbolSequence>>
    - Productions should be non-overlapping
## Builder class simplification
    - The simplified tree is not as simplified as the example as the last Internal Node
    which is still fruitful as having only one child that is a leaf node
### Fixes
     - Removed unsecessary override in AbstractToken
     - Fixed exception messages being redundant
     - Fixed fixed internalnode to not assume left and right node
     - Created missing toString methods for InternalNode and LeafNode
     - Fixing toList recomputing every time

     - Added more detail in error messages
     -Moved ConnectorSet to Connecter to reduce coupling
     -Removed unecessary import in tests as well as InternalNode
     -Fixed redundancy in pare and parseInput in NonTerminalSymbol
     -Shorted getList method in Variable to one line

     -Fixed redundant list creation in LeafNode
     -Made it so Builder.build returns children after simplifying
     -Chaged so SymbolSequence.EPSILON is not used with null input in all situations
     -Cleaned up map initialization in NonTerminalSymbol
     -Removed unneeded lists in NonTerminalSymbol
#FIX MASTER!!!!!!!
