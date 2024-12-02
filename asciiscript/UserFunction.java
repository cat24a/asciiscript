package asciiscript;

import java.util.List;

import asciiscript.Env.Scope;
import asciiscript.commands.Command;

public class UserFunction implements Function {

	List<String> params;
	List<Command> commands;

	public UserFunction(List<String> params, List<Command> commands) {
		this.params = params;
		this.commands = commands;
	}

	@Override
	public void run(Scope old) {
		Scope c = old.newScope();
		for(String param : params.reversed()) {
			c.setVar(param, c.pop());
		}
		for(Command command : commands) {
			command.run(c);
		}
	}
	
}
