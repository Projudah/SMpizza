// File: Experiment.java
// Description:

import cern.jet.random.engine.*;
import smPizzaModel.*;

// Main Method: Experiments
// 
class Experiment
{
   public static void main(String[] args)
   {
       int i, NUMRUNS = 1; 
       double startTime=0.0, endTime=660.0;
       Seeds[] sds = new Seeds[NUMRUNS];
       SMPizza mname;  // Simulation object

       // Lets get a set of uncorrelated seeds
       RandomSeedGenerator rsg = new RandomSeedGenerator();
       for(i=0 ; i<NUMRUNS ; i++) sds[i] = new Seeds(rsg);
       
       // Loop for NUMRUN simulation runs for each case
       // Case 1
       System.out.println(" Case 1");
       for(i=0 ; i < NUMRUNS ; i++)
       {
          mname = new SMPizza(startTime, endTime, 1, 1, 435, sds[i], true);
          mname.runSimulation();
          // See examples for hints on collecting output
          // and developping code for analysis
       }
   }
}
