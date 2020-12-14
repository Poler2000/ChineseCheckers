package tp.server;

public interface Filter<T> {
    boolean match(T element);
}
