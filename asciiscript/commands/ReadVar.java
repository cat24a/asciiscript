package asciiscript.commands;

import asciiscript.Env;

public class ReadVar implements Command {

	String name;

	public ReadVar(String name) {
		this.name = name;
	}

	@Override
	public void run(Env.Scope c) {
		c.push(c.getVar(name));
	}

}
