package com.coates.hash.map;


import java.util.Map;

/**
 * @ClassName Node
 * @Description TODO
 * @Author mc
 * @Date 2019/9/17 15:29
 * @Version 1.0
 **/
public class Node<K,V> implements Map.Entry<K,V> {
    private K key;
    private V value;
    private Node<K,V> nextNode;

    public Node(K key, V value, Node<K, V> nextNode) {
        this.key = key;
        this.value = value;
        this.nextNode = nextNode;
    }

    @Override
    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        this.value = value;
        return value;
    }

    public Node<K, V> getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node<K, V> nextNode) {
        this.nextNode = nextNode;
    }
}
