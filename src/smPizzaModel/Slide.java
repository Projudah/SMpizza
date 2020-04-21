package smPizzaModel;

import java.util.ArrayList;

public class Slide {

	protected int n;
	protected ArrayList<Pizza> slide;

	protected int getN(){ return slide.size();}

	protected void spInsertQue(Pizza pizza) { slide.add(pizza); }

}
