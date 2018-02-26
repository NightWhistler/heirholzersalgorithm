package com.jimboweb.heirholzersalgorithm;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class HeirholzersAlgorithm {

    private Inputter inputter;
    private Outputter outputter;

    private static final Logger logger = LoggerFactory.getLogger(HeirholzersAlgorithm.class);

    public HeirholzersAlgorithm(Inputter inputter, Outputter outputter) {
        this.inputter = inputter;
        this.outputter = outputter;
    }

    public static void main(String[] args) {
        Inputter inputter = new ConsoleInput();
        AccumulatingOutputter outputter = new AccumulatingOutputter();

        new HeirholzersAlgorithm(inputter, outputter).run();

        //Print output at the end so it doesn't get stuck in the middle of log output
        outputter.printOutput(System.out);
    }

    public void run() {
        List<List<Integer>> input = inputter.getInput();

        if ( input.size() < 1 || input.get(0).size() != 2 ) {
            throw new IllegalArgumentException("First row malformed");
        }

        int nodeCount = input.get(0).get(0);
        int edgeCount = input.get(0).get(1);

        if ( edgeCount != input.size() -1 ) {
            throw new IllegalArgumentException("Actual edge count does not match stated edge count");
        }

        logger.debug("Got a graph with {} nodes and {} edges", nodeCount, edgeCount);

        Graph graph = Graph.buildFromInputs(input);

        if(graph.isGraphEven(graph.size())){
            logger.debug("Graph is even");

            Path path = findPath(graph, new Path(0));

            outputter.output( "1" );
            List<Integer> pathQueue = path.getQueue();
            logger.debug("Found a path of {} elements.", pathQueue.size() );

            StringBuffer buffer = new StringBuffer();

            while(pathQueue.size()>1){
                int outputNode = pathQueue.remove(0) + 1;
                buffer.append( outputNode + " " );
            }
            outputter.output(buffer.toString());
        } else {
            logger.debug("Graph was uneven, outputting 0");
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
        logger.debug("First vertex was {}", currentVertex);

        if(currentVertex==null){
            return path;
        }

        Path newPath = makeNewPath(graph, currentVertex);
        logger.debug("Created new path of size {}", newPath.size() );

        if(path.isEmpty()){
            logger.debug("Path was empty, recursing with newPath");
            return findPath(graph, newPath);
        } else if(newPath.size()>1){
            logger.debug("Path size was {}, appending paths");
            Path withNewPath = addNewPath(path, newPath);
            return findPath(graph, withNewPath);
        } else {
            logger.debug("Path size was {}, going into straight recursive loop.");
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
            logger.debug("Graph is semi-eulerian");
            return firstVertexIfSemiEulerian(path, oddVertices);
        } else {
            logger.debug("Graph is eulerian");
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

        logger.debug("Making a new path for a graph with edge-count {} and currentVertex {}", graph.getEdgeCount(), currentVertex );
        // TODO: 2/16/18
        Path newPath = new Path(graph.getEdgeCount());

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
        logger.debug("Adding path '{}' to path '{}'", path, newPath);

        Path adjustedPath = new Path(path.size() + newPath.size());

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

        logger.debug("Returning adjustedPath {}", adjustedPath);
        return adjustedPath;
    }

}

