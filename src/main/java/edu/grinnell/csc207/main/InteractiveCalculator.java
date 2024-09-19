package edu.grinnell.csc207.main;

import edu.grinnell.csc207.util.CommandExecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * This class represents a calculator that takes commands through a REPL.
 *
 * @author David William Stroud
 */
public class InteractiveCalculator {
  /**
   * Starts the interactive calculator.
   * @param args The command-line arguments.
   * @throws IOException Can throw an IOException if there is an error reading stdin.
   */
  public static void main(String[] args) throws IOException {
    PrintWriter pen = new PrintWriter(System.out);
    CommandExecutor executor = new CommandExecutor();
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

    String command = null;
    do {
      if (command != null) {
        try {
          executor.execute(command, false, pen);
        } catch (Exception e) {
          System.err.println("Error: Error while executing command " + command);
          e.printStackTrace();
        } // try-catch
      } // if

      pen.print("> ");
      pen.flush();

      command = stdin.readLine();
    } while (command != null); // do-while
  } // main(String[])
} // class InteractiveCalculator
