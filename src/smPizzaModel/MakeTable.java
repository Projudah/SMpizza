package smPizzaModel;

import java.util.ArrayList;

public class MakeTable {
	static int POS1 = 0;
	static int POS2 = 1;
	static int POS3 = 2;
	static int POS4 = 3;
	static int POS5 = 4;
	protected int numBusy;
	protected int numPersons;
	protected Pizza[] position = new Pizza[5];
	protected boolean[] positionBusy = new boolean[5];
}
