from tokens import Token, TokenType

class TokenizationException(Exception):
	pass

class InvalidCharacterException(Exception):
	pass

def tokenize(src: str):
	tokens = [Token(TokenType.PAR_O)]
	src = list(src)

	def shift(amount = 1):
		nonlocal src
		src = src[amount:]

	brackets = 0

	while len(src) > 0:
		if   src[0] in " \t":
			shift()
		elif src[0] == "(":
			tokens.append(Token(TokenType.PAR_O))
			brackets += 1
			shift()
		elif src[0] in ")]":
			tokens.append(Token(TokenType.PAR_C))
			brackets -= 1
			shift()
		elif src[0] in "+-×÷^≠<≤>≥∧∨⊕—":
			tokens.append(Token(TokenType.OP, src[0]))
			shift()
		elif src[0] == "=":
			if brackets:
				tokens.append(Token(TokenType.OP, "="))
			else:
				tokens[-1] = Token(TokenType.STR, tokens[-1].value)
				tokens.append(Token(TokenType.OP, "set"))
			shift()
		elif src[0] == "\n":
			tokens.append(Token(TokenType.PAR_C))
			tokens.append(Token(TokenType.PAR_O))
			shift()
		elif src[0] == ",":
			tokens.append(Token(TokenType.COMMA))
			shift()
		elif src[0] == "„":
			shift()
			amount = src.index("”")
			data = "".join(src[:amount])
			tokens.append(Token(TokenType.STR, data))
			shift(amount+1)
		elif src[0] == "[":
			tokens.append(Token(TokenType.OP, "[]"))
			tokens.append(Token(TokenType.PAR_O))
			brackets += 1
			shift()
		elif src[0] == "¬":
			tokens.append(Token(TokenType.FUN, "_not"))
			shift()
		else:
			value = ""
			while src and src[0] in "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM_.":
				value += src[0]
				shift()
			if not value:
				raise InvalidCharacterException(f"Invalid character: {src[0]}")
			
			if src[0] == "!":
				tokens.append(Token(TokenType.FUN, value))
				shift()
				continue
			
			try:
				tokens.append(Token(TokenType.FLOAT, float(value)))
			except ValueError:
				tokens.append(Token(TokenType.VAR, value))

	tokens.append(Token(TokenType.PAR_C))

	return tokens


if __name__ == "__main__":
	from sys import stdin
	src = stdin.read()

	print(tokenize(src))