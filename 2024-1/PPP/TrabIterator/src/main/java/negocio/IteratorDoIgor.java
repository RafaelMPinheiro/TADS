package negocio;

public interface IteratorDoIgor<T> {
    public boolean hasNext();

    public T next();

    public void add(T menuItem);

    public void remove(int itemIndex);
}
