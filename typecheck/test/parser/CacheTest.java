package parser;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.is;


public class CacheTest {

    private Cache<BigInteger, String> cache;

    @Before
    public void setupCache(){
        cache = new Cache<>();
    }

    @After
    public void tearDown(){
        cache = null;
    }

    @Test(expected = NullPointerException.class)
    public void getNullTest(){
        cache.get(null, null);
        Assert.fail();
    }

    @Test
    public void getTest(){
        BigInteger key = new BigInteger("40");
        String expected = "40";

        String actual = cache.get(key, BigInteger::toString);

        Assert.assertThat(actual, is(expected));
    }

    @Test
    public void getMemoryTest(){
        BigInteger key = new BigInteger("42");
        String expected = cache.get(key, BigInteger::toString);

        String actual = cache.get(key, bigInteger -> bigInteger.toString((int)(Math.random()*100)));

        Assert.assertThat(actual, is(expected));
    }
}
