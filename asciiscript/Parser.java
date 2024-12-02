package asciiscript;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import asciiscript.commands.Command;
import asciiscript.commands.ExecFun;
import asciiscript.commands.ReadFloat;
import asciiscript.commands.ReadString;
import asciiscript.commands.ReadVar;
import asciiscript.util.IOUtil;

public class Parser {

	public static List<Command> parseFun(InputStream input) throws IOException {
		List<Command> commands = new ArrayList<>();
		while (true) {
			switch (IOUtil.readBytesOrWait(input, 1)[0]) {
				case 0:
					commands.add(new ReadFloat(Double.longBitsToDouble(IOUtil.byteArrayToLong(IOUtil.readBytesOrWait(input, 8)))));
					break;
				case 1:
					byte[] data = IOUtil.readDynamicSizeBytes(input);
					commands.add(new ReadString(new String(data)));
					break;
				case 2:
					data = IOUtil.readDynamicSizeBytes(input);
					commands.add(new ReadVar(new String(data)));
					break;
				case 3:
					data = IOUtil.readDynamicSizeBytes(input);
					commands.add(new ExecFun(new String(data)));
					break;
				case 127:
					return commands;
			}
		}
	}

	public static Hashtable<String, UserFunction> parse(InputStream is) throws IOException {
		Hashtable<String, UserFunction> functions = new Hashtable<>();
		while(true) {
			try {
				String name = new String(IOUtil.readDynamicSizeBytes(is));
				int argcount = IOUtil.byteArrayToInt(IOUtil.readBytesOrWait(is, 2));
				List<String> args = new ArrayList<>();
				for (int i = 0; i < argcount; i++) {
					args.add(new String(IOUtil.readDynamicSizeBytes(is)));
				}
				List<Command> commands = parseFun(is);
				functions.put(name, new UserFunction(args, commands));
			} catch (EOFException e) {
				break;
			}
		}
		return functions;
	}
}