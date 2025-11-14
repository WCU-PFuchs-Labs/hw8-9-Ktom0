import java.util.Scanner;
import binary.Generation;
import binary.GPTree;
import java.util.ArrayList;

public class TestGP {

    public static void main(String[] args) {

        String fileName;

        if (args.length >= 1) {
            fileName = args[0];
        } 
        else {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter the data file name:");
            fileName = keyboard.nextLine();
        }

        int generationSize = 500;
        int maxDepth = 6;

        Generation gen = new Generation(generationSize, maxDepth, fileName);

        int totalGenerations = 50;

        for (int g = 1; g <= totalGenerations; g++) {

            gen.evalAll();

            System.out.println("Generation " + g + ":");

            gen.printBestTree();

            gen.printBestFitness();

            ArrayList<GPTree> topTen = gen.getTopTen();
            System.out.print("Top 10 fitness values: ");
            for (int i = 0; i < topTen.size(); i++) {
                double fit = topTen.get(i).getFitness();
                System.out.printf("%.2f", fit);
                if (i < topTen.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();

            System.out.println("------");

            gen.evolve();
        }
    }
}
