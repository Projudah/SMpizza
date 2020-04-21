package smPizzaModel;

public class Pizza {
	enum Size {
		SMALL(115),
		MEDIUM(175),
		LARGE(250);
		
		private final int value;

		Size(final int newValue) {
            value = newValue;
        }

        public int getValue() { return value; }
	}
	protected Size size;
	protected Order associatedOrder;
	public void spleave() {
		// TODO Auto-generated method stub
		
	}
}
