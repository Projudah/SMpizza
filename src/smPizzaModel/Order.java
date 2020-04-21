package smPizzaModel;

public class Order {
	enum Type { CARRYOUT, DELIVERY };
	protected int numPizzas;
	protected Type uType;
	protected int uNumPizzasStarted;
	protected int uNumPizzasCompleted;
}
