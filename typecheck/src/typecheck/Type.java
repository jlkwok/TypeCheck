package typecheck;

import parser.Cache;

import java.util.*;

/**
 * Type class is represents a Type of a Variable token, and a component of an equation
 * that can be used for inferring types from an equation/parse tree
 * @author Phan Trinh Ha
 * @author Jessica Kwok
 **/
public final class Type implements EquationMember{
    private String representation; /** string representation of the Type, i.e. "integer", "double", "A", "B" etc. */
    private static Cache<String, Type> typeCache = new Cache<>(); /** used to avoid duplicate type creations */

    private Type (String representation) throws NullPointerException {
        Objects.requireNonNull(representation, "Null representation");
        this.representation = representation;
    }

    /**
     * A required method to build a new Type, this must also check through the cache
     * and it does not return a new Type with a different address in memory
     * @param representation string representation of type
     * @return The newly created or fetched type
     * @throws NullPointerException input String representation is Null
     */
    public static Type build(String representation){
        Objects.requireNonNull(representation);
        return typeCache.get(representation, Type::new);
    }

    /**
     * Gets the string representation
     * @return string representation of the Type, i.e. "integer", "double", "A", "B" etc. 
     */
    @Override
    public final String getRepresentation(){ 
        return representation;
    }

    /**
     * Check if this member is an operator
     * @return true if is operator
     */
    @Override
    public boolean isOperator() {
        return false;
    }

    @Override
    public int hashCode(){
        return representation.hashCode();
    }

    /**
     * Compare between this Type and a given Object
     * @param o object to be compared
     * @return true if object is a Type and have the same representation
     */
    @Override
    public boolean equals(Object o){
        return o instanceof Type && ((Type)o).getRepresentation().equals(this.getRepresentation()); 
    }


}
