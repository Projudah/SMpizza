package smPizzaModel;

public class Pizza {
	enum Size {
		SMALL(115), MEDIUM(175), LARGE(250);

		private final int value;

		Size(final int newValue) {
			value = newValue;
		}

		public int getValue() {
			return value;
		}
	}

	protected int size;
	protected Order associatedOrder;

	public String toString() {
		return "PIZZA";
	}
}
