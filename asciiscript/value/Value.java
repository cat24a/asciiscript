package asciiscript.value;

public interface Value {
	public double toFloat();
	public String toString();
	public boolean toBool();
	public boolean eq(Value other);
}
