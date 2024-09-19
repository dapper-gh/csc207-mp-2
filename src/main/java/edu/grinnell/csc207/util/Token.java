package edu.grinnell.csc207.util;
/**
 * This class represents a token in a calculator expression.
 *
 * @author David William Stroud
 */
public class Token {
  /**
   * A class that represents the possible types of tokens.
   *
   * @author David William Stroud
   */
  public enum TokenType {
    /**
     * The token is a fraction.
     */
    FRACTION,
    /**
     * The token is a register.
     */
    REGISTER,
    /**
     * The token is an add operation.
     */
    ADD,
    /**
     * The token is a subtraction operation.
     */
    SUBTRACT,
    /**
     * The token is a multiplication operation.
     */
    MULTIPLY,
    /**
     * The token is a division operation.
     */
    DIVIDE;
  } // enum TokenType

  /**
   * The type of this token.
   */
  private final TokenType type;
  /**
   * The parsed value of this token, if any.
   */
  private final Object value;

  private Token(BigFraction value1) {
    this.type = Token.TokenType.FRACTION;
    this.value = value1;
  } // Token(BigFraction)

  private Token(char value1) {
    this.type = Token.TokenType.REGISTER;
    this.value = value1;
  } // Token(char)

  private Token(Token.TokenType type1) {
    this.type = type1;
    this.value = null;
  } // Token(Token.TokenType)

  private static Token parseSingle(String word) throws NumberFormatException {
    if (word.equals("+")) {
      return new Token(Token.TokenType.ADD);
    } else if (word.equals("-")) {
      return new Token(Token.TokenType.SUBTRACT);
    } else if (word.equals("*")) {
      return new Token(Token.TokenType.MULTIPLY);
    } else if (word.equals("/")) {
      return new Token(Token.TokenType.DIVIDE);
    } else if (word.length() == 1 && word.charAt(0) <= 'z' && word.charAt(0) >= 'a') {
      return new Token(word.charAt(0));
    } else {
      return new Token(new BigFraction(word));
    } // if-else
  } // parseSingle(String)

  /**
   * Parse a line into an array of tokens.
   * @param line The line to be parsed.
   * @return The array of tokens in the line.
   */
  public static Token[] parse(String line) throws NumberFormatException {
    String[] words = line.split(" ");

    Token[] tokens = new Token[words.length];
    for (int i = 0; i < words.length; i++) {
      tokens[i] = Token.parseSingle(words[i]);
    } // for

    return tokens;
  } // parse(String)

  /**
   * Returns this token as a fraction, converting from a register if needed.
   * @param registers The register set to use if the token is a register.
   * @return This token as a fraction, or null if this token is not numeric.
   */
  public BigFraction getAsFraction(BFRegisterSet registers) {
    if (this.getType() == Token.TokenType.FRACTION) {
      return (BigFraction) this.value;
    } else if (this.getType() == Token.TokenType.REGISTER) {
      return registers.get((char) this.value);
    } else {
      return null;
    } // if-else chain
  } // getAsFraction(BFRegisterSet)

  /**
   * Returns the type of this token.
   * @return The type of this token.
   */
  public Token.TokenType getType() {
    return this.type;
  } // getType()

  /**
   * Returns whether this token is an operator.
   * @return Whether this token is an operator.
   */
  public boolean isOperator() {
    return this.getType() == Token.TokenType.ADD
        || this.getType() == Token.TokenType.SUBTRACT
        || this.getType() == Token.TokenType.MULTIPLY
        || this.getType() == Token.TokenType.DIVIDE;
  } // isOperator()

  /**
   * Returns whether this token is numeric.
   * @return Whether this token is numeric.
   */
  public boolean isNumeric() {
    return this.getType() == Token.TokenType.FRACTION
        || this.getType() == Token.TokenType.REGISTER;
  } // isNumeric()

  /**
   * Turns this token into a string.
   * @return The stringified version of this token.
   */
  public String toString() {
    if (this.isNumeric()) {
      return this.value.toString();
    } else if (this.getType() == Token.TokenType.ADD) {
      return "+";
    } else if (this.getType() == Token.TokenType.SUBTRACT) {
      return "-";
    } else if (this.getType() == Token.TokenType.MULTIPLY) {
      return "*";
    } else if (this.getType() == Token.TokenType.DIVIDE) {
      return "/";
    } else {
      return "";
    } // else-if chain
  } // toString()
} // class Token
