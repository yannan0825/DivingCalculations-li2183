package divingcalculations;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Formatter;

/**
 * Write a description of class Formulas here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DiveFormulas {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    public int modCal(double pp, int oxygen){
                //user input is pp(ata) and oxy %
                //convert oxy % to decimal Fg
                double Fg = (double) oxygen/100;
                double ataMOD = pp/Fg;
                //convert to depth in metre
                double metresMOD = (ataMOD-1)*10;
                int MOD = (int)Math.floor(metresMOD);
                return MOD;
    }

    public int smodCal(int oxygen){
        //user input is pp(ata) and oxy %
        //convert oxy % to decimal Fg
        double Fg = (double) oxygen/100;
        double ataSMOD = 1.4/Fg;
        //conver to depth in metres
        double metresSMOD = (ataSMOD-1)*10;
        int SMOD = (int)Math.floor(metresSMOD);
        return SMOD;
    }

    public int bmCal(double pp, double depth){
                //user input is pp(ata) and depth
                //convert depth to P
                double P = (double) (depth/10) +1;
                int BM = (int)Math.floor((pp/P)*100);
                return BM;
    }

    public double ppo2Cal(int oxygen, double depth){
                //user input is depth and oxy %
               // convert to oxy with decimal
                double Fg = (double) oxygen/100;
                //convert depth to P
                double P = (double) (depth/10) +1;
                double atappo2 = Fg*P;
                double ppo2 = Double.parseDouble(df.format(atappo2));
                return ppo2;

    }
    public int eadCal(double depth, int oxygen){
                //user input is depth and oxy %
                //convert depth to P
                double P = (double) (depth/10) +1;
                double Fg = (double) oxygen/100;
                double ataEAD = (double) (((1-Fg)*P)/0.79);
                //convert EAD (ata) to depth (metre)
                 double depthEAD = (ataEAD - 1)*10;
                 int EAD = (int) Math.round(depthEAD);
                return EAD;
    }

 public void createPPT(int start_oxygen, int end_oxygen, int start_depth, int end_depth){
        //calculate rows and columns size
        int rows = (end_depth - start_depth) / 3 + 1;
        int columns = (end_oxygen - start_oxygen) + 1;
        int metreDepth = start_depth;
        //create a table with 9*8 and fill the table one by one using formulas
        double [][] table = new double[rows][columns];
        for (int i=0 ; i < rows; i++){
            int oxygenMixPercentage = start_oxygen;
            for (int j = 0 ; j < columns; j++){
                table[i][j] = ppo2Cal(oxygenMixPercentage,metreDepth);
                oxygenMixPercentage += 1;
            }
            metreDepth += 3;
        }

        printPPT(table,start_oxygen,end_oxygen,start_depth, end_depth);
    }

    public void printPPT(double [][] table, int start_oxygen, int end_oxygen, int start_depth, int end_depth){
        System.out.print("\\      ");
        //print the column name
        for(int i = start_oxygen ; i <= end_oxygen ; i++ ){
            System.out.print(i + "    ");
        }
        System.out.println("\n ________________________________________________________________________");
        //print the row name
        int depth = start_depth;
        for (int j = 0 ; j < table.length ; j++ ){
            System.out.print(depth + "    ");
            for (int k = 0 ; k < table[0].length ; k++){
                if(table[j][k] >= 1.6){
                    System.out.print("     ");
                } else {
                    System.out.format("%.2f  ", table[j][k]);
                }
            }
            System.out.println();
            depth += 3;
        }
    }


    public void createEADT(int start_oxygen, int end_oxygen, int start_depth, int end_depth){
        //calculate rows and columns size
        int rows = (end_depth - start_depth) / 3 + 1;
        int columns = (end_oxygen - start_oxygen) + 1;
        int depth_metres = start_depth;
        //create a table with 10*5 and fill the table one by one using formulas
        double [][] table = new double[rows][columns];
        for (int i=0 ; i < rows; i++){
            int oxygenMixPercentage = start_oxygen;
            for (int j = 0 ; j < columns; j++){
                table[i][j] = eadCal(depth_metres,oxygenMixPercentage);
                oxygenMixPercentage += 1;
            }
            depth_metres += 3;
        }

        printEADT(table,start_oxygen,end_oxygen,start_depth, end_depth);
    }

    public void printEADT(double [][] table, int start_oxygen, int end_oxygen, int start_depth, int end_depth){

        System.out.print("\\     ");
        //print the column name
        for(int i = start_oxygen ; i <= end_oxygen ; i++ ){
            System.out.printf("%4d",i);
        }
        System.out.println("\n ____________________________________");
        //print the row name
        int depth = start_depth;
        for (int j = 0 ; j < table.length ; j++ ){
            System.out.print(depth + "    ");
            for (int k = 0 ; k < table[0].length ; k++){
                System.out.printf("%4.0f", table[j][k]);
            }
            System.out.println();
            depth += 3;
        }
    }

    public String[][] ppTable(int start_oxygen, int end_oxygen, int start_depth, int end_depth){
        int rows = (end_depth - start_depth) / 3 + 1;
        int columns = (end_oxygen - start_oxygen) + 1;
        int depth_metres = start_depth;
        //Set String [][] ppTable to hole every cell results
        String [][] ppTable = new String[rows][columns+1];

        for (int i=0 ; i < rows; i++){
            int oxygenMixPercentage = start_oxygen;
            ppTable[i][0] = String.valueOf(depth_metres);
            for (int j = 1 ; j < columns+1; j++){
                double ppo2Result = ppo2Cal(oxygenMixPercentage,depth_metres);
                ppTable[i][j] = String.valueOf(ppo2Result);
                if(ppo2Result >= 1.6){
                    ppTable[i][j] = "";
                }else{
                    ppTable[i][j] = String.format("%.2f  ", ppo2Result);
                }
                oxygenMixPercentage += 1;
            }
            depth_metres += 3;

        }
        return ppTable;
    }

    public String[] columnName(int start_oxygen, int end_oxygen){
        int columns = (end_oxygen - start_oxygen) + 1;
        //Set String [] columnName to hole column name
        String [] columnName = new String[columns+1];
        columnName [0] = " ";
        for (int n=1;n<columns+1;n++) {
            columnName [n] = String.valueOf(start_oxygen);
            start_oxygen++;
        }
        return columnName;
    }


    public String[][] eadTable(int start_oxygen, int end_oxygen, int start_depth, int end_depth){
        int rows = (end_depth - start_depth) / 3 + 1;
        int columns = (end_oxygen - start_oxygen) + 1;
        int depth_metres = start_depth;
        String [][] eadTable = new String[rows][columns+1];

        for (int i=0 ; i < rows; i++){
            int oxygenMixPercentage = start_oxygen;
             eadTable[i][0] = String.valueOf(depth_metres);
            for (int j = 1 ; j < columns+1; j++){
                int eadResult = eadCal(depth_metres,oxygenMixPercentage);
                eadTable[i][j] = String.valueOf(eadResult);
                oxygenMixPercentage += 1;
            }
            depth_metres += 3;
        }
        return eadTable;
    }


}
