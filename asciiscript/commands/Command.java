package asciiscript.commands;

import asciiscript.Env;

public interface Command {
	public void run(Env.Scope c);
}
