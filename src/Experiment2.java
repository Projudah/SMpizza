import cern.jet.random.engine.RandomSeedGenerator;
import outputAnalysis.ConfidenceInterval;
import smPizzaModel.SMPizza;
import smPizzaModel.Seeds;

public class Experiment2 {
    public static void main(String[] args) {
        //step 1
        System.out.println("Step 1A: 3 MAKE-TABLE EMPLOYEES, 605 IN2 OVEN AND 4 DRIVERS");
        experiment(3,4,605);
        System.out.println("Step 1B: 3 MAKE-TABLE EMPLOYEES, 605 IN2 OVEN AND 3 DRIVERS");
        experiment(3,3,605);
        System.out.println("\nStep 1C: 3 MAKE-TABLE EMPLOYEES, 605 IN2 OVEN AND 2 DRIVERS");
        experiment(3,2,605);
        System.out.println("\nStep 1D: 3 MAKE-TABLE EMPLOYEES, 605 IN2 OVEN AND 1 DRIVERS");
        experiment(3,1,605);
        System.out.println("\nThe number of drivers allowing for the lowest customer satisfaction above 90% is 2. "
                + "\nThis value will be used for all subsequent cases.");
        //Step 2
        System.out.println("\nStep 2A: 3 MAKE-TABLE EMPLOYEES, 605 IN2 OVEN AND 3 DRIVERS");
        experiment(3,3,605);
        System.out.println("\nStep 2B: 3 MAKE-TABLE EMPLOYEES, 520 IN2 OVEN AND 3 DRIVERS");
        experiment(3,3,520);
        System.out.println("\nStep 2C: 3 MAKE-TABLE EMPLOYEES, 435 IN2 OVEN AND 3 DRIVERS");
        experiment(3,3,435);
        System.out.println("\nThe oven size allowing for the lowest customer satisfaction above 90% is 605. "
                + "\nThis value will be used for all subsequent cases.");
        //Step 3
        System.out.println("\nStep 3B: 2 MAKE-TABLE EMPLOYEES, 435 IN2 OVEN AND 3 DRIVERS");
        experiment(2,3,435);
        System.out.println("\nStep 3C: 1 MAKE-TABLE EMPLOYEES, 435 IN2 OVEN AND 3 DRIVERS");
        experiment(1,3,435);
        System.out.println("\nThe number of make-table employees allowing for the lowest customer satisfaction above 90% is 1.");
    }

    public static void experiment(int numTableEmp, int numDrivers, int sizeOven) {
        final int NUMMAKETABLEEMPLOYEES = numTableEmp;
        final int NUMDRIVERS = numDrivers;
        final int SIZEOFOVEN = sizeOven;

        final int NUMRUNS = 40; // Number of runs
        final double CONF_LEVEL = 0.9;  // Confidence levels

        final boolean logging = false; // Do not display logging data

        int i;
        Seeds[] sds = new Seeds[NUMRUNS];
        SMPizza model;
        ConfidenceInterval  cfInterval;

        RandomSeedGenerator rsg = new RandomSeedGenerator();
        for (i = 0; i < NUMRUNS; i++) {
            sds[i] = new Seeds(rsg);
        }

        double t0Time = 0.0;
        double tfTime = 180.0;
        double [] customerSatisfactionValues = new double[NUMRUNS];


        for (i = 0; i < NUMRUNS; i++) {
            model = new SMPizza(t0Time,tfTime,NUMMAKETABLEEMPLOYEES, NUMDRIVERS, SIZEOFOVEN,new Seeds(rsg),logging);
            model.runSimulation();

            customerSatisfactionValues[i] = model.getCustomerSatisfaction();
        }

        cfInterval = new ConfidenceInterval(customerSatisfactionValues, CONF_LEVEL);

        /*------------ Display the resulting confidence intervals --------------*/
        // Header
        System.out.print("Run | Customer Satisfaction (CS)\n");
        System.out.print("------------------------\n");
        // Simulation values
        for(i = 0; i < NUMRUNS; i++) {
            System.out.printf("%5d | %8.3f\n",i+1, customerSatisfactionValues[i]);
        }

        // Confidence intervals
        System.out.print("------------------------\n");
        // Point Estimates
        System.out.printf("     PE  %8.3f\n", cfInterval.getPointEstimate());
        // Standard Deviations
        System.out.printf("   S(n)  %8.3f\n", cfInterval.getStdDev());
        // Confidence Half-Interval
        System.out.printf("   zeta  %8.3f\n", cfInterval.getZeta());
        // Minimum Value in Confidence Interval
        System.out.printf(" CI Min  %8.3f\n", cfInterval.getCfMin());
        // Maximum Value in Confidence Interval
        System.out.printf(" CI Max  %8.3f\n", cfInterval.getCfMax());
        // ratio of Confidence Half-Interval to Point Estimates
        System.out.printf("zeta/PE  %8.3f\n",  cfInterval.getZeta()/cfInterval.getPointEstimate());
        System.out.print("------------------------\n");
    }
}
