from tokens import Instr, InstructionType
from struct import pack

def encode_fun(code: list[Instr]) -> bytes:
	data = bytearray()
	for i in code:
		data.append(i.type.value)
		if i.type == InstructionType.FLOAT:
			data.extend(pack("<d", i.data))
		elif i.type in (InstructionType.STR, InstructionType.VAR, InstructionType.FUN):
			strdata = i.data.encode()
			data.extend(pack("<H", len(strdata)))
			data.extend(strdata)
	return data

def encode(functions: dict[str, tuple[list[str], list[Instr]]]):
	data = bytearray()
	for name, (params, code) in functions.items():
		strdata = name.encode()
		data.extend(pack("<H", len(strdata)))
		data.extend(strdata)
		data.extend(pack("<H", len(params)))
		for param in params:
			strdata = param.encode()
			data.extend(pack("<H", len(strdata)))
			data.extend(strdata)
		data.extend(encode_fun(code))
		data.append(127)
	return data

if __name__ == "__main__":
	from tokenizer import tokenize
	from shunting_yard import shunting_yard
	from sys import stdin
	print(encode_fun(shunting_yard(tokenize(stdin.read()))))