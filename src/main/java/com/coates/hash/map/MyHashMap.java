package com.coates.hash.map;

import java.io.Serializable;

/**
 * @ClassName MyHashMap
 * @Description TODO
 * @Author mc
 * @Date 2019/9/17 15:04
 * @Version 1.0
 **/
public class MyHashMap<K, V> implements MyMap<K, V>, Serializable {
    private static final long serialVersionUID = 843946861471503544L;
    Node<K, V>[] table = null;
    /**
     * 定义容器的默认大小
     */
    static int DEFAULT_INITIAL_CAPACITY = 16; // 而在源码中他是用 1 << 4 来表示
    /**
     * The load factor used when none specified in constructor.
     * HashMap默认负载因子，负载因子越小，Hash冲突的几率越低。官方给出的是0.75 当然也是大量数据中得出
     */
    static float DefAULT_LOAD_FACTOR = 0.75f;

    /**
     * 记录当前容器实际大小值
     */
    static int size;

    public V put(K key, V value) {
        //判断容器是否为空
        if (table == null) {
            table = new Node[DEFAULT_INITIAL_CAPACITY];
        }
        //如果size大于阀值则扩容处理
        if (size > DEFAULT_INITIAL_CAPACITY * DefAULT_LOAD_FACTOR) {
            resize();
        }
        int index = getIndex(key, DEFAULT_INITIAL_CAPACITY);
        Node<K, V> node = table[index];
        if (node == null) {
            table[index] = new Node<>(key, value, null);
            size++;
            return table[index].getValue();
        } else {
            Node<K, V> newNode = node;
            //循环遍历每个节点看看是否存在相同的key
            while (newNode != null) {
                //这里要用equals 和 == 因为key有可能是基本数据类型，也有可能是引用类型
                if (key.equals(newNode.getKey()) || key == newNode.getKey()) {
                    newNode.setValue(value);
                    size++;
                    return value;
                }
                newNode = node.getNextNode();
            }
            table[index] = new Node<>(key, value, table[index]);
            size++;
            return table[index].getValue();
        }
    }

    public V get(K key){
        int index = getIndex(key, DEFAULT_INITIAL_CAPACITY);
        Node<K, V> node = table [index];
        if (key.equals(node.getKey())||key==node.getKey()) {
            return node.getValue();
        }else {
            Node<K,V>  nextNode = node.getNextNode();
            while (nextNode != null) {
                if (key.equals(nextNode.getKey())||key== nextNode.getKey()) {
                    return nextNode.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 获取index
     *
     * @param key
     * @param length
     * @return
     */
    public int getIndex(K key, int length) {
        int hashcode = key.hashCode();
        return hashcode % length;
    }

    /**
     * 对size进行扩容
     */
    private void resize() {
        //1.创建新的table长度扩展为以前的两倍
        int newLength = DEFAULT_INITIAL_CAPACITY * 2;
        //2.将以前table中的取出，并重新计算index存入
        Node<K, V>[] newTable = new Node[newLength];
        for (int i = 0; i < table.length; i++) {
            Node<K, V> oldTable = table[i];
            //将table[i]的位置赋值为空,
            while (oldTable != null) {
                table[i] = null;
                //计算新的index值
                K key = oldTable.getKey();
                int index = getIndex(key, newLength);

                //将以前的nextnode保存下来
                Node<K, V> nextNode = oldTable.getNextNode();
                //将newtable的值赋值在oldtable的nextnode上，如果以前是空，则nextnode也是空
                oldTable.setNextNode(newTable[index]);
                newTable[index] = oldTable;
                //将以前的nextcode赋值给oldtable以便继续遍历
                oldTable = nextNode;
            }
        }
        //3.将新的table赋值回老的table
        table = newTable;
        DEFAULT_INITIAL_CAPACITY = newLength;
        newLength = 0;
    }

    // 测试方法.打印所有的链表元素
    public void print() {
        for (int i = 0; i < table.length; i++) {
            Node<K, V> node = table[i];
            System.out.print("下标位置[" + i + "]");
            while (node != null) {
                System.out.print("[ key:" + node.getKey() + ",value:" + node.getValue() + "]");
                node = node.getNextNode();
            }
            System.out.println();
        }

    }
    public static void main(String[] args) {
        MyHashMap<String, String> extHashMap = new MyHashMap<String, String>();
        extHashMap.put("1号", "1号");// 0
        extHashMap.put("2号", "1号");// 1
        extHashMap.put("3号", "1号");// 2
        extHashMap.put("4号", "1号");// 3
        extHashMap.put("6号", "1号");// 4
        extHashMap.put("7号", "1号");
        extHashMap.put("14号", "1号");

        extHashMap.put("22号", "1号");
        extHashMap.put("26号", "1号");
        extHashMap.put("27号", "1号");
        extHashMap.put("28号", "1号");
        extHashMap.put("66号", "66");
        extHashMap.put("30号", "1号");
        System.out.println("扩容前数据....");
        extHashMap.print();
        System.out.println("扩容后数据....");
        extHashMap.put("31号", "1号");
        extHashMap.put("66号", "123466666");
        extHashMap.print();
        // 修改3号之后
        System.out.println(extHashMap.get("66号"));

    }

}
