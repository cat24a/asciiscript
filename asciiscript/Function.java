package asciiscript;

import java.util.AbstractMap.SimpleEntry;

import asciiscript.Env.Scope;
import asciiscript.value.ArrayValue;
import asciiscript.value.BoolValue;
import asciiscript.value.FloatValue;
import asciiscript.value.IndexableValue;
import asciiscript.value.StringValue;
import asciiscript.value.Value;

import java.util.Map;

public interface Function {
	public void run(Scope c);

	public static void println(Scope c) {
		System.out.println(c.pop());
	}

	public static void set(Scope c) {
		Value value = c.pop();
		String name = c.pop().toString();
		c.setVar(name, value);
	}

	public static void _if(Scope c) {
		String if_fun = c.pop().toString();
		if(c.pop().toBool()) {
			c.call(if_fun);
		}
	}

	public static void ie(Scope c) {
		String else_fun = c.pop().toString();
		String if_fun = c.pop().toString();
		if(c.pop().toBool()) {
			c.call(if_fun);
		} else {
			c.call(else_fun);
		}
	}

	public static void iv(Scope c) {
		Value else_v = c.pop();
		Value if_v = c.pop();
		c.push(c.pop().toBool() ? if_v : else_v);
	}

	public static void dowhile(Scope c) {
		String fun = c.pop().toString();
		do {
			c.call(fun);
		} while(c.pop().toBool());
	}

	public static void _while(Scope c) {
		String fun = c.pop().toString();
		String cond = c.pop().toString();
		c.call(cond);
		while(c.pop().toBool()) {
			c.call(fun);
			c.call(cond);
		}
	}

	public static void array(Scope c) {
		c.push(new ArrayValue());
	}

	public static void len(Scope c) {
		c.push(new FloatValue(((IndexableValue)c.pop()).length()));
	}

	public static void append(Scope c) {
		Value v = c.pop();
		ArrayValue arr = (ArrayValue)c.pop();
		arr.append(v);
	}

	public static void pop(Scope c) {
		ArrayValue arr = (ArrayValue)c.pop();
		arr.pop();
	}

	public static void aset(Scope c) {
		Value value = c.pop();
		Value i = c.pop();
		ArrayValue array = (ArrayValue)c.pop();
		array.set(i, value);
	}

	public static void not(Scope c) {
		c.push(BoolValue.of(!c.pop().toBool()));
	}

	public static Map<String, Function> defaultFunctions = Map.<String, Function>ofEntries(
		new SimpleEntry<>("_println", Function::println),
		new SimpleEntry<>("_set", Function::set),
		new SimpleEntry<>("_add", new BinaryOperator((a, b)->new FloatValue(a.toFloat() + b.toFloat()))),
		new SimpleEntry<>("_subtract", new BinaryOperator((a, b)->new FloatValue(a.toFloat() - b.toFloat()))),
		new SimpleEntry<>("_multiply", new BinaryOperator((a, b)->new FloatValue(a.toFloat() * b.toFloat()))),
		new SimpleEntry<>("_divide", new BinaryOperator((a, b)->new FloatValue(a.toFloat() / b.toFloat()))),
		new SimpleEntry<>("_power", new BinaryOperator((a, b)->new FloatValue(Math.pow(a.toFloat(), b.toFloat())))),
		new SimpleEntry<>("_eq", new BinaryOperator((a, b)->BoolValue.of(a.eq(b)))),
		new SimpleEntry<>("_neq", new BinaryOperator((a, b)->BoolValue.of(!a.eq(b)))),
		new SimpleEntry<>("_lt", new BinaryOperator((a, b)->BoolValue.of(a.toFloat() < b.toFloat()))),
		new SimpleEntry<>("_le", new BinaryOperator((a, b)->BoolValue.of(a.toFloat() <= b.toFloat()))),
		new SimpleEntry<>("_gt", new BinaryOperator((a, b)->BoolValue.of(a.toFloat() > b.toFloat()))),
		new SimpleEntry<>("_ge", new BinaryOperator((a, b)->BoolValue.of(a.toFloat() >= b.toFloat()))),
		new SimpleEntry<>("_and", new BinaryOperator((a, b)->BoolValue.of(a.toBool() && b.toBool()))),
		new SimpleEntry<>("_or", new BinaryOperator((a, b)->BoolValue.of(a.toBool() || b.toBool()))),
		new SimpleEntry<>("_xor", new BinaryOperator((a, b)->BoolValue.of(a.toBool() ^ b.toBool()))),
		new SimpleEntry<>("_strjoin", new BinaryOperator((a, b)->new StringValue(a.toString() + b.toString()))),
		new SimpleEntry<>("_idx", new BinaryOperator((a, b)->((IndexableValue)a).at(b))),
		new SimpleEntry<>("_if", Function::_if),
		new SimpleEntry<>("_ie", Function::ie),
		new SimpleEntry<>("_iv", Function::iv),
		new SimpleEntry<>("_dowhile", Function::dowhile),
		new SimpleEntry<>("_while", Function::_while),
		new SimpleEntry<>("_array", Function::array),
		new SimpleEntry<>("_len", Function::len),
		new SimpleEntry<>("_append", Function::append),
		new SimpleEntry<>("_pop", Function::pop),
		new SimpleEntry<>("_aset", Function::aset),
		new SimpleEntry<>("_not", Function::not)
	);
}
