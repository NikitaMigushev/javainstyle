package ru.javainstyle.generic;

public interface ContainerLike<V, T extends ContainerLike<V, T>> {
    V get();
    void update(V val);
    void connectTo(T other);
}
