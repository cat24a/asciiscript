package asciiscript.commands;

import asciiscript.Env;
import asciiscript.value.FloatValue;

public class ReadFloat implements Command {

	FloatValue data;

	public ReadFloat(double data) {
		this.data = new FloatValue(data);
	}

	@Override
	public void run(Env.Scope c) {
		c.push(data);
	}

}
