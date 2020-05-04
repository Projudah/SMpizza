// File: Experiment.java
// Description:

import cern.jet.random.engine.*;
import outputAnalysis.ConfidenceInterval;
import smPizzaModel.*;

// Main Method: Experiments
// 
class Experiment
{
   public static void main(String[] args)
   {
       int i, NUMRUNS = 20;
       double startTime=0.0, endTime=180;
       Seeds[] sds = new Seeds[NUMRUNS];
       SMPizza mname;  // Simulation object
       ConfidenceInterval cfInterval;
       final double CONF_LEVEL = 0.9;  // Confidence levels

       double [] customerSatisfactionValues = new double[NUMRUNS];

       // Lets get a set of uncorrelated seeds
       RandomSeedGenerator rsg = new RandomSeedGenerator();
       for(i=0 ; i<NUMRUNS ; i++) sds[i] = new Seeds(rsg);
       
       // Loop for NUMRUN simulation runs for each case
       // Case 1
       for(i=0 ; i < NUMRUNS ; i++)
       {
          System.out.println("\n Run "+(i+1));
          mname = new SMPizza(startTime, endTime, 2, 3, 435, sds[i], true);
          mname.runSimulation();
          customerSatisfactionValues[i] = mname.getCustomerSatisfaction();
       }

       cfInterval = new ConfidenceInterval(customerSatisfactionValues, CONF_LEVEL);
       /*------------ Display the resulting confidence intervals --------------*/
       // Header
       System.out.print("Run   | Customer Satisfaction\n");
       System.out.print("------------------------\n");
       // Simulation values
       for(i = 0; i < NUMRUNS; i++) {
           System.out.printf("%5d | %8.3f\n",i+1, customerSatisfactionValues[i]);
       }

       // Confidence intervals
       System.out.print("------------------------\n");
       System.out.printf("     PE  %8.3f\n", cfInterval.getPointEstimate());
       System.out.printf("   S(n)  %8.3f\n", cfInterval.getStdDev());
       System.out.printf("   zeta  %8.3f\n", cfInterval.getZeta());
       System.out.printf(" CI Min  %8.3f\n", cfInterval.getCfMin());
       System.out.printf(" CI Max  %8.3f\n", cfInterval.getCfMax());
       System.out.printf("zeta/PE  %8.3f\n",  cfInterval.getZeta()/cfInterval.getPointEstimate());
       System.out.print("------------------------\n");
   }
}
