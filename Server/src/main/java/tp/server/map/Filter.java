package tp.server.map;

/**
 * Generic interface used to filter elements from
 * collection based on condition
 * @param <T>
 */
public interface Filter<T> {
    boolean match(final T element);
}
