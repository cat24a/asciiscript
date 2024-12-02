package asciiscript.value;

import java.util.List;

public class ArrayValue implements IndexableValue {

	List<Value> data;

	@Override
	public double toFloat() {
		return data.size();
	}
	
	@Override
	public boolean toBool() {
		return data.size() != 0;
	}
	
	@Override
	public String toString() {
		String result = "";
		for (Value value : data) {
			result += value.toString();
			result += ",";
		}
		if (result.length() != 0) {
			result = result.substring(0, result.length()-1);
			return result;
		} else {
			return "<empty>";
		}
	}

	@Override
	public boolean eq(Value other) {
		if (!(other instanceof ArrayValue)) return false;
		List<Value> otherData = ((ArrayValue)other).data;
		if(data.size() != otherData.size()) return false;
		for (int i = 0; i < data.size(); i++) {
			if(!data.get(i).eq(otherData.get(i))) return false;
		}
		return true;
	}

	@Override
	public Value at(Value i) {
		return data.get((int)i.toFloat()%data.size());
	}

	@Override
	public int length() {
		return data.size();
	}
	
	public void append(Value value) {
		data.add(value);
	}

	public void pop() {
		data.removeLast();
	}

	public void set(Value i, Value v) {
		data.set((int)i.toFloat(), v);
	}
}
