package asciiscript;

import java.util.Hashtable;
import java.util.Stack;

import asciiscript.value.Value;

public class Env {
	private Stack<Value> stack = new Stack<>();
	private Hashtable<String, Value> globals = new Hashtable<>();
	private Hashtable<String, Function> functions;

	public Env(Hashtable<String, Function> functions, Hashtable<String, Value> globals) {
		this.functions = functions;
		this.globals = globals;
	}

	public class Scope {
		private Hashtable<String, Value> locals = new Hashtable<>();

		public Value push(Value v) {
			return stack.push(v);
		}

		public Value pop() {
			return stack.pop();
		}

		public Value getVar(String name) {
			Value value = locals.get(name);
			if(value == null) {
				value = globals.get(name);
			}
			return value;
		}

		public Value setVar(String name, Value value) {
			if(name.startsWith("."))
				return globals.put(name, value);
			else
				return locals.put(name, value);
		}

		public void call(String fun) {
			functions.get(fun).run(this);
		}

		public Scope newScope() {
			return new Scope();
		}
	}
}
