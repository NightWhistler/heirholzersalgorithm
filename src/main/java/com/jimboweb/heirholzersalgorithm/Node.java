package com.jimboweb.heirholzersalgorithm;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A directed node
 */
public class Node {

    private final Integer vertex;
    private Deque<Integer> adjacentVertices;

    private int incomingVertices;
    private int selfLoops = 0;


    public Node(Integer vertex){
        this.vertex = vertex;
        this.adjacentVertices = new ArrayDeque<>();
        this.incomingVertices = 0;
    }

    public boolean hasSelfLoops(){
        return selfLoops>0;
    }

    public void addSelfLoop(){
        selfLoops++;
    }

    public void removeSelfLoop(){
        selfLoops--;
    }

    /**
     *
     * @return the vertex number
     */
    public Integer getVertex() {
        return vertex;
    }

    /**
     *
     * @return true if adjecent vertices, false if not
     */
    public boolean hasAdjacent() {
        return !adjacentVertices.isEmpty();
    }

    public void removeIncomingVertex(){
        incomingVertices--;
    }

    /**
     *
     * @return first adjacent vertex
     */
    public Integer popFirstAdjacent() {
        if(hasAdjacent()) {
            return adjacentVertices.pop();
        } else {
            return null;
        }
    }

    /**
     *
     * @param i index of adjacent vertex
     * @return vertex number of adjacent vertex
     */

    public void addAdjacentVertex(Integer i){
        adjacentVertices.push(i);
    }

    public void addIncomingVertex(Integer i){
        incomingVertices++;
    }

    /**
     *
     * @return true if in==out, false if not
     */
    public boolean isEven(){
        return incomingVertices==adjacentVertices.size();
    }
}
