package typecheck;

import parser.Node;
import parser.Token;
import parser.Variable;

import java.util.*;

/**
 * Synthesizer used for checking a type of an equation, given that the tree has been parsed and simplified
 * @author Phan Trinh Ha
 * @author Jessica Kwok
 */
final class Synthesizer {

    private Rules rules;    // rules for equation conversion
    private Map<Variable, Type> varTypeAssignments; // variable assignment to a type

    private Synthesizer(Rules rules, Map<Variable, Type> variableType){
        this.rules = rules;
        this.varTypeAssignments = variableType;
    }

    /**
     * Create Rules object from the given rule map
     * @param rulesMap conversion rule
     * @return Rules object of given rules
     * @throws NullPointerException if the given rules map was null
     */
    static Rules createRules(Map<Equation, Type> rulesMap){
        Objects.requireNonNull(rulesMap);
        Rules rules = new Rules();
        for(Map.Entry<Equation, Type> entry : rulesMap.entrySet()){
            rules.addRule(entry.getKey(), entry.getValue());
        }
        return rules;
    }

    /**
     * Assign new variables to the Synthesizer
     * @param token Token representation of the variable
     * @param type string representation of the type
     */
    void assignVar(Token token, String type){
        Objects.requireNonNull(token);
        Objects.requireNonNull(type);
        if(token.isVariable()){
        	varTypeAssignments.put((Variable)token, Type.build(type));
        }
    }

    /**
     * Get the variable to type assignments of the Synthesizer
     * @return HashMap of variable to Type assignment
     */
    Map<Variable, Type> getVarTypeAssignments() {
    	return new HashMap<>(this.varTypeAssignments);
    }

    /**
     * Builder for Synthesizer object
     * @param rulesMap equation to type assignments
     * @param variableType variable to type assignment
     * @return Synthesizer with given rules and variable assignments
     * @throws NullPointerException if any of the input is null
     */
     static Synthesizer build(Map<Equation, Type> rulesMap, Map<Variable, Type> variableType){
        Objects.requireNonNull(rulesMap);
        Objects.requireNonNull(variableType);

        Rules rules = createRules(rulesMap);
        variableType.values().forEach(type -> {
                rules.addRule(Equation.build(Arrays.asList(type)), type);
        });

        return new Synthesizer(rules, variableType);
    }

    /**
     * Find the type of the parse tree equation from the given rule in the synthesizer
     * @param root root Node of the parse tree
     * @return Type of the parse tree equation, null if not in the given rules
     * @throws NullPointerException if the given root node is null
     */
    Type typeOf(Node root){
        Objects.requireNonNull(root);
        List<EquationMember> memberList = new LinkedList<>();
        List<Node> children = root.getChildren();

        for(Node child : children){ 
            memberList = simplifyMemberList(memberList);

            //verify that there's no child, and this is a single token leaf node
            if(child.getChildren() == null){
                //assert child.toList().size() == 1 : "Tree should be simplified";
                Token token = child.toList().get(0);
                addToMemberList(memberList, token);
            }
            else{
                memberList.add(typeOf(child));
            }

        }
        Equation equation = Equation.build(memberList);
        return rules.infer(equation);
    }

    /**
     * Add a token to a list of member, added if the token is a Connector or a Variable
     * @param memberList list of found equation members in the parse tree
     * @param token Token to be added
     */
    private void addToMemberList(List<EquationMember> memberList, Token token) {
        if(token.isOperator()){
            memberList.add(Operator.build(token));
        }
        else{
            if(token.isVariable()){
                if(varTypeAssignments.containsKey(token)){
                    memberList.add(varTypeAssignments.get(token));
                }
            }
        }
    }

    /**
     * Simplify the memberlist to a single type if its equation format exists in the created Rule of the Synthesizer
     * @param memberList list of found equation member in the parse tree
     * @return List with one type inside if simplified, else return the old list of members
     */
    private List<EquationMember> simplifyMemberList(List<EquationMember> memberList) {
        List<EquationMember> tempMemberList = new LinkedList<>(memberList);
        Equation subEquation = Equation.build(tempMemberList);
        if(rules.infer(subEquation) != null){
            List<EquationMember> newMemberList = new LinkedList<>();
            newMemberList.add(rules.infer(subEquation));
            tempMemberList = newMemberList;
        }
        return tempMemberList;
    }


    public class TestHook{
        public List<EquationMember> simplifyMemberList(List<EquationMember> memberList){
            return Synthesizer.this.simplifyMemberList(memberList);
        }

        public Rules getRules(){
            return Synthesizer.this.rules;
        }

        public void addToMemberList(List<EquationMember> memberList, Token token){
            Synthesizer.this.addToMemberList(memberList, token);
        }
    }
}
