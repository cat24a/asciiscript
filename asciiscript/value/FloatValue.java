package asciiscript.value;

public class FloatValue implements Value {

	double data;

	public FloatValue(double data) {
		this.data = data;
	}

	@Override
	public double toFloat() {
		return data;
	}

	@Override
	public String toString() {
		return Double.toString(data);
	}

	@Override
	public boolean toBool() {
		return data != 0;
	}

	@Override
	public boolean eq(Value other) {
		return data == other.toFloat();
	}
	
}
