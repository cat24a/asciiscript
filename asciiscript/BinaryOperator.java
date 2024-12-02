package asciiscript;

import asciiscript.Env.Scope;
import asciiscript.value.Value;

public class BinaryOperator implements Function {
	public interface Doer {
		public Value run(Value a, Value b);
	}

	Doer doer;

	BinaryOperator(Doer doer) {
		this.doer = doer;
	}

	public void run(Scope c) {
		Value b = c.pop();
		Value a = c.pop();
		c.push(doer.run(a, b));
	}
}
