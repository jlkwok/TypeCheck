package parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import  java.util.function.Function;

/**
 * Cache used for storing already set Token types
 * @param <T> Type of Token
 * @param <V> Type of Value
 * @author Phan Trinh Ha (pxt177)
 * @author Adam Stewart (axs1477)
 */
public final class Cache<T,V> {

    private Map<T, V> cache = new HashMap<>();

    /**
     * Getting the corresponding value in the cache
     * @param key input token
     * @param constructor constructor of the token
     * @throws NullPointerException Key input or constructor input is null
     * @return the object at the given key in the map
     */
    public final V get(T key, Function<? super T, ? extends V> constructor) throws NullPointerException{
        Objects.requireNonNull(key,"The inputted key is null, please enter a valid nonnull key");
        Objects.requireNonNull(constructor,"The inputted constructor is null, please enter a valid nonnull constructor");
        return cache.computeIfAbsent(key, constructor);
    }
}
