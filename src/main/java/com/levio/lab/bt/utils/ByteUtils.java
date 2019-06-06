package com.levio.lab.bt.utils;

public class ByteUtils {

  public static String byteToBinaryString(byte[] bytes, int i) {
    String bitArray =
        String.format("%8s", Integer.toBinaryString(bytes[i])).replace(' ', '0');

    if (bitArray.length() != 8) {
      bitArray = bitArray.substring(24);
    }

    return bitArray;
  }

  public static String byteToBinaryString(byte b) {
    String bitArray =
        String.format("%8s", Integer.toBinaryString(b)).replace(' ', '0');

    if (bitArray.length() != 8) {
      bitArray = bitArray.substring(24);
    }

    return bitArray;
  }

  public static boolean binaryCharToBoolean(char bit) {
    if (bit == '0') {
      return false;
    } else if (bit == '1') {
      return true;
    } else {
      throw new IllegalArgumentException("Char is not a binary number");
    }
  }

  public static float SFLOAT16Parser(byte b1, byte b2) {
    String rawBits = byteToBinaryString(b2) + byteToBinaryString(b1);
    String exponent = rawBits.substring(0, 4);
    exponent = new StringBuilder(exponent).reverse().toString();
    String mantissa = rawBits.substring(4, 16);
    int mantissaValue = Integer.parseInt(mantissa, 2);
    int exponentValue = Integer.parseInt(exponent.substring(1), 2);

    if (exponent.charAt(0) == '1') {
      exponentValue = -exponentValue;
    }

    return (float) (mantissaValue * (Math.pow(10, exponentValue)));
  }
}
