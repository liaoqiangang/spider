package com.spider.queue;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * 队列  先进先出，后进后出
 * 存放需要访问的URL
 */
public class Queue{
    //使用链表实现队列
    private LinkedList queue = new LinkedList();

    /**
     * 入队列
     * @param o
     */
    public void enQueue(Object o){
        queue.add(o);
    }

    /**
     * 出队列
     */
    public Object deQueue(){
       return queue.removeFirst();
    }

    /**
     * 判断队列是否包含 o
     * @param o
     * @return
     */
    public boolean contains(Object o){
       return queue.contains(o);
    }

    /**
     * 判断队列是否为空
     * @return
     */
    public boolean isQueueEmpty(){
        return queue.isEmpty();
    }

    public boolean empty() {
        return queue.isEmpty();
    }

}
