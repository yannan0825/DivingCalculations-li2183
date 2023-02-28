package divingcalculations;

import java.math.RoundingMode;
import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Formatter;

public class DiveBroker {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    DiveFormulas db;
    public DiveBroker()
    {
        db = new DiveFormulas();
    }
    public String[] op;

    public DiveBroker(String[] op) {
        this.op = op;
    }

    public void help()
    {
        //show the content of 'help'
        System.out.println("You can select from the following choices:");
        System.out.println("1. HELP prints this message.");
        System.out.println("2. MOD (Maximum Operating Depth) for a supplied percentage of Oxygen and partial pressure");
        System.out.println("3. SMOD (Standard Maximum Operating Depth) for a supplied percentage of Oxygen and a standard partial pressure of 1.4 ata");
        System.out.println("4. BM (Best Mix) for a dive with a supplied partial pressure and depth (in metres)");
        System.out.println("5. PP (Partial Pressure) for a supplied percentage of oxygen and depth (in metres)");
        System.out.println("6. EAD (Equivalent Air Depth) for a supplied percentage of oxygen and depth (in metres)");
        System.out.println("7. EXIT Exit the dive formula calculator");
    }
    public void mod(){
        System.out.println("Calculating the MOD");
        // ask user to input oxy %
        System.out.println("Enter the percentage of Oxygen: ");
        Scanner in = new Scanner(System.in);
        int oxygen = in.nextInt();
        //convert % to decimal
        // ask user to input pp(ata)
        System.out.println("Enter the partial pressure of oxygen (between 1.1 and 1.6 inclusive): ");
        double pp = in.nextDouble();
        //use formula to calculate
        int MOD = db.modCal(pp, oxygen);
        //rounded down to keep within safe limits
        System.out.println("Maximum Operating Depth (MOD) for a dive with "
                +oxygen+"% O2 and a partial pressure of "
                +pp+" is "+MOD+" metres");
    }
    public void smod(){
        System.out.println("Calculating the MOD for the standard 1.4 partial pressure");
        //ask user to input oxy %
        System.out.println("Enter the percentage of Oxygen: ");
        Scanner in = new Scanner(System.in);
        int oxygen = in.nextInt();
        //use formula to calculate
        int SMOD = db.smodCal(oxygen);
        //rounded down to keep within safe limits
        System.out.println("Standard Maximum Operating Depth (SMOD) for a dive with "
                +oxygen+"% O2 and a partial pressure of 1.4 ata is "
                +SMOD+" metres");
    }
    public void bm(){
        System.out.println("Calculating the Best Mix");
        //ask user to input pp(ata)
        System.out.println("Enter the partial pressure of Oxygen (between 1.1 and 1.6 inclusive): ");
        Scanner in = new Scanner(System.in);
        double pp = in.nextDouble();
        //ask user to input p(metres)
        System.out.println("Enter the depth of the dive (in metres): ");
        int depth = in.nextInt();
        //use formula to calculate
        int BM = db.bmCal(pp,depth);
        //convert decimal to percentage and rounded down to keep within safe limits
        System.out.println("Best mix for a drive to "
                +depth+" metres with a partial pressure of "
                +pp+" is "+BM+"% O2");
    }
    public void ppo2(){
        System.out.println("Calculating the Partial Pressure");
        //ask user to input P(metre)
        System.out.println("Enter the depth of the dive (in metres): ");
        Scanner in =new Scanner(System.in);
        int depth = in.nextInt();

        //ask user to input oxy %
        System.out.println("Enter the percentage of Oxygen: ");
        int oxygen = in.nextInt();
        //use formula to calculate
        double ppo2 = db.ppo2Cal(oxygen, depth);

        //rounded down to keep within safe limits
        System.out.println("The partial pressure of oxygen for a dive to "
                +depth+" with a percentage of oxygen of "
                +oxygen+"% is "+ppo2+" ata");
    }
    public void ead(){
        System.out.println("Calculating the Equivalent Air Depth");
        //ask user to input P(metre)
        System.out.println("Enter the depth of the dive (in metres): ");
        Scanner in = new Scanner(System.in);
        int depth = in.nextInt();
        //ask user to input oxy %
        System.out.println("Enter the percentage of Oxygen: ");
        int oxygen = in.nextInt();
        //use formula to calculate
        double EAD = db.eadCal(depth, oxygen);
        System.out.println("Equivalent Air Depth for a dive with "
                +oxygen+"% O2 to a depth of "
                +depth+" metres is "+ EAD+" metres");
    }

    public void ppt(){
        System.out.println("Generating Partial Pressures Table");
        System.out.print("Enter a start and end percentage of Oxygen: ");
        //ask user to input start and end oxyg %
        Scanner in = new Scanner(System.in);
        String oxygen_input = in.nextLine();
        String[] inputOxygen = oxygen_input.split(" ");
        int start_oxygen = Integer.parseInt(inputOxygen[0]);
        int end_oxygen = Integer.parseInt(inputOxygen[1]);
        //ask user to input start and end depth
        System.out.print("Enter a start and end depth (in metres): ");
        String depth_input = in.nextLine();
        String[] inputDepth = depth_input.split(" ");
        int start_depth = Integer.parseInt(inputDepth[0]);
        int end_depth = Integer.parseInt(inputDepth[1]);
        System.out.println("Partial Pressures Table for " + start_oxygen + " to " + end_oxygen + " percent Oxygen and depths of " + start_depth + " to " + end_oxygen + " metres");
        System.out.println("=================================================================================");
        db.createPPT(start_oxygen, end_oxygen, start_depth, end_depth );
    }

    public void eadt(){
        System.out.println("Generating Partial Pressures Table");
        System.out.print("Enter a start and end percentage of Oxygen: ");
        Scanner in = new Scanner(System.in);
        String oxygen_input = in.nextLine();
        String[] inputOxygen = oxygen_input.split(" ");
        int start_oxygen = Integer.parseInt(inputOxygen[0]);
        int end_oxygen = Integer.parseInt(inputOxygen[1]);

        System.out.print("Enter a start and end depth (in metres): ");
        String depth_input = in.nextLine();
        String[] inputDepth = depth_input.split(" ");
        int start_depth = Integer.parseInt(inputDepth[0]);
        int end_depth = Integer.parseInt(inputDepth[1]);

        System.out.println("Equivalent Air Depth Table for " + start_oxygen + " to " + end_oxygen + " percent Oxygen and depths of " + start_depth + " to " + end_oxygen + " metres");
        System.out.println("=================================================================================");
        db.createEADT(start_oxygen, end_oxygen, start_depth, end_depth);
    }

    public boolean isContain (String option){
        for (String x : op) {
            if (option.toUpperCase() == x) {
                return true;
            }
        }
        return false;
    }


}
