package asciiscript.commands;

import asciiscript.Env;
import asciiscript.value.StringValue;

public class ReadString implements Command {

	StringValue data;

	public ReadString(String data) {
		this.data = new StringValue(data);
	}

	@Override
	public void run(Env.Scope c) {
		c.push(data);
	}

}
