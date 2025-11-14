package binary;
import java.util.Random;
import tabular.DataSet;
import tabular.DataRow;


public class GPTree implements Comparable<GPTree>, Cloneable {

    private final int numIndepVars;  
    private final int maxDepth;   
    private final Random rand;  

    private Node root;     

    private double fitness = Double.POSITIVE_INFINITY;

    public GPTree(int numIndepVars, int maxDepth, Random rand) {
        this.numIndepVars = Math.max(0, numIndepVars);
        this.maxDepth = Math.max(1, maxDepth);
        this.rand = (rand == null) ? new Random() : rand;
        this.root = build(this.maxDepth);
    }

    public GPTree(NodeFactory factory, int maxDepth, Random rand) {
        this.maxDepth = Math.max(1, maxDepth);
        this.rand = (rand == null) ? new Random() : rand;
        this.numIndepVars = factory.getNumIndepVars();

        Node rootOp = factory.getOperator(this.rand);
        rootOp.depth = 0;
        rootOp.addRandomKids(factory, this.maxDepth, this.rand);
        this.root = rootOp;
    }

    private Node build(int depth) {
        if (depth <= 1) {
            return makeRandomLeaf();
        }
        Node left  = build(depth - 1);
        Node right = build(depth - 1);
        Binop op   = randomOp();
        return new Node(op, left, right);
    }

    private Node makeRandomLeaf() {
        boolean pickVar = rand.nextBoolean();
        if (pickVar && numIndepVars > 0) {
            int idx = rand.nextInt(numIndepVars);
            return new Node(new Variable(idx));
        } 
        else {
            int n = rand.nextInt(20) + 1; 
            return new Node(new Const(n));
        }
    }

    private Binop randomOp() {
        int k = rand.nextInt(4);
        if (k == 0) return new Plus();
        if (k == 1) return new Minus();
        if (k == 2) return new Mult();
        return new Divide();
    }

    public Node getRoot() {
        return root;
    }

    public double eval(double[] xVals) {
        if (root == null) {
            return 0.0;
        }
        return root.eval(xVals);
    }

    public void traverse() {
        Collector.clearCollected();
        Collector c = new Collector(numIndepVars, maxDepth);
        if (root != null) {
            root.traverse(c);
        }
    }

    public void traverse(Collector c) {
        if (c == null) return;
        if (root != null) {
            root.traverse(c);
        }
    }

    public String getCrossNodes() {
        return Collector.collectedString;
    }

    public void evalFitness(DataSet dataSet) {
        if (dataSet == null) {
            throw new IllegalArgumentException("dataSet cannot be null");
        }

        double sum = 0.0;

        for (DataRow row : dataSet) {
            double[] xVals = row.getX();  
            double y = row.getY();
            double guess = eval(xVals);
            double diff = guess - y;
            sum += diff * diff;
        }

        this.fitness = sum;
    }

    public double getFitness() {
        return fitness;
    }

    public int compareTo(GPTree other) {
        if (other == null) {
            return 1;
        }
        return Double.compare(this.fitness, other.fitness);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GPTree)) return false;
        GPTree other = (GPTree) o;
        return Double.compare(this.fitness, other.fitness) == 0;
    }

    public Object clone() {
        try {
            GPTree copy = (GPTree) super.clone();
            if (this.root != null) {
                copy.root = (Node) this.root.clone();
            }
            return copy;
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public String toString() {
        return (root == null) ? "(empty)" : root.toString();
    }
}
