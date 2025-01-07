package ru.javainstyle.generic;

public class Container extends UnionFindNode<Double, ContainerSummary> {
    public Container() {
        super(ContainerSummary.ops);
    }
}
