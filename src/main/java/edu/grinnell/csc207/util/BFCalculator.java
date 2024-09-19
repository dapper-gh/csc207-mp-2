package edu.grinnell.csc207.util;
/**
 * This class represents a calculator.
 *
 * @author David William Stroud
 */
public class BFCalculator {
  /**
   * The most recently computed value stored in this calculator.
   */
  private BigFraction lastValue;

  /**
   * Creates a BFCalculator with the most recently computed value set to 0.
   */
  public BFCalculator() {
    this.lastValue = BigFraction.ZERO;
  } // BFCalculator()

  /**
   * Creates a BFCalculator with the most recently computed value set to lastValue1.
   *
   * @param lastValue1 The most recently computed value to use.
   */
  public BFCalculator(BigFraction lastValue1) {
    this.lastValue = lastValue1;
  } // BFCalculator(BigFraction)

  /**
   * Gets the most recently computed value.
   * @return The most recently value.
   */
  public BigFraction get() {
    return this.lastValue;
  } // get()

  /**
   * Adds val to the most recently computed value.
   * @param val The fraction to which to add to the most recently computed value.
   */
  public void add(BigFraction val) {
    this.lastValue = this.lastValue.add(val);
  } // add(BigFraction)

  /**
   * Subtracts val from the most recently computed value.
   * @param val The fraction by which to subtract the most recently computed value.
   */
  public void subtract(BigFraction val) {
    this.lastValue = this.lastValue.subtract(val);
  } // subtract(BigFraction)

  /**
   * Multiplies the most recently computed value by val.
   * @param val The fraction by which to multiply the most recently computed value.
   */
  public void multiply(BigFraction val) {
    this.lastValue = this.lastValue.multiply(val);
  } // multiply(BigFraction)

  /**
   * Divides the most recently computed value by val.
   * @param val The fraction by which to divide the most recently computed value.
   */
  public void divide(BigFraction val) {
    this.lastValue = this.lastValue.divide(val);
  } // divide(BigFraction)

  /**
   * Resets the most recently computed value to zero.
   */
  public void clear() {
    this.lastValue = BigFraction.ZERO;
  } // clear()
} // class BFCalculator
