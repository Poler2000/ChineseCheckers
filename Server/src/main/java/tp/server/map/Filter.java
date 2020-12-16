package tp.server.map;

public interface Filter<T> {
    boolean match(T element);
}
