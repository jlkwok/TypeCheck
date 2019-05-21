package typecheck;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parser.*;

import java.util.*;

import static parser.TerminalSymbol.*;

public class SynthesizerTest {

    private Map<Equation, Type> ruleMap;
    private Map<Variable, Type> varAssignment;

    @Before
    public void setUp(){
        ruleMap = new HashMap<>();
        ruleMap.put(Equation.build(Arrays.asList(Type.build("A"), Operator.build(Connector.build(PLUS)), Type.build("B"))),
                Type.build("A"));
        ruleMap.put(Equation.build(Arrays.asList(Type.build("A"), Operator.build(Connector.build(PLUS)), Type.build("C"))),
                Type.build("B"));
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

    @After
    public void tearDown(){
        ruleMap = null;
        varAssignment = null;
    }
	
	@Test(expected = NullPointerException.class)
    public void buildNullTest(){
        Synthesizer.build(null, null);
        Assert.fail();
    }

    @Test
    public void buildNominalTest(){
        Synthesizer testSynthesizer = Synthesizer.build(ruleMap, varAssignment);
        Assert.assertEquals(testSynthesizer.getVarTypeAssignments(), varAssignment);
    }

    @Test(expected = NullPointerException.class)
    public void createRulesNull(){
        Synthesizer.createRules(null);
        Assert.fail();
    }

    @Test
    public void createRulesEmpty(){
        Rules emptyRule = Synthesizer.createRules(new HashMap<>());
        Assert.assertEquals(emptyRule.getRuleMap(), new HashMap<>());
    }

    @Test
    public void createRulesNominal(){
        Rules emptyRule = Synthesizer.createRules(ruleMap);
        Assert.assertEquals(emptyRule.getRuleMap(), ruleMap);
    }

    @Test(expected = NullPointerException.class)
    public void assignNullVar(){
        Synthesizer.build(null, null);
        Assert.fail();
    }

    @Test
    public void assignVarNominal(){
        Synthesizer testSynthesizer = Synthesizer.build(ruleMap, varAssignment);

        testSynthesizer.assignVar(Variable.build("d"), "D");
        Assert.assertTrue(testSynthesizer.getVarTypeAssignments().containsKey(Variable.build("d")));
        Assert.assertEquals(testSynthesizer.getVarTypeAssignments().get(Variable.build("d")), Type.build("D"));
    }

    @Test
    public void assignNonVar(){
        Synthesizer testSynthesizer = Synthesizer.build(ruleMap, varAssignment);

        testSynthesizer.assignVar(Connector.build(PLUS), "D");
        Assert.assertEquals(testSynthesizer.getVarTypeAssignments(), varAssignment);
    }

    @Test
    public void findTypeOfOneLayer(){
        Synthesizer testSynthesizer = Synthesizer.build(ruleMap, varAssignment);
        List<Token> tokenList = Parser.parseStringInput("a");
        Node root = Parser.parseInput(tokenList).orElseThrow(AssertionError::new);

        Type result = testSynthesizer.typeOf(root);
        Type expected = Type.build("A");
        Assert.assertEquals(expected, result);
    }

    @Test
    public void findTypeOfMultipleLayer(){
        Synthesizer testSynthesizer = Synthesizer.build(ruleMap, varAssignment);
        List<Token> tokenList = Parser.parseStringInput("a + b/c");
        Node root = Parser.parseInput(tokenList).orElseThrow(AssertionError::new);

        Type result = testSynthesizer.typeOf(root);
        Type expected = Type.build("A");
        Assert.assertEquals(expected, result);
    }

    @Test
    public void findTypeOfOpenClose(){
        Synthesizer testSynthesizer = Synthesizer.build(ruleMap, varAssignment);
        List<Token> tokenList = Parser.parseStringInput("(a + b)/c");
        Node root = Parser.parseInput(tokenList).orElseThrow(AssertionError::new);

        Type result = testSynthesizer.typeOf(root);
        Type expected = Type.build("A");
        Assert.assertEquals(expected, result);
    }

    @Test
    public void simplifyNominalTest(){
        Synthesizer testSynthesizer = Synthesizer.build(ruleMap, varAssignment);
        List<EquationMember> memberList = Arrays.asList(Type.build("A"), Operator.build(Connector.build(PLUS)), Type.build("A"));

        List<EquationMember> result = testSynthesizer.new TestHook().simplifyMemberList(memberList);
        List<EquationMember> expected = Arrays.asList(Type.build("A"));
        Assert.assertEquals(expected, result);
    }

    @Test
    public void simplifyNonSimplifyTest(){
        Synthesizer testSynthesizer = Synthesizer.build(ruleMap, varAssignment);
        List<EquationMember> memberList = Arrays.asList(Type.build("A"), Operator.build(Connector.build(PLUS)));

        List<EquationMember> result = testSynthesizer.new TestHook().simplifyMemberList(memberList);
        List<EquationMember> expected = Arrays.asList(Type.build("A"), Operator.build(Connector.build(PLUS)));
        Assert.assertEquals(expected, result);
    }

    @Test
    public void addToMemberListVariable(){
        Synthesizer testSynthesizer = Synthesizer.build(ruleMap, varAssignment);
        List<EquationMember> memberList = new ArrayList<>(Arrays.asList(Type.build("A"), Operator.build(Connector.build(PLUS))));

        testSynthesizer.new TestHook().addToMemberList(memberList, Variable.build("a"));
        List<EquationMember> expected = Arrays.asList(Type.build("A"), Operator.build(Connector.build(PLUS)), Type.build("A"));
        Assert.assertEquals(expected, memberList);
    }

    @Test
    public void addToMemberListUnknownVariable(){
        Synthesizer testSynthesizer = Synthesizer.build(ruleMap, varAssignment);
        List<EquationMember> memberList = new ArrayList<>(Arrays.asList(Type.build("A"), Operator.build(Connector.build(PLUS))));

        testSynthesizer.new TestHook().addToMemberList(memberList, Variable.build("d"));
        List<EquationMember> expected = Arrays.asList(Type.build("A"), Operator.build(Connector.build(PLUS)));
        Assert.assertEquals(expected, memberList);
    }

    @Test
    public void addToMemberListOperator(){
        Synthesizer testSynthesizer = Synthesizer.build(ruleMap, varAssignment);
        List<EquationMember> memberList = new ArrayList<>(Arrays.asList(Type.build("A")));

        testSynthesizer.new TestHook().addToMemberList(memberList, Connector.build(PLUS));
        List<EquationMember> expected = Arrays.asList(Type.build("A"), Operator.build(Connector.build(PLUS)));
        Assert.assertEquals(expected, memberList);
    }

    @Test
    public void addToMemberListNonConnector(){
        Synthesizer testSynthesizer = Synthesizer.build(ruleMap, varAssignment);
        List<EquationMember> memberList = new ArrayList<>(Arrays.asList(Type.build("A")));

        testSynthesizer.new TestHook().addToMemberList(memberList, Connector.build(CLOSE));
        List<EquationMember> expected = Arrays.asList(Type.build("A"));
        Assert.assertEquals(expected, memberList);
    }
}
