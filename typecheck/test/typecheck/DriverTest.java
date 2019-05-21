package typecheck;

import org.junit.Assert;
import org.junit.Test;
import parser.*;

import java.util.*;
import java.util.logging.Logger;

import static parser.TerminalSymbol.*;

public class DriverTest {

    private static Logger logger = Logger.getLogger("Test log");

    private static List<Operator> operatorList = Arrays.asList(
            Operator.build(Connector.build(TIMES)),
            Operator.build(Connector.build(PLUS)),
            Operator.build(Connector.build(DIVIDE))
            );

    private static int BOUND_LIMIT = 100;
    private static int EQ_LIMIT = 5;
    private List<String> varList = Arrays.asList("a", "b", "c");
    private static Set<Type> types;
    private static Map<Equation, Type> ruleMap;
    private static Map<Variable, Type> varAssignment;

    static{
        types = new HashSet<>();
        types.add(Type.build("A"));
        types.add(Type.build("B"));
        types.add(Type.build("C"));

        ruleMap = new HashMap<>();
        ruleMap.put(Equation.build(Arrays.asList(Type.build("A"), Operator.build(Connector.build(PLUS)), Type.build("B"))),
                Type.build("A"));
        ruleMap.put(Equation.build(Arrays.asList(Type.build("A"), Operator.build(Connector.build(PLUS)), Type.build("C"))),
                Type.build("A"));
        ruleMap.put(Equation.build(Arrays.asList(Type.build("B"), Operator.build(Connector.build(DIVIDE)), Type.build("C"))),
                Type.build("A"));
        ruleMap.put(Equation.build(Arrays.asList(Type.build("A"), Operator.build(Connector.build(DIVIDE)), Type.build("C"))),
                Type.build("A"));
        ruleMap.put(Equation.build(Arrays.asList(Type.build("A"), Operator.build(Connector.build(PLUS)), Type.build("A"))),
                Type.build("A"));
        varAssignment = new HashMap<>();
        varAssignment.put(Variable.build("a"), Type.build("A"));
        varAssignment.put(Variable.build("b"), Type.build("B"));
        varAssignment.put(Variable.build("c"), Type.build("C"));
    }

    @Test(expected = AssertionError.class)
    public void constructorAssertionTest(){
        Driver.TestHook.constructDriver();
        Assert.fail();
    }

    @Test
    public void parseInputTest() {
         //generate random numbers
        Random random = new Random();
        int bound = random.nextInt(EQ_LIMIT);

        for(int j = 0; j < BOUND_LIMIT; j++) {
            StringBuilder builder = new StringBuilder();
            builder.append(varList.get(random.nextInt(varList.size())));
            for (int i = 0; i < bound; i++) {
                builder.append(operatorList.get(random.nextInt(operatorList.size())).getRepresentation());
                builder.append(varList.get(random.nextInt(varList.size())));
            }
            List<Token> tokenList = Parser.parseStringInput(builder.toString());
            Node root = Parser.parseInput(tokenList).orElseThrow(AssertionError::new);

            Type result;
            try{
                result = Driver.synthesize(ruleMap, varAssignment, root);
                Assert.assertTrue(types.contains(result));
                logger.info("Found a valid equation: " + builder.toString());
            }
            catch(IllegalArgumentException e){
                continue;
            }
            catch(Exception e){
                Assert.fail("Unexpected failure occurred");
            }
        }
    }
}