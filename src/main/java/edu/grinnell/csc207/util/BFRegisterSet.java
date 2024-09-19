package edu.grinnell.csc207.util;

import java.util.Arrays;

/**
 * This class represents a set of registers for use in a calculator.
 *
 * @author David William Stroud
 */
public class BFRegisterSet {
  /**
   * A representation of the registers in this set as a 26-length array.
   */
  private final BigFraction[] registers = new BigFraction['z' - 'a' + 1];

  private static int charToInt(char val) {
    return val - 'a';
  } // charToInt(char)

  /**
   * Creates a register set with every register set to zero.
   */
  public BFRegisterSet() {
    Arrays.fill(this.registers, BigFraction.ZERO);
  } // BFRegisterSet()

  /**
   * Stores val into register.
   * @param register The register into which to store val, as a char from 'a' to 'z'.
   * @param val The value to store into the register.
   */
  public void store(char register, BigFraction val) {
    this.registers[BFRegisterSet.charToInt(register)] = val;
  } // store(char, BigFraction)

  /**
   * Retrieves the value stored in register.
   * @param register The register to retrieve, as a char from 'a' to 'z'.
   * @return The fraction inside that register.
   */
  public BigFraction get(char register) {
    return this.registers[BFRegisterSet.charToInt(register)];
  } // get(char)
} // class BFRegisterSet
