package cn.textworld.java.alg4.heap;

public interface Heap<T> {
    T deleteMax();
    void insert(T t);
    int size();
    boolean isEmpty();
}

