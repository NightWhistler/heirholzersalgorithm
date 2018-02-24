package com.jimboweb.heirholzersalgorithm;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccumulatingOutputter implements Outputter {
    private List<String> items = new ArrayList<>();

    @Override
    public void output(String output) {
        items.add(output);
    }

    public List<String> getItems() {
        return Collections.unmodifiableList(this.items);
    }

    public void printOutput(PrintStream stream) {
        for ( String item: items ) {
            stream.println( item );
        }
    }
}
