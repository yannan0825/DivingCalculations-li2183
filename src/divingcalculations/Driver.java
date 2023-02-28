package divingcalculations;
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.Formatter;

/**
 * Write a description of class Driver here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Driver {
    public static void main(String[] args) {

        /**
         * Declare instance variables
         */
        DiveBroker dive = new DiveBroker();
        DivePanel divePanel = new DivePanel();
        Scanner in = new Scanner(System.in);
        String choice; //should be y or n (upper or lower case
        String option;  //should be 1 to 7
        String[] op = {"HELP", "MOD", "SMOD", "BM", "PP", "EAD", "EADT", "PPT"};
        DiveBroker check = new DiveBroker(op);

        //Create Diving Calculations window
        divePanel.createWindow();

        System.out.println("Welcome to the Dive Formula Calculator");
            do {
                System.out.println("Which calculation do you wish to perform (Help/MOD/SMOD/BM/PP/PPT/EAD/EADT)?");
                // ask user to input option
                   do {
                    option = in.nextLine();
                    //Change option to Upper case so that it can do this ignoring case actually
                    switch (option.toUpperCase()) {
                        case "HELP":
                            dive.help();
                            break;
                        case "MOD":
                            dive.mod();
                            break;
                        case "SMOD":
                            dive.smod();
                            break;
                        case "BM":
                            dive.bm();
                            break;
                        case "PP":
                            dive.ppo2();
                            break;
                        case "EAD":
                            dive.ead();
                            break;
                        case "PPT":
                            dive.ppt();
                            break;
                        case "EADT":
                            dive.eadt();
                            break;
                        case "EXIT":
                            break;
                        default:
                            System.out.println("Invalid option. Please reenter: ");
                            break;
                        }

                   } while (check.isContain(option));
                System.out.println("Would you like to perform another calculation (y/n)?");
                choice=in.nextLine();
            } while (choice.equalsIgnoreCase("y"));

    }

}
