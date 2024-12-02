from tokenizer import tokenize
from shunting_yard import shunting_yard
from encoder import encode
from sys import argv

def main():
	with open(argv[1], "r") as f:
		code = f.read()
	functions = split_functions(code)
	encoded = encode(functions)
	with open(argv[2], "wb") as f:
		f.write(encoded)

def split_functions(code: str):
	functions = {}
	lines = code.splitlines(True)
	while not lines[0].startswith("λ"): lines.pop(0)
	while lines:
		info = lines.pop(0).split()
		code = ""
		while lines and not lines[0].startswith("λ"):
			code += lines.pop(0)
		compiled = shunting_yard(tokenize(code + " "))
		functions[info[0][1:]] = (info[1:], compiled)
	return functions

if __name__ == "__main__":
	main()