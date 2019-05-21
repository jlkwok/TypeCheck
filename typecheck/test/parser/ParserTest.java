package parser;

import org.junit.*;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static parser.Parser.*;

public class ParserTest {

    private String rawInput;
    private String expectedTokenListRepresentation;
    private String connectorFormat;
    private String notConnectorFormat;
    private String rawSingleConnectorInput;
    private Token expectedToken;
    private Node root;

    @Before
    public void setUp(){
        rawInput = "a / (d+ b)  * c";
        expectedTokenListRepresentation = "[a, /, (, d, +, b, ), *, c]";
        connectorFormat = "+";
        notConnectorFormat = "42";
        rawSingleConnectorInput = "+";
        expectedToken = Connector.build(TerminalSymbol.PLUS);
        List<Token> tokenList = parseStringInput(rawInput);
        root = NonTerminalSymbol.parseInput(tokenList).orElseThrow(IllegalStateException::new);
    }

    @After
    public void tearDown(){
        rawInput = null;
        expectedTokenListRepresentation = null;
        connectorFormat = null;
        notConnectorFormat = null;
        rawSingleConnectorInput = null;
        expectedToken = null;
    }

    @Test
    public void isConnectorFormatTest() {
        Assert.assertTrue(isConnectorFormat(connectorFormat));
    }

    @Test
    public void isNotConnectorFormatTest(){
        Assert.assertFalse(isConnectorFormat(notConnectorFormat));
    }

    @Test
    public void stringToTokenTest(){
        Assert.assertThat(stringToToken(rawSingleConnectorInput).getType(), is(expectedToken.getType()));
    }

    @Test
    public void parseStringInputTest(){
        Assert.assertEquals(expectedTokenListRepresentation, parseStringInput(rawInput).toString());
    }

    @Test
    public void printRepresentationDemo(){
        System.out.println("Testing raw input: " + rawInput);
        System.out.println("Parsed tree will look as below");
        printRepresentation(root);
    }
}
