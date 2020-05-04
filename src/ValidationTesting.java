import cern.jet.random.engine.RandomSeedGenerator;
import smPizzaModel.SMPizza;
import smPizzaModel.Seeds;

import java.util.Scanner;

public class ValidationTesting {
    public static void main(String[] args) {

        // Variables
        int i;

        SMPizza model;
        boolean logging = true;

        // Input variables
        int NUMRUNS = -1;
        int NUMMAKETABLEMPLOYEE = -1;
        int NUMDRIVERS = -1;
        int OVENSIZE = -1;

        boolean allRight = false;

        double t0Time = 0.0;
        double tfTime = 180.0;

        // Random
        RandomSeedGenerator rsg = new RandomSeedGenerator();

        // User input for all the things
        Scanner userInput = new Scanner(System.in);

        while(!allRight)
        {
            System.out.println("How many times would you like to run this experiment? (1 or more) : ");

            // Enter the number of runs
            while(NUMRUNS <= 0)
            {
                System.out.print("NUMRUNS : ");
                NUMRUNS = userInput.nextInt();
            }

            System.out.println();
            System.out.println("How many make table employees should there be? (1 - 3) : ");

            // Enter number of Make table employees
            while(NUMMAKETABLEMPLOYEE <= 0 || NUMMAKETABLEMPLOYEE > 3)
            {
                System.out.print("NUMMAKETABLEMPLOYEE : ");
                NUMMAKETABLEMPLOYEE = userInput.nextInt();
            }

            // Set first position of parameter list to NUMMAKETABLEMPLOYEE

            System.out.println();
            System.out.println("How many drivers should there be? (1 - 10) : ");

            // Enter number of drivers you want
            while(NUMDRIVERS <= 0 || NUMDRIVERS > 10)
            {
                System.out.print("NUMDRIVERS : ");
                NUMDRIVERS = userInput.nextInt();
            }

            // Set second position of parameter list to NUMDRIVERS

            System.out.println();
            System.out.println("What size should the oven be? (435, 520, or 605)");

            // Enter size of oven
            while(!(OVENSIZE == 435 || OVENSIZE == 520 || OVENSIZE ==  605))
            {
                System.out.print("OVENSIZE : ");
                OVENSIZE = userInput.nextInt();
            }

            // Set third position of parameter list to OVENSIZE

            allRight = true;
        }

        Seeds[] sds = new Seeds[NUMRUNS];

        // Create a seed for each NUMRUNS
        for (i = 0; i < NUMRUNS; i++)
        {
            sds[i] = new Seeds(rsg);
        }

        // Run the simulation NUMRUNS time
        for (i = 0; i < NUMRUNS; i++) {
            model = new SMPizza(t0Time, tfTime, NUMMAKETABLEMPLOYEE, NUMDRIVERS, OVENSIZE, sds[i], logging);
            model.runSimulation();
        }
    }
}
