package typecheck;

/**
 * The EquationMember interface provides two methods to help manage and assess Types and Operators
 * @author Phan Trinh Ha
 * @author Jessica Kwok
 */
public interface EquationMember {
	/**
	 * Get the representation of the Member
	 * @return String representation of the member
	 */
	String getRepresentation();

	/**
	 * Check if this member is an operator
	 * @return true if is operator
	 */
	boolean isOperator();
}
