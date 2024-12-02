from dataclasses import dataclass
from tokens import Token, TokenType, Instr, InstructionType
from enum import Enum

@dataclass
class OpData:
	fun: str
	precedence: int
	left_assoc: bool

class SEType(Enum):
	FUN = 0
	OP = 1
	LB = 2

@dataclass
class StackEntry:
	type: SEType
	data: OpData | str | None = None


operators = {
	"set": OpData("_set", 0, True),
	"∧": OpData("_and", 1, True),
	"∨": OpData("_or", 1, True),
	"⊕": OpData("_xor", 1, True),
	"=": OpData("_eq", 3, True),
	"≠": OpData("_neq", 3, True),
	"<": OpData("_lt", 3, True),
	"≤": OpData("_le", 3, True),
	">": OpData("_gt", 3, True),
	"≥": OpData("_ge", 3, True),
	"—": OpData("_strjoin", 4, True),
	"+": OpData("_add", 5, True),
	"-": OpData("_subtract", 5, True),
	"×": OpData("_multiply", 6, True),
	"÷": OpData("_divide", 6, True),
	"^": OpData("_power", 7, False),
	"[]": OpData("_idx", 10, True),
}

def op2instr(op):
	if op.type == SEType.OP:
		return Instr(InstructionType.FUN, op.data.fun)
	elif op.type == SEType.FUN:
		return Instr(InstructionType.FUN, op.data)

def shunting_yard(tokens: list[Token]) -> list[Instr]:
	out = []
	stack = []
	for token in tokens:
		if token.type == TokenType.FLOAT:
			out.append(Instr(InstructionType.FLOAT, token.value))
		elif token.type == TokenType.STR:
			out.append(Instr(InstructionType.STR, token.value))
		elif token.type == TokenType.VAR:
			out.append(Instr(InstructionType.VAR, token.value))
		elif token.type == TokenType.FUN:
			stack.append(StackEntry(SEType.FUN, token.value))
		elif token.type == TokenType.OP:
			o1 = operators[token.value]
			while stack and stack[-1].type == SEType.OP and (stack[-1].data.precedence > o1.precedence or (stack[-1].data.precedence == o1.precedence and o1.left_assoc)):
				out.append(op2instr(stack.pop()))
			stack.append(StackEntry(SEType.OP, o1))
		elif token.type == TokenType.COMMA:
			while stack[-1].type == SEType.OP:
				out.append(op2instr(stack.pop()))
		elif token.type == TokenType.PAR_O:
			stack.append(StackEntry(SEType.LB))
		elif token.type == TokenType.PAR_C:
			while stack[-1].type == SEType.OP:
				out.append(op2instr(stack.pop()))
			stack.pop()
			if stack and stack[-1].type == SEType.FUN:
				out.append(op2instr(stack.pop()))
	
	for entry in stack:
		out.append(op2instr(entry))

	return out

if __name__ == "__main__":
	from tokenizer import tokenize
	from sys import stdin
	src = stdin.read()
	tokens = tokenize(src)
	instrs = shunting_yard(tokens)
	print(*instrs, sep="\n")