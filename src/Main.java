import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        double startingTime = System.currentTimeMillis();
        AVLTree avlTree = new AVLTree();

        // Check input file argument
        String inputFileName = (args.length > 0) ? args[0] : "inputs/type5.txt";

        // "Try-with-resources" - Java automatically closes these when done!
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
             Scanner sc = new Scanner(new FileInputStream(inputFileName))) {

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                // Skip empty lines to prevent crashes
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(" ");
                String command = parts[0];

                switch (command) {
                    case "create_parking_lot":
                        avlTree.create_parking_lot(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                        break;
                    case "add_truck":
                        writer.write(avlTree.add_truck(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
                        writer.newLine();
                        break;
                    case "ready":
                        writer.write(avlTree.ready(Integer.parseInt(parts[1])));
                        writer.newLine();
                        break;
                    case "load":
                        writer.write(avlTree.load(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
                        writer.newLine();
                        break;
                    case "count":
                        writer.write(avlTree.count(Integer.parseInt(parts[1])));
                        writer.newLine();
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        double endingTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endingTime - startingTime) / 1000 + " seconds");
    }
}