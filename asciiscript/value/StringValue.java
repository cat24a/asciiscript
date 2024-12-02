package asciiscript.value;

public class StringValue implements IndexableValue {

	String data;

	public StringValue(String data) {
		this.data = data;
	}

	@Override
	public double toFloat() {
		return Double.parseDouble(data);
	}

	@Override
	public String toString() {
		return data;
	}

	@Override
	public boolean toBool() {
		return data.length() != 0;
	}

	@Override
	public boolean eq(Value other) {
		return data.equals(other.toString());
	}

	@Override
	public Value at(Value i) {
		return new StringValue(Character.toString(data.charAt((int)i.toFloat())));
	}

	@Override
	public int length() {
		return data.length();
	}
	
}
