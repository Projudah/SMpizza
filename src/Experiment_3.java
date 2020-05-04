import cern.jet.random.engine.RandomSeedGenerator;
import outputAnalysis.ConfidenceInterval;
import smPizzaModel.SMPizza;
import smPizzaModel.Seeds;

public class Experiment_3 {
    public static void main(String[] args) {

        final int NUMRUNS = 20; // Number of runs
        final double CONF_LEVEL = 0.9;  // Confidence levels
        final boolean logging = false; // Do not display logging data

        int i;
        Seeds[] sds = new Seeds[NUMRUNS];
        SMPizza model;
        ConfidenceInterval cfInterval1;
        ConfidenceInterval cfInterval2;
        ConfidenceInterval cfInterval3;
        ConfidenceInterval cfInterval4;
        ConfidenceInterval cfInterval5;
        ConfidenceInterval cfInterval6;
        ConfidenceInterval cfInterval7;
        ConfidenceInterval cfInterval8;
        ConfidenceInterval cfInterval9;

        RandomSeedGenerator rsg = new RandomSeedGenerator();
        for (i = 0; i < NUMRUNS; i++) sds[i] = new Seeds(rsg);

        double t0Time = 0.0;
        double tfTime = 180.0;
        double[] customerSatisfactionValues1 = new double[NUMRUNS];
        double[] customerSatisfactionValues2 = new double[NUMRUNS];
        double[] customerSatisfactionValues3 = new double[NUMRUNS];
        double[] customerSatisfactionValues4 = new double[NUMRUNS];
        double[] customerSatisfactionValues5 = new double[NUMRUNS];
        double[] customerSatisfactionValues6 = new double[NUMRUNS];
        double[] customerSatisfactionValues7 = new double[NUMRUNS];
        double[] customerSatisfactionValues8 = new double[NUMRUNS];
        double[] customerSatisfactionValues9 = new double[NUMRUNS];

        System.out.println("Step 1A: 3 MAKE-TABLE EMPLOYEES, 605 IN2 OVEN AND 4 DRIVERS");
        for (i = 0; i < NUMRUNS; i++) {
            model = new SMPizza(t0Time, tfTime, 3, 4, 605, sds[i], logging);
            model.runSimulation();
            customerSatisfactionValues1[i] = model.getCustomerSatisfaction();
        }
        System.out.println("Step 1B: 3 MAKE-TABLE EMPLOYEES, 605 IN2 OVEN AND 3 DRIVERS");
        for (i = 0; i < NUMRUNS; i++) {
            model = new SMPizza(t0Time, tfTime, 3, 3, 605, sds[i], logging);
            model.runSimulation();
            customerSatisfactionValues2[i] = model.getCustomerSatisfaction();
        }

        System.out.println("Step 1C: 3 MAKE-TABLE EMPLOYEES, 605 IN2 OVEN AND 2 DRIVERS");
        for (i = 0; i < NUMRUNS; i++) {
            model = new SMPizza(t0Time, tfTime, 3, 2, 605, sds[i], logging);
            model.runSimulation();
            customerSatisfactionValues3[i] = model.getCustomerSatisfaction();
        }

        System.out.println("Step 1D: 3 MAKE-TABLE EMPLOYEES, 605 IN2 OVEN AND 1 DRIVERS");
        for (i = 0; i < NUMRUNS; i++) {
            model = new SMPizza(t0Time, tfTime, 3, 1, 605, sds[i], logging);
            model.runSimulation();
            customerSatisfactionValues4[i] = model.getCustomerSatisfaction();
        }

        System.out.println("Step 2A: 3 MAKE-TABLE EMPLOYEES, 605 IN2 OVEN AND 3 DRIVERS");
        for (i = 0; i < NUMRUNS; i++) {
            model = new SMPizza(t0Time, tfTime, 3, 3, 605, sds[i], logging);
            model.runSimulation();
            customerSatisfactionValues5[i] = model.getCustomerSatisfaction();
        }

        System.out.println("Step 2B: 3 MAKE-TABLE EMPLOYEES, 520 IN2 OVEN AND 3 DRIVERS");
        for (i = 0; i < NUMRUNS; i++) {
            model = new SMPizza(t0Time, tfTime, 3, 3, 520, sds[i], logging);
            model.runSimulation();
            customerSatisfactionValues6[i] = model.getCustomerSatisfaction();
        }

        System.out.println("Step 2C: 3 MAKE-TABLE EMPLOYEES, 435 IN2 OVEN AND 3 DRIVERS");
        for (i = 0; i < NUMRUNS; i++) {
            model = new SMPizza(t0Time, tfTime, 3, 3, 435, sds[i], logging);
            model.runSimulation();
            customerSatisfactionValues7[i] = model.getCustomerSatisfaction();
        }

        System.out.println("Step 3A: 2 MAKE-TABLE EMPLOYEES, 435 IN2 OVEN AND 3 DRIVERS");
        for (i = 0; i < NUMRUNS; i++) {
            model = new SMPizza(t0Time, tfTime, 2, 3, 435, sds[i], logging);
            model.runSimulation();
            customerSatisfactionValues8[i] = model.getCustomerSatisfaction();
        }

        System.out.println("Step 3B: 1 MAKE-TABLE EMPLOYEES, 435 IN2 OVEN AND 3 DRIVERS");
        for (i = 0; i < NUMRUNS; i++) {
            model = new SMPizza(t0Time, tfTime, 1, 3, 435, sds[i], logging);
            model.runSimulation();
            customerSatisfactionValues9[i] = model.getCustomerSatisfaction();
        }

        cfInterval1 = new ConfidenceInterval(customerSatisfactionValues1, CONF_LEVEL);
        cfInterval2 = new ConfidenceInterval(customerSatisfactionValues2, CONF_LEVEL);
        cfInterval3 = new ConfidenceInterval(customerSatisfactionValues3, CONF_LEVEL);
        cfInterval4 = new ConfidenceInterval(customerSatisfactionValues4, CONF_LEVEL);
        cfInterval5 = new ConfidenceInterval(customerSatisfactionValues5, CONF_LEVEL);
        cfInterval6 = new ConfidenceInterval(customerSatisfactionValues6, CONF_LEVEL);
        cfInterval7 = new ConfidenceInterval(customerSatisfactionValues7, CONF_LEVEL);
        cfInterval8 = new ConfidenceInterval(customerSatisfactionValues8, CONF_LEVEL);
        cfInterval9 = new ConfidenceInterval(customerSatisfactionValues9, CONF_LEVEL);

        /*------------ Display the resulting confidence intervals --------------*/
        // Header
        System.out.print("\nRun     Step 1A    Step 1B   Step 1C   Step 1D   Step 2A    Step 2B     Step 3A     Step 3B\n");
        System.out.print("----------------------------------------------------------------------------------------------------\n");
        // Simulation values
        for(i = 0; i < customerSatisfactionValues1.length; i++)
            System.out.printf("%5d %8.3f  %8.3f  %8.3f "
                            + " %8.3f %8.3f %8.3f"
                            + " %8.3f %8.3f %8.3f\n",
                    i+1, customerSatisfactionValues1[i], customerSatisfactionValues2[i], customerSatisfactionValues3[i],
                    customerSatisfactionValues4[i], customerSatisfactionValues5[i], customerSatisfactionValues6[i],
                    customerSatisfactionValues7[i], customerSatisfactionValues8[i], customerSatisfactionValues9[i]);
        // Confidence intervals
        System.out.print("----------------------------------------------------------------------------------------------------\n");
        System.out.printf("   PE %8.3f  %8.3f  %8.3f" +
                        "  %8.3f %8.3f %8.3f" +
                        " %8.3f %8.3f %8.3f\n",
                cfInterval1.getPointEstimate(), cfInterval2.getPointEstimate(), cfInterval3.getPointEstimate(),
                cfInterval4.getPointEstimate(), cfInterval5.getPointEstimate(), cfInterval6.getPointEstimate(),
                cfInterval7.getPointEstimate(), cfInterval8.getPointEstimate(), cfInterval9.getPointEstimate());
        System.out.printf("  S(n)%8.3f  %8.3f  %8.3f" +
                        "  %8.3f %8.3f %8.3f" +
                        " %8.3f %8.3f %8.3f\n",
                cfInterval1.getStdDev(), cfInterval2.getStdDev(), cfInterval3.getStdDev(),
                cfInterval4.getStdDev(), cfInterval5.getStdDev(), cfInterval6.getStdDev(),
                cfInterval7.getStdDev(), cfInterval8.getStdDev(), cfInterval9.getStdDev());
        System.out.printf("  zeta%8.3f  %8.3f  %8.3f" +
                        "  %8.3f %8.3f %8.3f" +
                        " %8.3f %8.3f %8.3f\n",
                cfInterval1.getZeta(), cfInterval2.getZeta(), cfInterval3.getZeta(),
                cfInterval4.getZeta(), cfInterval5.getZeta(), cfInterval6.getZeta(),
                cfInterval7.getZeta(), cfInterval8.getZeta(), cfInterval9.getZeta());
        System.out.printf(" CI Min%7.3f  %8.3f  %8.3f " +
                        "  %7.3f %8.3f %8.3f" +
                        " %8.3f %8.3f %8.3f\n",
                cfInterval1.getCfMin(), cfInterval2.getCfMin(), cfInterval3.getCfMin(),
                cfInterval4.getCfMin(), cfInterval5.getCfMin(), cfInterval6.getCfMin(),
                cfInterval7.getCfMin(), cfInterval8.getCfMin(), cfInterval9.getCfMin());
        System.out.printf(" CI Max%7.3f  %8.3f  %8.3f " +
                        "  %7.3f %8.3f %8.3f" +
                        " %8.3f %8.3f %8.3f\n",
                cfInterval1.getCfMax(), cfInterval2.getCfMax(), cfInterval3.getCfMax(),
                cfInterval4.getCfMax(), cfInterval5.getCfMax(), cfInterval6.getCfMax(),
                cfInterval7.getCfMax(), cfInterval8.getCfMax(), cfInterval9.getCfMax());
        System.out.printf(" zeta/PE%6.3f  %8.3f  %8.3f" +
                        "  %8.3f %8.3f %8.3f" +
                        " %8.3f %8.3f %8.3f\n",
                cfInterval1.getZeta()/cfInterval1.getPointEstimate(), cfInterval2.getZeta()/cfInterval2.getPointEstimate(),
                cfInterval3.getZeta()/cfInterval3.getPointEstimate(),cfInterval4.getZeta()/cfInterval4.getPointEstimate(),
                cfInterval5.getZeta()/cfInterval5.getPointEstimate(),cfInterval6.getZeta()/cfInterval6.getPointEstimate(),cfInterval7.getZeta()/cfInterval7.getPointEstimate(),
                cfInterval8.getZeta()/cfInterval8.getPointEstimate(),cfInterval9.getZeta()/cfInterval9.getPointEstimate());
//        System.out.printf("zeta/PE %8.3f %8.3f\n",  cfInterval1.getZeta()/cfInterval1.getPointEstimate(),
//                cfInterval2.getZeta()/cfInterval2.getPointEstimate());
//        System.out.printf("------------------------\n");
    }
}
