package edu.grinnell.csc207.util;

import java.io.PrintWriter;

/**
 * This class executes calculator commands.
 *
 * @author David William Stroud
 */
public class CommandExecutor {
  /**
   * The prefix for the STORE command.
   */
  private static final String STORE_PREFIX = "STORE ";
  /**
   * The text for the QUIT command.
   */
  private static final String QUIT_COMMAND = "QUIT";

  /**
   * The registers for this set of commands.
   */
  private final BFRegisterSet registers = new BFRegisterSet();
  /**
   * The calculator for this set of commands,
   * which will be replaced at the start of every expression.
   */
  private BFCalculator calculator = null;

  /**
   * Creates a CommandExecutor.
   */
  public CommandExecutor() { } // CommandExecutor()


  /**
   * Applies the operation represented by token on first and second.
   * @param token The token reperesenting the operation.
   * @param second The second fraction in the operation.
   */
  private void applyOperation(Token token, BigFraction second) {
    if (token.getType() == Token.TokenType.ADD) {
      this.calculator.add(second);
    } else if (token.getType() == Token.TokenType.SUBTRACT) {
      this.calculator.subtract(second);
    } else if (token.getType() == Token.TokenType.MULTIPLY) {
      this.calculator.multiply(second);
    } else if (token.getType() == Token.TokenType.DIVIDE) {
      this.calculator.divide(second);
    } // if-else chain
  } // applyOperation(Token, BigFraction)

  /**
   * Executes a command.
   * @param command The command to be executed.
   * @param printCommand Whether to print the command before the command output.
   * @param pen The PrintWriter to use to print output, if needed.
   */
  public void execute(String command, boolean printCommand, PrintWriter pen) {
    String trimmed = command.trim();

    if (trimmed.equals(CommandExecutor.QUIT_COMMAND)) {
      System.exit(0);
    } else if (trimmed.startsWith(CommandExecutor.STORE_PREFIX)) {
      String remaining = trimmed.substring(CommandExecutor.STORE_PREFIX.length());
      if (remaining.length() != 1) {
        System.err.println("Error: STORE command not in format STORE <register>.");
        return;
      } // if

      char register = remaining.charAt(0);
      if (register < 'a' || register > 'z') {
        System.err.println("Error: Invalid register in STORE command.");
        return;
      } // if

      this.registers.store(register, this.calculator.get());
    } else {
      Token[] tokens;
      try {
        tokens = Token.parse(command);
      } catch (NumberFormatException err) {
        System.err.println("Error: Expected number but found non-number.");
        return;
      } // try-catch

      this.calculator = null;
      for (int i = 0; i < tokens.length; i++) {
        Token token = tokens[i];
        if (this.calculator == null) {
          if (token.isNumeric()) {
            this.calculator = new BFCalculator(token.getAsFraction(this.registers));
            continue;
          } else {
            System.err.println("Error: First token is not numeric.");
            return;
          } // if-else
        } // if

        if (token.isOperator()) {
          if ((i + 1) >= tokens.length) {
            System.err.println("Error: Command ended mid-instruction.");
            return;
          } // if
          Token nextToken = tokens[++i];
          if (nextToken.isNumeric()) {
            this.applyOperation(token, nextToken.getAsFraction(this.registers));
          } else {
            System.err.println("Error: Non-numeric token given when numeric token was expected.");
            return;
          } // if-else
        } else {
          System.err.println("Error: Non-operator token given when operator token was expected.");
          return;
        } // if-else
      } // for
      if (this.calculator == null) {
        System.err.println("Error: Empty command given.");
      } else {
        pen.println((printCommand ? (trimmed + " = ") : "")
            + this.calculator.get().toString());
        pen.flush();
      } // if-else chain
    } // if-else chain
  } // execute(String)
} // class CommandExecutor
