from typing import NamedTuple
from enum import Enum
from dataclasses import dataclass

class TokenType(Enum):
	VAR = 0
	FLOAT = 1
	OP = 2
	FUN = 3
	PAR_O = 4
	PAR_C = 5
	STR = 6
	COMMA = 7

class Token(NamedTuple):
	type: TokenType
	value: str|float|None = None

class InstructionType(Enum):
	FLOAT = 0
	STR = 1
	VAR = 2
	FUN = 3

@dataclass
class Instr:
	type: InstructionType
	data: str | float | None