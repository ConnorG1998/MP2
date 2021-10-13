//Machine Problem 2 Group 10
//Alfonso Gonzalez & Connor Griffith
//9/23/2021

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private ArrayList<ArrayList<Float>> matrix1;
    private ArrayList<ArrayList<Float>> matrix2;

    private int threadCount = 5;

    public Calculator() {
        // SImply call to generate matrix
        this.matrix1 = new ArrayList<>();
        this.matrix2 = new ArrayList<>();
        generateMatrices();
    }

    // Matrix generation

    private void generateMatrices() {
        // Populate the 20x20 matrix
        // Temporary for testing
        for (int i = 0; i < 20; i++) {
            ArrayList<Float> row = new ArrayList<>();
            for (int j = 0; j < 20; j++) {
                row.add(Float.valueOf(j));
            }

            matrix2.add(row);
        }
    }

    // Matrix multiplication

    private class Runner implements Runnable {
        private List<ArrayList<Float>> rows;
        private List<ArrayList<Float>> cols;

        private Float result;

        public Runner(List<ArrayList<Float>> rows, List<ArrayList<Float>> cols) {
            this.rows = rows;
            this.cols = cols;
        }

        // Main

        public void run() {
            // We do the multiplication here
            
        }

        // Results

        public Float getResult() {
            // Simply retrieve our result
            return result;
        }
    }

    public ArrayList<ArrayList<Float>> colsFromSubIndicies(int bottomIndex, int topIndex) {
        // Since top level arraylist is rows, and sublists contain column entries, we need to iterate through the nth entries of the rows and add those entries
        // Create our cols
        ArrayList<ArrayList<Float>> cols = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            cols.add(new ArrayList<>());
        }

        // Retrieve from matrix
        for (int i = 0; i < matrix2.size(); i++) {
            ArrayList<Float> row = matrix2.get(i);
            
            for (int j = bottomIndex; j < topIndex; j++) {
                Float value = row.get(j);
                cols.get(j - bottomIndex).add(value);
            }
        }

        return cols;
    }

    public Float matrixProduct() {
        // We create our threads
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            // Get subarrays
            int bottomIndex = i * 4;
            int topIndex = (i + 1) * 4;
            List<ArrayList<Float>> rows = matrix1.subList(bottomIndex, topIndex);
            ArrayList<ArrayList<Float>> cols = colsFromSubIndicies(bottomIndex, topIndex);
            
            // Create our thread from a portion of the matricies
            Runner runner = new Runner(rows, cols);
            Thread thread = new Thread(runner);
        }

        // Temporary to get compiling
        return Float.valueOf(0);
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();

        // Perform calculations
    }
}