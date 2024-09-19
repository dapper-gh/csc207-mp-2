package edu.grinnell.csc207.main;

import edu.grinnell.csc207.util.CommandExecutor;

import java.io.PrintWriter;

/**
 * This class represents a calculator that takes commands through command-line arguments.
 *
 * @author David William Stroud
 */
public class QuickCalculator {
  /**
   * Starts the quick calculator.
   * @param args The command-line arguments.
   */
  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out);
    CommandExecutor executor = new CommandExecutor();
    for (String arg : args) {
      try {
        executor.execute(arg, true, pen);
      } catch (Exception err) {
        System.err.println("Error: Executing command " + arg);
        err.printStackTrace();
      } // try-catch
    } // for
  } // main(String[])
} // QuickCalculator
