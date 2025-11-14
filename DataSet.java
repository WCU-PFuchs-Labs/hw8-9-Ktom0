package tabular;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class DataSet {

    private ArrayList<DataRow> data;   
    private int numIndepVariables;      

   
    public DataSet(String fileName) {
        data = new ArrayList<DataRow>();
        numIndepVariables = 0;

        Scanner sc = null;
        try {
            sc = new Scanner(new File(fileName));

            if (sc.hasNextLine()) {
                String header = sc.nextLine().trim();
                String[] names = header.split(",");

              if (names.length >= 2) {
                    numIndepVariables = names.length - 1;
                } 
              else {
                    numIndepVariables = 0;
                }
            }

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.length() == 0) {
                    continue; 
                }

                String[] parts = line.split(",");
                if (parts.length < 1 + numIndepVariables) {
                    continue;
                }

                double yValue = 0.0;
                try {
                    yValue = Double.parseDouble(parts[0].trim());
                } 
                catch (NumberFormatException e) {
                    continue;
                }

                double[] xVals = new double[numIndepVariables];
                int i = 0;
                while (i < numIndepVariables) {
                    String cell = parts[i + 1].trim();
                    try {
                        xVals[i] = Double.parseDouble(cell);
                    } 
                    catch (NumberFormatException e) {
                        xVals = null;
                        break;
                    }
                    i++;
                }
                if (xVals == null) {
                    continue;
                }

                data.add(new DataRow(yValue, xVals));
            }
        }
          
        catch (FileNotFoundException e) {
            System.out.println("Could not open file: " + fileName);
        }
        
        finally {
            if (sc != null) sc.close();
        }
    }

    public ArrayList<DataRow> getRows() {
        return data;
    }

    public int getNumIndependentVariables() {
        return numIndepVariables;
    }
}
