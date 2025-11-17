import java.util.ArrayList;
import java.util.Scanner;
import binary.Generation;
import binary.GPTree;

public class TestGeneration {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String fileName = sc.nextLine().trim();
        sc.close();

        Generation gen = new Generation(500, 6, fileName);

        gen.evalAll();

        gen.printBestTree();

        gen.printBestFitness();

        ArrayList<GPTree> topTenTrees = gen.getTopTen();

        System.out.print("Top Ten Fitness Values: ");

        for (int i = 0; i < topTenTrees.size(); i++) {
            double fitnessValue = topTenTrees.get(i).getFitness();

            System.out.printf("%.2f", fitnessValue);

            if (i < topTenTrees.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.println();  
    }
}
