package smPizzaModel;

import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.Uniform;

class RVPs 
{
	static SMPizza model; // for accessing the clock
    // Data Models - i.e. random veriate generators for distributions
	// are created using Colt classes, define 
	// reference variables here and create the objects in the
	// constructor with seeds

	private static Uniform triangularDist;


	// Constructor
	protected RVPs(Seeds sd) 
	{ 
	// Set up distribution functions
		triangularDist = new Uniform(new MersenneTwister(sd.tri));
	}

	static double triangularDistribution(final double min, final double mode, final double max) {
		final double F = (max - min) / (mode - min);
		final double rand = triangularDist.nextDouble();
		if (rand < F) {
			return min + Math.sqrt(rand * (mode - min) * (max - min));
		} else {
			return mode - Math.sqrt((1 - rand) * (mode - min) * (mode - max));
		}
	}

}
