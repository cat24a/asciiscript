package asciiscript;

import java.io.File;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

import asciiscript.value.StringValue;
import asciiscript.value.Value;

public class Main {
	public static void main(String[] args) throws IOException {
		Hashtable<String, Function> functions = new Hashtable<>();
		for (String arg : args) {
			try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(new File(arg)))) {
				functions.putAll(Parser.parse(input));
			}
		}
		Hashtable<String, Value> globals = new Hashtable<>();
		for (String function : functions.keySet()) {
			globals.put(function, new StringValue(function));
		}
		functions.putAll(Function.defaultFunctions);
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		functions.put("_readln", c->{
			try {
				c.push(new StringValue(stdin.readLine()));
			} catch(IOException e) {
				System.err.println("IOError while reading from stdin");
				e.printStackTrace();
				c.push(new StringValue(""));
			}
		});
		Env env = new Env(functions, globals);
		env.new Scope().call("_main");
	}
}
