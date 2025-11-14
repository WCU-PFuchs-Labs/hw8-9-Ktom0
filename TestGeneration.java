import java.util.ArrayList;
import binary.Generation;
import binary.GPTree;

public class TestGeneration {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Usage: java TestGeneration <datafile>");
            return;
        }

        String fileName = args[0];

        Generation gen = new Generation(500, 6, fileName);

        gen.evalAll();

        gen.printBestTree();
        gen.printBestFitness();

        ArrayList<GPTree> top = gen.getTopTen();

        System.out.print("Top 10 fitness values: ");
        for (int i = 0; i < top.size(); i++) {
            System.out.printf("%.2f", top.get(i).getFitness());
            if (i < top.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
}
