package com.jimboweb.heirholzersalgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DummyInputter implements Inputter {

    private List<List<Integer>> items;

    public DummyInputter(List<List<Integer>> items) {
        this.items = Collections.unmodifiableList(items);
    }

    @Override
    public List<List<Integer>> getInput() {
        return items;
    }
}
