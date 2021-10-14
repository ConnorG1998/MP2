//Machine Problem 2 Group 10
//Alfonso Gonzalez & Connor Griffith
//10/13/2021

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Calculator {
    private List<List<Integer>> matrix1;
    private List<List<Integer>> matrix2;

    private int threadCount = 5;

    public Calculator() {
        // SImply call to generate matrix
        generateMatrices();
    }

    // Matrix generation

    private void generateMatrices() {
        // Populate the 20x20 matrix
        Random rand = new Random();
        int rows = 20;
        int columns = 20;

        // Create matrix 1
        System.out.println("Creating Matrix 1");
        
        matrix1 = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            // Populate our row
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                int n = rand.nextInt(10);
                System.out.print(n);
                System.out.print(", ");

                row.add(n);
            }
            
            matrix1.add(row);
            System.out.println("");
        }
        
        // Create matrix 2
        System.out.println("Creating Matrix 1");
        
        matrix2 = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            // Populate our row
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                int n = rand.nextInt(10);
                System.out.print(n);
                System.out.print(", ");

                row.add(n);
            }

            matrix2.add(row);
            System.out.println("");
        }
    }

    // Matrix multiplication

    private class Runner implements Runnable {
        private List<List<Integer>> rows;
        private List<List<Integer>> cols;

        private Integer result;

        public Runner(List<List<Integer>> rows, List<List<Integer>> cols) {
            this.rows = rows;
            this.cols = cols;
        }

        // Main

        public void run() {
            // We do the multiplication here
            // result = //not sure how to thread the first 4 rows
            //pseudo code: (row : colunm),(i : j)
            //1 of 5 threads each where each consecutive thread uses a+5,b+5:
            //int a = 0;
            //int b = 4;
            //for z=a:b {
            //  matrix1(:z)*matrix2(z:); //entirety of respective column/row                  
            //}
        }

        // Results

        public Integer getResult() {
            // Simply retrieve our result
            return result;
        }
    }

    public List<List<Integer>> colsFromSubIndicies(int bottomIndex, int topIndex) {
        // Since top level arraylist is rows, and sublists contain column entries, we need to iterate through the nth entries of the rows and add those entries
        // Create our cols
        List<List<Integer>> cols = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            cols.add(new ArrayList<>());
        }

        // Retrieve from matrix
        for (int i = 0; i < matrix2.size(); i++) {
            List<Integer> row = matrix2.get(i);
            
            for (int j = bottomIndex; j < topIndex; j++) {
                Integer value = row.get(j);
                cols.get(j - bottomIndex).add(value);
            }
        }

        return cols;
    }

    public Integer matrixProduct() {
        // We create our threads
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            // Get subarrays
            int bottomIndex = i * 4;
            int topIndex = (i + 1) * 4;
            List<List<Integer>> rows = matrix1.subList(bottomIndex, topIndex);
            List<List<Integer>> cols = colsFromSubIndicies(bottomIndex, topIndex);
            
            // Create our thread from a portion of the matricies
            Runner runner = new Runner(rows, cols);
            Thread thread = new Thread(runner);
        }

        // Temporary to get compiling
        return 0;
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();

        // Perform calculations
    }
}