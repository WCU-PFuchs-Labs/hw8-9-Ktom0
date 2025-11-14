package tabular;

public class DataRow {

    private double y;
    private double[] x;

    public DataRow(double y, double[] x) {
        this.y = y;

        if (x == null) {
            this.x = new double[0];
        } 
        else {
            this.x = new double[x.length];
            for (int i = 0; i < x.length; i++) {
                this.x[i] = x[i];
            }
        }
    }

     public DataRow() {
        this.y = 0.0;
        this.x = new double[0];
    }
    
    public double getDependentVariable() {
        return this.y;
    }
    

    public double[] getIndependentVariables() {
        return this.x; 
    }
}
