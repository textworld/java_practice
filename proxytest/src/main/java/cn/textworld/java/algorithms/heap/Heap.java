package cn.textworld.java.algorithms.heap;

public interface Heap<T> {
    T deleteMax();
    void insert(T t);
}
