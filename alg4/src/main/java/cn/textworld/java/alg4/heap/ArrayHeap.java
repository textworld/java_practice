package cn.textworld.java.alg4.heap;

public class ArrayHeap<T extends Comparable<? super T>> implements Heap<T> {
    private T[] node;
    private int length;
    private int capacity;
    private double factor = 0.8;

    public ArrayHeap() {
        this(16);
    }

    public ArrayHeap(int capacity) {
        length = 0;
        this.capacity = capacity;
        node = (T[]) new Comparable[capacity+1];
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    public T deleteMax() {
        if(length == 0) {
            throw new RuntimeException("No elements in heap");
        }
        T ret = node[1];
        node[1] = node[length];
        length--;
        sink(1);
        return ret;
    }

    public void insert(Comparable comparable) {
        if(length >= capacity*factor){
            expand();
        }

        length++;
        node[length] = (T)comparable;
        swim(length);
    }
    private void expand(){
        capacity = capacity * 2;
        T[] newNodes = (T[]) new Comparable[capacity+1];
        for(int i = 0; i < length; i++){
            newNodes[i] = node[i];
        }
        node = newNodes;
    }
    private void swim(int i) {
        while(i > 1) {
            int j = i / 2;
            if(node[i].compareTo(node[j]) > 0){
                swap(i, j);
                i = j;
            }else{
                break;
            }
        }
    }

    private void sink(int i){
        while(2 * i <= length){
            int j = i*2;
            if(node[j].compareTo(node[j+1]) < 0) j++;
            if(node[i].compareTo(node[j]) > 0) break;
            swap(i, j);
            i = j;
        }
    }

    private void swap(int i, int j) {
        T temp = node[i];
        node[i] = node[j];
        node[j] = temp;
    }

    public static void main(String[] args){
        String str = "P R I O * R * * I * T * Y * * * Q U E * * * U * E";
        Heap<Character> heap = new ArrayHeap<>();
        for(int i = 0; i < str.length();i++){
            Character ch = str.charAt(i);
            if(ch.equals(' ')){
                continue;
            }
            if(ch.equals('*')){
                Character max = heap.deleteMax();
                System.out.print(max);
                System.out.print(" ");
            }else{
                heap.insert(ch);
            }
        }
    }
}
