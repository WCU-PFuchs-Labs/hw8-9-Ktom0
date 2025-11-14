package binary;
import java.util.ArrayList;
import java.util.Random;
import tabular.DataSet;

public class Generation {

    private GPTree[] trees;
    private DataSet dataSet;
    private Random rand;
    private int maxDepth;

    public Generation(int size, int maxDepth, String fileName) {
        dataSet = new DataSet(fileName);

        this.maxDepth = maxDepth;
        rand = new Random();

        int numIndepVars = dataSet.getNumIndep();

        NodeFactory factory = new NodeFactory(numIndepVars);

        trees = new GPTree[size];
        for (int i = 0; i < size; i++) {
            trees[i] = new GPTree(factory, maxDepth, rand);
        }
    }

    public void evalAll() {
        for (int i = 0; i < trees.length; i++) {
            trees[i].evalFitness(dataSet);
        }

        for (int i = 0; i < trees.length - 1; i++) {
            int bestIndex = i;

            for (int j = i + 1; j < trees.length; j++) {
                if (trees[j].compareFitness(trees[bestIndex]) < 0) {
                    bestIndex = j;
                }
            }

            GPTree temp = trees[i];
            trees[i] = trees[bestIndex];
            trees[bestIndex] = temp;
        }
    }

    public ArrayList<GPTree> getTopTen() {
        ArrayList<GPTree> result = new ArrayList<GPTree>();

        int howMany = 10;
        if (trees.length < howMany) {
            howMany = trees.length;
        }

        for (int i = 0; i < howMany; i++) {
            result.add(trees[i]);
        }

        return result;
    }

    public void printBestFitness() {
        if (trees.length == 0) {
            System.out.println("No trees in this generation.");
            return;
        }

        double bestFitness = trees[0].getFitness();

        System.out.printf("Best fitness: %.2f%n", bestFitness);
    }

    public void printBestTree() {
        if (trees.length == 0) {
            System.out.println("No trees in this generation.");
            return;
        }

        System.out.println("Best tree:");
        System.out.println(trees[0].toString());
    }

    public void evolve() {
        if (trees.length == 0) {
            return;
        }

        evalAll();

        GPTree[] newTrees = new GPTree[trees.length];

        int half = trees.length / 2;
        if (half == 0) {
            half = 1; 
        }

        int index = 0;

        while (index < newTrees.length) {
            int p1Index = rand.nextInt(half);
            int p2Index = rand.nextInt(half);

            GPTree parent1 = trees[p1Index];
            GPTree parent2 = trees[p2Index];

            GPTree child1 = parent1.copy();
            GPTree child2 = parent2.copy();

            newTrees[index] = child1;
            index++;

            if (index < newTrees.length) {
                newTrees[index] = child2;
                index++;
            }
        }

        trees = newTrees;
    }

    public GPTree[] getTrees() {
        return trees;
    }
}
