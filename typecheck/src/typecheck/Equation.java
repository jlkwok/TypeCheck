package typecheck;

import java.util.* ;

/**
 * the Equation class represents the left side of a conversion rule
 * @author Phan Trinh Ha
 * @author Jessica Kwok
 */
public final class Equation {
    private List<EquationMember> equationMembers;	/** a list of types or operators (EquationMembers) that make up a single Equation */

    private Equation(List<EquationMember> equationMembers){
        this.equationMembers = equationMembers;
    }
    
    /**
     * A required method to build a new Equation using the private constructor
     * @param equationMembers list of types and operators that make up a single Equation
     * @return Copy of the newly created Equation
     * @throws NullPointerException input list of equationMembers is Null
     */
    public static Equation build(List<EquationMember> equationMembers){
        Objects.requireNonNull(equationMembers);
        return new Equation(Collections.unmodifiableList(equationMembers)); 
    }
    
    /**
     * Gets the list of equationMembers
     * @return the list of types and operators (EquationMembers) that were used to make the Equation
     */
    List<EquationMember> getEquationMembers() {
    	return equationMembers;
    }

    @Override
    public int hashCode(){
        return equationMembers.hashCode();
    }

    /**
     * Compare between two Equations, if they have the same members in order
     * @param o Object to be compared
     * @return true if all members are equal and in order
     */
    @Override
    public boolean equals(Object o){
        return o instanceof Equation && ((Equation) o).getEquationMembers().equals(equationMembers);
    }
 
}
