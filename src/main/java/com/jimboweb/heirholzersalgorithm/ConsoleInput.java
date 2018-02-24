package com.jimboweb.heirholzersalgorithm;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleInput implements Inputter {
    public ArrayList<ArrayList<Integer>> getInput(){
        Scanner scanner = new Scanner(System.in);
        ArrayList<ArrayList<Integer>> inputs = new ArrayList<>();
        ArrayList<Integer> in = new ArrayList<>();
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
