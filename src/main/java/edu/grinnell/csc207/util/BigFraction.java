package edu.grinnell.csc207.util;

import java.math.BigInteger;

/**
 * A simple implementation of arbitrary-precision Fractions.
 *
 * @author Samuel A. Rebelsky
 * @author David William Stroud
 * @author Moses Milenge
 */
public class BigFraction {
  // +------------------+---------------------------------------------
  // | Design Decisions |
  // +------------------+

  /*
   * (1) Denominators are always positive. Therefore, negative fractions
   * are represented with a negative numerator. Similarly, if a fraction
   * has a negative numerator, it is negative.
   *
   * (2) Fractions are not necessarily stored in simplified form. To
   * obtain a fraction in simplified form, one must call the `simplify`
   * method.
   */

  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * A BigFraction that is equal to 0.
   */
  public static final BigFraction ZERO = new BigFraction(0, 1);
  /**
   * A BigFraction that is equal to -1.
   */
  public static final BigFraction NEGATIVE_ONE = new BigFraction(-1, 1);

  // +--------+-------------------------------------------------------
  // | Fields |
  // +--------+

  /** The numerator of the fraction. Can be positive, zero or negative. */
  private BigInteger num;

  /** The denominator of the fraction. Must be non-negative. */
  private BigInteger denom;

  // +--------------+-------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a new fraction with numerator num and denominator denom.
   *
   * @param numerator
   *   The numerator of the fraction.
   * @param denominator
   *   The denominator of the fraction.
   */
  public BigFraction(BigInteger numerator, BigInteger denominator) {
    this.num = numerator;
    this.denom = denominator;

    this.simplify();
  } // BigFraction(BigInteger, BigInteger)

  /**
   * Build a new fraction with numerator num and denominator denom.
   *
   * @param numerator
   *   The numerator of the fraction.
   * @param denominator
   *   The denominator of the fraction.
   */
  public BigFraction(int numerator, int denominator) {
    this.num = BigInteger.valueOf(numerator);
    this.denom = BigInteger.valueOf(denominator);

    this.simplify();
  } // BigFraction(int, int)

  /**
   * Build a new fraction by parsing a string.
   *
   * @param str
   *   The fraction in string form
   */
  public BigFraction(String str) throws NumberFormatException {
    int slash = str.indexOf("/");

    if (slash == -1) {
      this.num = new BigInteger(str);
      this.denom = BigInteger.ONE;
    } else {
      this.num = new BigInteger(str.substring(0, slash));
      this.denom = new BigInteger(str.substring(slash + 1));
    } // if-else

    this.simplify();
  } // BigFraction

  // +---------+------------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Mutates this fraction such that it is in simplified form.
   */
  private void simplify() {
    BigInteger numerAbs = this.numerator().abs();
    BigInteger denomAbs = this.denominator().abs();

    BigInteger gcd = numerAbs.gcd(denomAbs);

    int numeratorSign = this.numerator().signum();
    if (numeratorSign == 0) {
      numeratorSign = 1;
    } // if
    int denominatorSign = this.denominator().signum();
    if (denominatorSign == 0) {
      denominatorSign = 1;
    } // if

    this.num = numerAbs.divide(gcd);
    this.denom = denomAbs.divide(gcd).multiply(BigInteger.valueOf(numeratorSign * denominatorSign));
  } // simplify()

  /**
   * Express this fraction as a double.
   *
   * @return The fraction approximated as a double.
   */
  public double doubleValue() {
    return this.num.doubleValue() / this.denom.doubleValue();
  } // doubleValue()

  /**
   * Add another faction to this fraction.
   *
   * @param addend
   *   The fraction to add.
   *
   * @return The result of the addition.
   */
  public BigFraction add(BigFraction addend) {
    BigInteger resultNumerator;
    BigInteger resultDenominator;

    // The denominator of the result is the product of this object's
    // denominator and addend's denominator
    resultDenominator = this.denom.multiply(addend.denom);
    // The numerator is more complicated
    resultNumerator =
        (this.num.multiply(addend.denom)).add(addend.num.multiply(this.denom));

    // Return the computed value
    return new BigFraction(resultNumerator, resultDenominator);
  } // add(BigFraction)

  /**
   * Get the denominator of this fraction.
   *
   * @return The denominator.
   */
  public BigInteger denominator() {
    return this.denom;
  } // denominator()

  /**
   * Get the numerator of this fraction.
   *
   * @return The numerator.
   */
  public BigInteger numerator() {
    return this.num;
  } // numerator()

  /**
   * Convert this fraction to a string for ease of printing.
   *
   * @return A string that represents the fraction.
   */
  public String toString() {
    BigInteger numerator;
    BigInteger denominator;
    if (this.denominator().signum() == -1) {
      numerator = this.numerator().negate();
      denominator = this.denominator().negate();
    } else {
      numerator = this.numerator();
      denominator = this.denominator();
    } // if-else

    // Special case: It's zero
    if (numerator.equals(BigInteger.ZERO)) {
      return "0";
    } // if it's zero

    // Special case: It's an integer
    if (denominator.equals(BigInteger.ONE)) {
      return numerator.toString();
    } // if

    // Lump together the string represention of the numerator,
    // a slash, and the string representation of the denominator
    return numerator + "/" + denominator;
  } // toString()

  /**
   * Multiply this fraction by another fraction.
   *
   * @param other The fraction to multiply with this fraction.
   *
   * @return The multiplied fraction.
   */
  public BigFraction multiply(BigFraction other) {
    return new BigFraction(
        this.numerator().multiply(other.numerator()),
        this.denominator().multiply(other.denominator())
    );
  } // multiply(BigFraction)

  /**
   * Returns the fractional portion of this fraction as a mixed number.
   *
   * @return The fractional portion.
   */
  public BigFraction fractional() {
    return new BigFraction(
        this.numerator()
            .subtract(
                this.numerator()
                    .divide(this.denominator())
                    .multiply(this.denominator())
            ),
        this.denominator()
    );
  } // fractional()

  /**
   * Returns the negated form of this fraction.
   * @return The negated form.
   */
  public BigFraction negate() {
    return this.multiply(BigFraction.NEGATIVE_ONE);
  } // negate()

  /**
   * Returns this fraction minus the other fraction.
   * @param other The fraction to be subtracted from this fraction.
   * @return The result of the subtraction.
   */
  public BigFraction subtract(BigFraction other) {
    return this.add(other.negate());
  } // subtract(BigFraction)

  /**
   * Returns the reciprocal of this fraction.
   * @return The reciprocal of this fraction.
   */
  public BigFraction reciprocal() {
    return new BigFraction(this.denominator(), this.numerator());
  } // reciprocal()

  /**
   * Divides this fraction by another fraction.
   * @param other The fraction by which to divide this fraction.
   * @return The new fraction.
   */
  public BigFraction divide(BigFraction other) {
    return this.multiply(other.reciprocal());
  } // divide(BigFraction)
} // class BigFraction
