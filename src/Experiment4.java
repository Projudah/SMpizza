import cern.jet.random.engine.RandomSeedGenerator;
import outputAnalysis.ConfidenceInterval;
import smPizzaModel.SMPizza;
import smPizzaModel.Seeds;

public class Experiment4 {
    // Constants
    static final int NUMRUNS = 10000;
    static final double CONF_LEVEL = 0.9;
    static final int [] NUM_RUNS_ARRAY = {20, 30, 40, 60, 80, 100, 1000, 10000};

    public static void main(String[] args)
    {
        // Local variables
        int i;
        double t0Time = 0.0;
        double tfTime = 180.0;
        Seeds[] sds = new Seeds[NUMRUNS];
        SMPizza model;  // Simulation object
        double [] valuesCase1 = new double[NUMRUNS];
        double [] valuesCase2 = new double[NUMRUNS];

        // Lets get a set of uncorrelated seeds
        RandomSeedGenerator rsg = new RandomSeedGenerator();
        for(i=0 ; i<NUMRUNS ; i++) sds[i] = new Seeds(rsg);

        // Loop for NUMRUN simulation runs for each case
        // Step 1B

        System.out.println("Step 1B: 3 MAKE-TABLE EMPLOYEES, 435 IN2 OVEN AND 3 DRIVERS");
        for(i=0 ; i < NUMRUNS ; i++) {
            model = new SMPizza(t0Time,tfTime,3,3,435,sds[i], false);
            model.runSimulation();
            valuesCase1[i] = model.getCustomerSatisfaction();
        }
        // Step 1C
        System.out.println("Step 1C: 3 MAKE-TABLE EMPLOYEES, 435 IN2 OVEN AND 2 DRIVERS");
        for(i=0 ; i < NUMRUNS ; i++)
        {
            model = new SMPizza(t0Time,tfTime,3,2,435,sds[i], false);
            model.runSimulation();
            valuesCase2[i] = model.getCustomerSatisfaction();
        }

        displayTable(valuesCase1,"1B");
        displayTable(valuesCase2,"1C");
    }

    /*------------ Display the confidence intervals for various number of simulations --------------*/
    private static void displayTable(double [] allValues, String caseNum)
    {
        System.out.printf("------------------------------------------------------------------\n");
        System.out.printf("                             Step%s\n", caseNum);
        System.out.printf("------------------------------------------------------------------\n");
        System.out.printf("n        y(n)     s(n)     zeta(n)  CI Min   CI Max   zeta(n)/y(n)\n");
        System.out.printf("------------------------------------------------------------------\n");
        for(int ix1 = 0; ix1 < NUM_RUNS_ARRAY.length; ix1++)
        {
            int numruns = NUM_RUNS_ARRAY[ix1];
            double [] values = new double[numruns];
            for(int ix2 = 0 ; ix2 < numruns; ix2++) values[ix2] = allValues[ix2];
            ConfidenceInterval ci = new ConfidenceInterval(values, CONF_LEVEL);
            System.out.printf("%5d %8.3f %8.3f %8.3f %8.3f %8.3f %8.3f\n",
                    numruns, ci.getPointEstimate(), ci.getStdDev(), ci.getZeta(),
                    ci.getCfMin(), ci.getCfMax(), ci.getZeta()/ci.getPointEstimate());
        }
        System.out.printf("------------------------------------------------------------------\n");
    }
}
