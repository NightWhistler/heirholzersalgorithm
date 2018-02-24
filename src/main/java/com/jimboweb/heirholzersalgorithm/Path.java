package com.jimboweb.heirholzersalgorithm;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * ArrayList of vertex numbers, will be final return of findPath method
 */
class Path {

    private Queue<Integer> queue;
    private boolean[] doesContain;

    public Path(int size) {
        doesContain = new boolean[size];
        queue = new LinkedList<>();
    }


    public int size(){
        return queue.size();
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public Queue<Integer> getQueue() {
        return queue;
    }


    public boolean add(int val){
        boolean rtrn = queue.add(val);
        if(val > doesContain.length){
            throw new IndexOutOfBoundsException("contains array is not big enough for " + val);
        }
        if(rtrn){
            doesContain[val] = true;
        }
        return rtrn;
    }


    public boolean doesContain(int val){
        return doesContain[val];
    }

    /**
     *
     * @return first vertex or Optional.empty if path is empty
     */
    public Integer getStart(){
        if(queue.isEmpty()){
            return null;
        } else {
            return queue.poll();
        }
    }

    public int getGraphSize(){
        return doesContain.length;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for ( Integer item: queue ) {
            buffer.append(item);
            buffer.append(" ");
        }
        return buffer.toString();
    }
}

