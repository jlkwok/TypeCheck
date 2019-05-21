package typecheck;

import java.util.*;

/**
 * Rules class provides three methods to help create and infer a type
 * Rules class contains the already given conversion rule and check if an equation can be inferred or not.
 * @author Phan Trinh Ha
 * @author Jessica Kwok
 */
final class Rules {
    private Map<Equation, Type> ruleMap;	/** mapping of conversion rules => Equations to result Types */

    /**
     * public constructor so the user can input a rulemap corresponding to their expression
     */
    public Rules(){
        ruleMap = new HashMap<>();
    }

    /**
     * Adds a new Equation mapping into the map if it has not already been added
     * @param equation the left side of a conversion rule made of types and operators
     * @param returnType the result type that this type of equation would go to
     */
    public void addRule(Equation equation, Type returnType){
        ruleMap.putIfAbsent(equation, returnType);
    }

    /**
     * Gets the type value of an equation key
     * @param equation the key 
     * @return the type mapped to the equation key
     */
    public Type infer(Equation equation){
        return ruleMap.get(equation);
    }

    /**
     * Gets the ruleMap
     * @return copy of ruleMap 
     */
    public Map<Equation, Type> getRuleMap(){
        return new HashMap<>(ruleMap);
    }
}
