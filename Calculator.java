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

    private List<List<Integer>> resultMatrix;

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
                row.add(n);
                
                System.out.print(n);
                if (j < columns - 1) {
                    // Print until last
                    System.out.print(", ");
                }
            }
            
            matrix1.add(row);
            System.out.println("");
        }
        
        // Create matrix 2
        System.out.println("Creating Matrix 2");
        
        matrix2 = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            // Populate our row
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                int n = rand.nextInt(10);
                row.add(n);

                System.out.print(n);
                if (j < columns - 1) {
                    // Print until last
                    System.out.print(", ");
                }
            }

            matrix2.add(row);
            System.out.println("");
        }

        // Create our result matrix
        resultMatrix = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            // Populate our row
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                row.add(-1);
            }

            resultMatrix.add(row);
        }
    }

    // Helper Methods

    private List<List<Integer>> colsFromSubIndicies(int bottomIndex, int topIndex) {
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

    // Runner

    private class Runner implements Runnable {
        private Calculator calc;

        private Integer num;
        private List<List<Integer>> rows;
        private List<List<Integer>> cols;

        public Runner(Integer num, List<List<Integer>> rows, List<List<Integer>> cols, Calculator calc) {
            this.num = num;
            this.rows = rows;
            this.cols = cols;
            this.calc = calc;
        }

        // Main

        public void run() {
            System.out.println("Thread #" + num + " is beginning");

            // We go through every row, and multiply by the 4 cols we have
            for (int i = 0; i < cols.size(); i++) {
                // Calculate our product
                int index = (num * 4) + i;
                List<Integer> col = cols.get(i);

                for (int j = 0; j < rows.size(); j++) {
                    Integer product = 0;
                    List<Integer> row = rows.get(j);

                    for (int k = 0; k < row.size(); k++) {
                        product += row.get(k) * col.get(k);
                    }

                    calc.setValueOnProductMatrix(j, index, product);
                }
            }
        }
    }

    // Matrix multiplication

    public synchronized void setValueOnProductMatrix(int row, int col, Integer value) {
        // Simply call to our result matrix
        resultMatrix.get(row).set(col, value);
    }

    public void matrixProduct() {
        // We create our threads
        List<Runner> runners = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            // Get cols
            // Since we need all the rows in order to equally divide into 5 parts, we instead subdivide the cols
            int bottomIndex = i * 4;
            int topIndex = (i + 1) * 4;
            List<List<Integer>> cols = colsFromSubIndicies(bottomIndex, topIndex);
            
            // Create our runner
            Runner runner = new Runner(i, matrix1, cols, this);
            runners.add(runner);

            Thread thread = new Thread(runner);
            threads.add(thread);
            thread.start();
        }

        // We wait for all of them
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (Exception e) {
                // If error, simply sysout
                System.out.println("Error: " + e.getMessage());
            }
        }

        // At this point we're finished, lets print
        for (List<Integer> row : resultMatrix) {
            for (Integer product : row) {
                System.out.print(product + ", ");
            }

            System.out.println("");
        }
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();

        // Perform calculations
        System.out.println("The matrix product is:");
        calc.matrixProduct();
    }
}