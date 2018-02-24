package com.jimboweb.heirholzersalgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleInput implements Inputter {
    public List<List<Integer>> getInput(){
        Scanner scanner = new Scanner(System.in);
        List<List<Integer>> inputs = new ArrayList<>();
        List<Integer> in = new ArrayList<>();
        in.add(scanner.nextInt());
        in.add(scanner.nextInt());
        if (in.get(0) == 0 || in.get(1) == 0) {
            System.out.println("0");
            System.exit(0);
        }
        inputs.add(in);
        for (int i = 0; i < inputs.get(0).get(1); i++) {
            in = new ArrayList<>();
            in.add(scanner.nextInt());
            in.add(scanner.nextInt());
            inputs.add(in);

        }
        return inputs;
    }
}
