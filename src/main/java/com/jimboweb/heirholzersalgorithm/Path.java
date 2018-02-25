package com.jimboweb.heirholzersalgorithm;

import java.util.*;

/**
 * ArrayList of vertex numbers, will be final return of findPath method
 */
class Path {

    private List<Integer> queue;

    public Path(int pathSize) {
        queue = new ArrayList<>(pathSize);
    }

    public int size(){
        return queue.size();
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public List<Integer> getQueue() {
        return queue;
    }


    public boolean add(int val){
        return queue.add(val);
    }

    public boolean doesContain(int val){
        return queue.contains(val);
    }

    /**
     *
     * @return first vertex or Optional.empty if path is empty
     */
    public Integer getStart(){
        if(queue.isEmpty()){
            return null;
        } else {
            return queue.get(0);
        }
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

