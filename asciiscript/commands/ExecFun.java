package asciiscript.commands;

import asciiscript.Env;

public class ExecFun implements Command {

	String name;

	public ExecFun(String name) {
		this.name = name;
	}

	@Override
	public void run(Env.Scope c) {
		c.call(name);
	}

}
