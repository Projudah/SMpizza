package smPizzaModel;

public class Order {
	enum Type { CARRYOUT, DELIVERY};
	protected Type uType;
	public int uNumPizzasStarted;
	public int uNumPizzasCompleted;
	public int uNumPizzas;
	public double startTime;
}
