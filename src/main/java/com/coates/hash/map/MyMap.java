package com.coates.hash.map;

/**
 * @ClassName MyMap
 * @Description TODO
 * @Author mc
 * @Date 2019/9/17 15:04
 * @Version 1.0
 **/
public interface MyMap<K, V> {

    public V put(K k, V v);

    public V get(K k);
}
