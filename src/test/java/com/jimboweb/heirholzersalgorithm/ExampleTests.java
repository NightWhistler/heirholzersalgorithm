package com.jimboweb.heirholzersalgorithm;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExampleTests extends TestCase {

    private static List<String> testForInput( Integer[][] inputInts ) {
       List<List<Integer>> input = new ArrayList<>();
       for ( Integer[] items: inputInts ) {
           input.add( Arrays.asList(items) );
       }

       return testForInput(input);
    }

    private static List<String> testForInput( List<List<Integer>> input ) {
       Inputter inputter = new DummyInputter(input);
        AccumulatingOutputter outputter = new AccumulatingOutputter();
       HeirholzersAlgorithm algorithm = new HeirholzersAlgorithm(inputter, outputter);
       algorithm.run();

       return outputter.getItems();
    }

    public void testExampleFromJimsEmail() {

        Integer [][] inputInts = {
            {3,4},
            {2,3},
            {2,2},
            {1,2},
            {3,1}
        };

        List<String> result = testForInput(inputInts);

       assertEquals( 2, result.size() );
       assertEquals( "1", result.get(0) );
       assertEquals( "1 2 2 3 ", result.get(1) );

    }

    public void testSquare() {

        Integer [][] inputInts = {
            {4,8},
            {1,2},
            {2,3},
            {3,4},
            {4,1},
            {1,1},
            {2,2},
            {3,3},
            {4,4},
        };

        List<String> result = testForInput(inputInts);

       assertEquals( 2, result.size() );
       assertEquals( "1", result.get(0) );
       assertEquals( "1 1 2 2 3 3 4 4 ", result.get(1) );
    }

     public void testSquareCross() {

        Integer [][] inputInts = {
            {4,10},
            {1,2},
            {2,3},
            {3,4},
            {4,1},
            {1,1},
            {2,2},
            {3,3},
            {4,4},
            {1,3},
            {2,4},
        };

        List<String> result = testForInput(inputInts);

       assertEquals( 1, result.size() );
       assertEquals( "0", result.get(0) );
    }

    public void testG4GExample() {
        //https://www.geeksforgeeks.org/hierholzers-algorithm-directed-graph/
        Integer [][] inputs = {
                {7, 10},
                {1, 2},
                {2, 3},
                {3, 4},
                {4, 5},
                {5, 6},
                {6, 1},
                {1, 7},
                {7, 5},
                {5, 3},
                {3, 1}
        };

        List<String> result = testForInput(inputs);

       assertEquals( 2, result.size() );
       assertEquals( "1", result.get(0) );
       assertEquals( "1 7 5 3 1 2 3 4 5 6 ", result.get(1) );
    }

    public void testG4GExample2() {
        //https://www.geeksforgeeks.org/hierholzers-algorithm-directed-graph/
        Integer [][] inputs = {
                {5, 6},
                {1, 2},
                {2, 3},
                {3, 1},
                {2, 4},
                {4, 5},
                {5, 2}
        };

        List<String> result = testForInput(inputs);

       assertEquals( 2, result.size() );
       assertEquals( "1", result.get(0) );
       assertEquals( "1 2 4 5 2 3 ", result.get(1) );
    }

    public void testOdd() {

        Integer [][] inputInts = {
            {4,5},
            {2,3},
            {2,2},
            {1,2},
            {3,1},
            {3,4}
        };

        List<String> result = testForInput(inputInts);

       assertEquals( 1, result.size() );
       assertEquals( "0", result.get(0) );

    }

    public void testTiny() {
         Integer [][] inputInts = {
            {3,2},
            {1,2},
            {2,1}
        };

        List<String> result = testForInput(inputInts);

       assertEquals( 2, result.size() );
       assertEquals( "1 2 ", result.get(1) );

    }

    public void testSemiEulerian() {
          Integer [][] inputInts = {
            {5,5},
            {1,2},
            {1,3},
            {2,3},
            {1,4},
            {4,5}
        };

        List<String> result = testForInput(inputInts);

    }

    public void testBig() {

        Outputter nullOutputter = new Outputter() {
            @Override
            public void output(String output) {
                //Send it to /dev/null
            }
        };

        List<List<Integer>> input = createFullyConnectedGraphOfSize(1000);
        Inputter inputter = new DummyInputter(input);
        HeirholzersAlgorithm algorithm = new HeirholzersAlgorithm(inputter, nullOutputter);
        algorithm.run();
    }

    private List<List<Integer>> createFullyConnectedGraphOfSize(int size) {
        List<List<Integer>> result = new ArrayList<>(size + 1);
        result.add( Arrays.asList( size + 1, size * size ) );
        for ( int n = 1; n <= size; n ++ ) {
            for ( int i = 1; i <= size; i++ ) {
                result.add( Arrays.asList( n, i ) );
            }
        }

        return result;
    }

}
