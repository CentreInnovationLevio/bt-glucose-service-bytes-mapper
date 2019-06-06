package com.levio.lab.bt.mappers;

import static com.levio.lab.bt.utils.ByteUtils.SFLOAT16Parser;
import static com.levio.lab.bt.utils.ByteUtils.binaryCharToBoolean;
import static com.levio.lab.bt.utils.ByteUtils.byteToBinaryString;

import com.levio.lab.bt.services.glucose.measurementcontext.GlucoseMeasurementContext;
import com.levio.lab.bt.services.glucose.measurementcontext.GlucoseMeasurementContextFlags;

class GlucoseMeasurementContextBytesMapper {

  static GlucoseMeasurementContext bytesToGlucoseMeasurementContext(byte[] bytes) {
    GlucoseMeasurementContext glucoseMeasurementContext = new GlucoseMeasurementContext();
    int bytePosition = 3;

    try {
      setFlagsContextFromRawData(bytes, glucoseMeasurementContext);
      setSequenceNumberFromRawData(bytes, glucoseMeasurementContext);
      bytePosition = skipExtendedFlagsIfAllocated(glucoseMeasurementContext, bytePosition);
      bytePosition = setCarbohydrateIdFromRawData(bytes, glucoseMeasurementContext, bytePosition);
      bytePosition = setMealFromRawData(bytes, glucoseMeasurementContext, bytePosition);
      bytePosition =
          setTesterAndHealthFromRawData(bytes, glucoseMeasurementContext, bytePosition);
      bytePosition = setExerciceDurationAndIntensityFromRawData(bytes, glucoseMeasurementContext,
          bytePosition);
      bytePosition = setMedicationFromRawData(bytes, glucoseMeasurementContext, bytePosition);
      setHbA1cFromRawData(bytes, glucoseMeasurementContext, bytePosition);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return glucoseMeasurementContext;
  }


  private static int skipExtendedFlagsIfAllocated(
      GlucoseMeasurementContext glucoseMeasurementContext,
      int bytePosition) {
    if (glucoseMeasurementContext.getFlags().isExtendedFlagsPresent()) {
      bytePosition++;
    }
    return bytePosition;
  }

  private static void setHbA1cFromRawData(byte[] bytes,
      GlucoseMeasurementContext glucoseMeasurementContext, int bytePosition) {
    if (glucoseMeasurementContext.getFlags().isHba1CPresent()) {

      glucoseMeasurementContext
          .setHbA1c(SFLOAT16Parser(bytes[bytePosition], bytes[bytePosition + 1]));
    }
  }


  private static int setMedicationFromRawData(byte[] bytes,
      GlucoseMeasurementContext glucoseMeasurementContext, int bytePosition) {
    if (glucoseMeasurementContext.getFlags().isMedicationIdAndMedicationPresent()) {

      String rawBits = byteToBinaryString(bytes, bytePosition);
      int bitsValue = Integer.parseInt(rawBits, 2);

      switch (bitsValue) {
        case 0:
          glucoseMeasurementContext.setMedicationId("Reserved for future use");
          break;
        case 1:
          glucoseMeasurementContext.setMedicationId("Rapid acting insulin");
          break;
        case 2:
          glucoseMeasurementContext.setMedicationId("Short acting insulin");
          break;
        case 3:
          glucoseMeasurementContext.setMedicationId("Intermediate acting insulin");
          break;
        case 4:
          glucoseMeasurementContext.setMedicationId("Long acting insulin");
          break;
        case 5:
          glucoseMeasurementContext.setMedicationId("Pre-mixed insulin");
          break;
        default:
          break;
      }
      bytePosition++;

      if (glucoseMeasurementContext.getFlags().isMedicationValueUnits()) {
        glucoseMeasurementContext.setMedicationUnits("Units of liters");
      } else {
        glucoseMeasurementContext.setMedicationUnits("Units of kilograms");
      }

      glucoseMeasurementContext
          .setMedicationQuantity(SFLOAT16Parser(bytes[bytePosition], bytes[bytePosition + 1]));

      bytePosition = bytePosition + 2;
    }
    return bytePosition;
  }


  private static int setExerciceDurationAndIntensityFromRawData(
      byte[] bytes,
      GlucoseMeasurementContext glucoseMeasurementContext, int bytePosition) {
    if (glucoseMeasurementContext.getFlags().isExerciseDurationAndExerciseIntensityPresent()) {
      String rawBitsForExerciceDuration = byteToBinaryString(bytes, bytePosition + 1)
          + byteToBinaryString(bytes, bytePosition);
      String rawBitsForExerciceIntensity = byteToBinaryString(bytes, bytePosition + 2);
      glucoseMeasurementContext
          .setExerciceDuration(Integer.parseInt(rawBitsForExerciceDuration, 2));
      glucoseMeasurementContext
          .setExerciceIntensity(Integer.parseInt(rawBitsForExerciceIntensity, 2));
      bytePosition = bytePosition + 3;
    }
    return bytePosition;
  }


  private static int setTesterAndHealthFromRawData(byte[] bytes,
      GlucoseMeasurementContext glucoseMeasurementContext, int bytePosition) {
    if (glucoseMeasurementContext.getFlags().isTesterHealthPresent()) {
      String rawBits = byteToBinaryString(bytes, bytePosition);
      int firstNibbleBits = Integer.parseInt(rawBits.substring(0, 4), 2);
      int secondNibbleBits = Integer.parseInt(rawBits.substring(4, 8), 2);

      switch (firstNibbleBits) {
        case 0:
          glucoseMeasurementContext.setTester("Reserved for future use");
          break;
        case 1:
          glucoseMeasurementContext.setTester("Self");
          break;
        case 2:
          glucoseMeasurementContext.setTester("Health Care Professional");
          break;
        case 3:
          glucoseMeasurementContext.setTester("Lab Test");
          break;
        case 15:
          glucoseMeasurementContext.setTester("Tester value not available");
          break;
        default:
          break;
      }

      switch (secondNibbleBits) {
        case 0:
          glucoseMeasurementContext.setHealth("Reserved for future use");
          break;
        case 1:
          glucoseMeasurementContext.setHealth("Minor health issues");
          break;
        case 2:
          glucoseMeasurementContext.setHealth("Major health issues");
          break;
        case 3:
          glucoseMeasurementContext.setHealth("During Menses");
          break;
        case 4:
          glucoseMeasurementContext.setHealth("Under stress");
          break;
        case 5:
          glucoseMeasurementContext.setHealth("No health issues");
          break;
        case 15:
          glucoseMeasurementContext.setTester("Health value not available");
          break;
        default:
          break;
      }
      bytePosition++;
    }
    return bytePosition;
  }


  private static int setMealFromRawData(byte[] bytes,
      GlucoseMeasurementContext glucoseMeasurementContext, int bytePosition) {
    if (glucoseMeasurementContext.getFlags().isMealPresent()) {
      String rawBits = byteToBinaryString(bytes, bytePosition);
      int bitsValue = Integer.parseInt(rawBits, 2);

      switch (bitsValue) {
        case 0:
          glucoseMeasurementContext.setMeal("Reserved for future use");
          break;
        case 1:
          glucoseMeasurementContext.setMeal("Preprendial (before meal)");
          break;
        case 2:
          glucoseMeasurementContext.setMeal("Postprendial (after meal)");
          break;
        case 3:
          glucoseMeasurementContext.setMeal("Fasting");
          break;
        case 4:
          glucoseMeasurementContext.setMeal("Casual (snacks, drinks, etc.)");
          break;
        case 5:
          glucoseMeasurementContext.setMeal("Bedtime");
          break;
        default:
          break;
      }
      bytePosition++;
    }
    return bytePosition;
  }


  private static int setCarbohydrateIdFromRawData(byte[] bytes,
      GlucoseMeasurementContext glucoseMeasurementContext, int bytePosition) {
    if (glucoseMeasurementContext.getFlags().isCarbohydrateIdAndCarbohydratePresent()) {
      String rawBits = byteToBinaryString(bytes, bytePosition);
      int bitsValue = Integer.parseInt(rawBits, 2);

      switch (bitsValue) {
        case 0:
          glucoseMeasurementContext.setCarbohydrateId("Reserved for future use");
          break;
        case 1:
          glucoseMeasurementContext.setCarbohydrateId("Breakfast");
          break;
        case 2:
          glucoseMeasurementContext.setCarbohydrateId("Lunch");
          break;
        case 3:
          glucoseMeasurementContext.setCarbohydrateId("Dinner");
          break;
        case 4:
          glucoseMeasurementContext.setCarbohydrateId("Snack");
          break;
        case 5:
          glucoseMeasurementContext.setCarbohydrateId("Drink");
          break;
        case 6:
          glucoseMeasurementContext.setCarbohydrateId("Supper");
          break;
        case 7:
          glucoseMeasurementContext.setCarbohydrateId("Brunch");
          break;
        default:
          break;
      }
      bytePosition++;

      glucoseMeasurementContext.setCarbohydrateUnits("Units of kilograms");

      glucoseMeasurementContext
          .setCarbohydrateQuantity(SFLOAT16Parser(bytes[bytePosition], bytes[bytePosition + 1]));

      bytePosition = bytePosition + 2;

    }
    return bytePosition;
  }


  private static void setSequenceNumberFromRawData(byte[] bytes,
      GlucoseMeasurementContext glucoseMeasurementContext) {
    String rawSequenceNumberBits =
        byteToBinaryString(bytes, 2) + byteToBinaryString(bytes, 1);
    glucoseMeasurementContext.setSequenceNumber(Integer.parseInt(rawSequenceNumberBits, 2));
  }


  private static void setFlagsContextFromRawData(byte[] bytes,
      GlucoseMeasurementContext glucoseMeasurementContext) {
    GlucoseMeasurementContextFlags flags = new GlucoseMeasurementContextFlags();
    String rawFlagsBits = byteToBinaryString(bytes, 0);
    rawFlagsBits = new StringBuilder(rawFlagsBits).reverse().toString();
    int bitCount = 0;

    for (char bit : rawFlagsBits.toCharArray()) {
      switch (bitCount) {
        case 0:
          flags.setCarbohydrateIdAndCarbohydratePresent(binaryCharToBoolean(bit));
          break;
        case 1:
          flags.setMealPresent(binaryCharToBoolean(bit));
          break;
        case 2:
          flags.setTesterHealthPresent(binaryCharToBoolean(bit));
          break;
        case 3:
          flags.setExerciseDurationAndExerciseIntensityPresent(binaryCharToBoolean(bit));
          break;
        case 4:
          flags.setMedicationIdAndMedicationPresent(binaryCharToBoolean(bit));
          break;
        case 5:
          flags.setMedicationValueUnits(binaryCharToBoolean(bit));
          break;
        case 6:
          flags.setHba1CPresent(binaryCharToBoolean(bit));
          break;
        case 7:
          flags.setExtendedFlagsPresent(binaryCharToBoolean(bit));
          break;
        default:
          break;
      }
      bitCount++;
    }
    glucoseMeasurementContext.setFlags(flags);
  }
}
