package com.jimboweb.heirholzersalgorithm;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;


public class HeirholzersAlgorithm {

    private Inputter inputter;
    private Outputter outputter;

    public HeirholzersAlgorithm(Inputter inputter, Outputter outputter) {
        this.inputter = inputter;
        this.outputter = outputter;
    }

    public static void main(String[] args) {
        Inputter inputter = new ConsoleInput();
        Outputter outputter = new ConsoleOutput();

        new HeirholzersAlgorithm(inputter, outputter).run();
    }

    public void run() {
        ArrayList<ArrayList<Integer>> input = inputter.getInput();

        Graph graph = Graph.buildFromInputs(input);

        if(graph.isGraphEven(graph.size())){

            Path path = findPath(graph, new Path(graph.size()));

            String output = "1\n";
            Queue<Integer> pathQueue = path.getQueue();

            while(pathQueue.size()>1){
                int outputNode = pathQueue.poll() + 1;
                output += outputNode + " ";
            }
            outputter.output(output);
        } else {
            outputter.output("0");
        }
    }

    /**
     * finds the Eulerian path
     * @param graph contains ArrayList of Nodes
     * @param path previous path if there is one
     * @return the path of Integer vertices
     */
    public static Path findPath(Graph graph, Path path){
        Integer currentVertex = findFirstVertex(graph, path);

        if(currentVertex==null){
            return path;
        }

        Path newPath = makeNewPath(graph, currentVertex);

        if(path.isEmpty()){
            return findPath(graph, newPath);
        } else if(newPath.size()>1){
            Path withNewPath = addNewPath(path, newPath);
            return findPath(graph, withNewPath);
        } else {
            return findPath(graph, path);
        }
    }


    /**
     * find the first vertex in the new path
     * @param graph graph of what's left
     * @param path
     * @return endpoints of semi-Eulerian graph or open node of Eulerian graph
     */
    public static Integer findFirstVertex(Graph graph, Path path){
        ArrayList<Integer> oddVertices = graph.oddVertices(graph.size());

        if (oddVertices.size()==2){
            return firstVertexIfSemiEulerian(path, oddVertices);
        } else {
            return firstVertexIfEulerian(graph,path);
        }
    }

    /**
     * first vertex if path is Eulerian
     * @param graph what's left of the graph
     * @param path the previous path
     * @return
     */
    private static Integer firstVertexIfEulerian(Graph graph, Path path){
        Iterator<Node> graphIterator = graph.iterator();
        while (graphIterator.hasNext()){
            Node n = graphIterator.next();
            if(n.hasAdjacent()){
                if(path.isEmpty()||path.doesContain(n.getVertex())){
                    return n.getVertex();
                }
            }
        }
        return null;
    }

    /**
     * finds the first vertex from endpoints of semiEulerianGrapph
     * @param path previous path
     * @param endPoints end points of semi Eulerian graph
     * @return
     */
    private static Integer firstVertexIfSemiEulerian(Path path, ArrayList<Integer> endPoints) {
        Integer firstEndpoint = endPoints.get(0);
        Integer secondEndpoint = endPoints.get(1);

        if(path.isEmpty() || path.doesContain(firstEndpoint)){
            return firstEndpoint;
        } else if(path.doesContain(secondEndpoint)){
            return secondEndpoint;
        } else {
            return null;
        }
    }

    /**
     * finds the next new path
     * @param graph what's left of the graph
     * @param path  the previous path
     * @param currentVertex the vertex to start on
     * @return the new path from a vertex of the old one
     */
    private static Path makeNewPath(Graph graph, Integer currentVertex){
        // TODO: 2/16/18
        Path newPath = new Path(graph.size());

        while(currentVertex!=null){
            Integer currentVertexNum = currentVertex;
            newPath.add(currentVertexNum);
            Node currentNode = graph.getNode(currentVertexNum);

            while(currentNode.hasSelfLoops()){
                newPath.add(currentVertexNum);
                graph.removeSelfLoop(currentVertexNum);
            }

            if(currentNode.hasAdjacent()){
                Integer nextVertex = currentNode.popFirstAdjacent();
                graph.getNode(nextVertex).removeIncomingVertex();
                currentVertex=nextVertex;
            } else {
                currentVertex = null;
            }
        }
        return newPath;

    }

    /**
     * connects the previous path to the new one
     * @param path previous path
     * @param newPath path to add
     * @return the previous path joined to new path
     */
    private static Path addNewPath(Path path, Path newPath) {
        Path adjustedPath = new Path(path.getGraphSize());

        boolean newPathNotAdded = true;
        Integer start = newPath.getStart();

        for(Integer vertex:path.getQueue()){
            adjustedPath.add(vertex);
            if(newPathNotAdded && vertex.equals(start)){
                for(int newVertex:newPath.getQueue()){
                    adjustedPath.add(newVertex);
                }
                newPathNotAdded = false;
            }
        }

        return adjustedPath;
    }

}

