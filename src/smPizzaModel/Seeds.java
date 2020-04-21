package smPizzaModel;

import cern.jet.random.engine.RandomSeedGenerator;

public class Seeds {
	// Order arrivals seed
	int arr;

	// Cutting and Boxing seed type
	int cb;

	// Type of order seed
	int ot;

	// Number of pizzas per order seed
	int np;

	// Size of pizza seed
	int sp;

	// Triangular distribution seed
	int tri;

	public Seeds(RandomSeedGenerator rsg) {
		arr =rsg.nextSeed();
		cb  =rsg.nextSeed();
		ot  =rsg.nextSeed();
		np  =rsg.nextSeed();
		sp  = rsg.nextSeed();
		tri = rsg.nextSeed();
	}
}
