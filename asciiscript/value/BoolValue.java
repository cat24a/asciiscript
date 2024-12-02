package asciiscript.value;

public class BoolValue implements Value {

	public static final BoolValue yes = new BoolValue(true);
	public static final BoolValue no = new BoolValue(false);

	public static BoolValue of(boolean value) {
		return value ? yes : no;
	}

	private BoolValue(boolean value) {
		data = value;
	}

	boolean data;

	@Override
	public String toString() {
		return data ? "yes" : "no";
	}

	@Override
	public double toFloat() {
		return data ? 1 : 0;
	}

	@Override
	public boolean toBool() {
		return data;
	}

	@Override
	public boolean eq(Value other) {
		return data == other.toBool();
	}
	
}
