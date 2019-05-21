package parser;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static parser.TerminalSymbol.*;

public class TokenTest{
  @Test
  public void testIsVariable(){
    Assert.assertTrue(Variable.build("a").isVariable());
  }

  @Test
  public void testIsNotVariable(){
    Assert.assertFalse(Connector.build(TerminalSymbol.MINUS).isVariable());
  }
}
