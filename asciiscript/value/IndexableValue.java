package asciiscript.value;

public interface IndexableValue extends Value {
	Value at(Value i);
	int length();
}
