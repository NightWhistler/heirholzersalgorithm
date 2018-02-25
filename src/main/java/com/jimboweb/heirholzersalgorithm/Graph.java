package com.jimboweb.heirholzersalgorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Graph  {

    private ArrayList<Node> nodes;
    private int edgeCount;

    public Graph(int size){
        nodes = new ArrayList<>(size);
    }

    public int size(){
        return nodes.size();
    }

    public void addSelfLoop(int n){
        getNode(n).addSelfLoop();
        edgeCount++;
    }

    public void removeSelfLoop(int n){
        getNode(n).removeSelfLoop();
        edgeCount--;
    }

    public void addNode(Node n){
        nodes.add(n);
    }

    public Node getNode(int i){
        return nodes.get(i);
    }

    public Iterator<Node> iterator() {
        return nodes.iterator();
    }

    public  ArrayList<Integer> oddVertices(int graphSize){
        ArrayList<Integer> rtrn = new ArrayList<>(graphSize);

        for ( Node n: nodes ) {
            if(!n.isEven()){
                rtrn.add(n.getVertex());
            }
        }
        return rtrn;
    }

    public boolean isGraphEven(int graphSize){
        ArrayList<Integer> oddVertices = oddVertices(graphSize);
        return oddVertices.isEmpty();
    }

    public void addEdge(int from, int to){
        getNode(from).addAdjacentVertex(to);
        getNode(to).addIncomingVertex(from);
        edgeCount++;
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    /**
     * creates basic graph from inputs
     * @param inputs ArrayList of Integers from input
     * @return
     */
    public static Graph buildFromInputs(List<List<Integer>> inputs) {

        int n = inputs.get(0).get(0);

        Graph g = new Graph(n);
        for(int i=0;i<n;i++){
            Node newNode = new Node(i);
            g.addNode(newNode);
        }

        for(int i=1;i<inputs.size();i++){
            int from = inputs.get(i).get(0)-1;
            int to = inputs.get(i).get(1)-1;
            if(from==to){
                g.addSelfLoop(from);
            }else {
                g.addEdge(from, to);
            }
        }
        return g;
    }
}



