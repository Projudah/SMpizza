package smPizzaModel;

public class Order {
	enum Type { CARRYOUT, DELIVERY};
	public Type uType;
	public int uNumPizzasStarted;
	public int uNumPizzasCompleted;
	public int uNumPizzas;
	public double startTime;
}
