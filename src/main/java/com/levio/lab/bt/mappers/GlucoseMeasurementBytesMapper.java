package com.levio.lab.bt.mappers;

import static com.levio.lab.bt.utils.ByteUtils.binaryCharToBoolean;
import static com.levio.lab.bt.utils.ByteUtils.byteToBinaryString;

import com.levio.lab.bt.services.glucose.measurement.GlucoseMeasurement;
import com.levio.lab.bt.services.glucose.measurement.GlucoseMeasurementFlags;
import com.levio.lab.bt.services.glucose.measurement.SensorStatusAnnunciation;

class GlucoseMeasurementBytesMapper {

  static GlucoseMeasurement bytesToGlucoseMeasurement(byte[] bytes) {
    GlucoseMeasurement glucoseMeasurement = new GlucoseMeasurement();
    try {
      GlucoseMeasurementFlags flags = new GlucoseMeasurementFlags();
      SensorStatusAnnunciation sensorStatusAnnunciation = new SensorStatusAnnunciation();

      setFlagsFromRawData(bytes, flags);
      setSequenceNumberFromRawData(bytes, glucoseMeasurement);
      setYearFromRawData(bytes, glucoseMeasurement);
      setMonthFromRawData(bytes, glucoseMeasurement);
      setDayFromRawData(bytes, glucoseMeasurement);
      setHourFromRawData(bytes, glucoseMeasurement);
      setMinutesFromRawData(bytes, glucoseMeasurement);
      setSecondsFromRawData(bytes, glucoseMeasurement);
      setSensorStatusAnnunciationFromRawData(bytes, sensorStatusAnnunciation);
      setSampleLocationFromRawData(bytes, glucoseMeasurement);
      setTimeOffsetFromRawData(bytes, glucoseMeasurement);
      setGlucoseConcentrationValueFromRawData(bytes, glucoseMeasurement);
      setTypeFromRawData(bytes, glucoseMeasurement);

      glucoseMeasurement.setSensorStatusAnnunciation(sensorStatusAnnunciation);
      flags.setGlucoseConcentrationUnit();
      glucoseMeasurement.setFlags(flags);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return glucoseMeasurement;
  }

  private static void setTypeFromRawData(byte[] bytes,
      GlucoseMeasurement glucoseMeasurement) {
    String rawSampleLocationBits = byteToBinaryString(bytes, 14).substring(4, 8);
    int value = Integer.parseInt(rawSampleLocationBits, 2);
    switch (value) {
      case 0:
        glucoseMeasurement.setType("Reserved for future use");
        break;
      case 1:
        glucoseMeasurement.setType("Capillary Whole blood");
        break;
      case 2:
        glucoseMeasurement.setType("Capillary Plasma");
        break;
      case 3:
        glucoseMeasurement.setType("Venous Whole blood");
        break;
      case 4:
        glucoseMeasurement.setType("Venous Plasma");
        break;
      case 5:
        glucoseMeasurement.setType("Arterial Whole blood");
        break;
      case 6:
        glucoseMeasurement.setType("Arterial Plasma");
        break;
      case 7:
        glucoseMeasurement.setType("Undetermined Whole blood");
        break;
      case 8:
        glucoseMeasurement.setType("Undetermined Plasma");
        break;
      case 9:
        glucoseMeasurement.setType("Interstitial Fluid (ISF)");
        break;
      case 10:
        glucoseMeasurement.setType("Control Solution");
        break;
      default:
        break;
    }
  }

  private static void setSampleLocationFromRawData(byte[] bytes,
      GlucoseMeasurement glucoseMeasurement) {
    String rawSampleLocationBits = byteToBinaryString(bytes, 14).substring(0, 4);
    int value = Integer.parseInt(rawSampleLocationBits, 2);

    switch (value) {
      case 0:
        glucoseMeasurement.setSampleLocation("Reserved for future use");
        break;
      case 1:
        glucoseMeasurement.setSampleLocation("Finger");
        break;
      case 2:
        glucoseMeasurement.setSampleLocation("Alternate Site Test (AST)");
        break;
      case 3:
        glucoseMeasurement.setSampleLocation("Earlobe");
        break;
      case 4:
        glucoseMeasurement.setSampleLocation("Control solution");
        break;
      case 15:
        glucoseMeasurement.setSampleLocation("Sample Location value not available");
        break;
      default:
        break;
    }
  }

  private static void setSensorStatusAnnunciationFromRawData(byte[] bytes,
      SensorStatusAnnunciation sensorStatusAnnunciation) {
    String rawSensorStatusAnnunciationByte1 = byteToBinaryString(bytes, 15);
    String rawSensorStatusAnnunciationByte2 = byteToBinaryString(bytes, 16);
    rawSensorStatusAnnunciationByte1 =
        new StringBuilder(rawSensorStatusAnnunciationByte1).reverse().toString();
    rawSensorStatusAnnunciationByte2 =
        new StringBuilder(rawSensorStatusAnnunciationByte2).reverse().toString();
    int bitCount = 0;

    for (char bit : rawSensorStatusAnnunciationByte1.toCharArray()) {
      switch (bitCount) {
        case 0:
          sensorStatusAnnunciation
              .setDeviceBatteryLowAtTimeOfMeasurement(binaryCharToBoolean(bit));
          break;
        case 1:
          sensorStatusAnnunciation
              .setSensorMalfunctionOrFaultingAtTimeOfMeasurement(binaryCharToBoolean(bit));
          break;
        case 2:
          sensorStatusAnnunciation
              .setSampleSizeForBloodOrControlSolutionInsufficientAtTimeOfMeasurement(
                  binaryCharToBoolean(bit));
          break;
        case 3:
          sensorStatusAnnunciation.setStripInsertionError(binaryCharToBoolean(bit));
          break;
        case 4:
          sensorStatusAnnunciation.setStripTypeIncorrectForDevice(binaryCharToBoolean(bit));
          break;
        case 5:
          sensorStatusAnnunciation
              .setSensorResultHigherThanTheDeviceCanProcess(binaryCharToBoolean(bit));
          break;
        case 6:
          sensorStatusAnnunciation
              .setSensorResultLowerThanTheDeviceCanProcess(binaryCharToBoolean(bit));
          break;
        case 7:
          sensorStatusAnnunciation
              .setSensorTemperatureTooHighForValidTestResultAtTimeOfMeasurement(
                  binaryCharToBoolean(bit));
          break;
        default:
          break;
      }
      bitCount++;
    }

    bitCount = 0;

    for (char bit : rawSensorStatusAnnunciationByte2.toCharArray()) {
      switch (bitCount) {
        case 0:
          sensorStatusAnnunciation
              .setSensorTemperatureTooLowForValidTestResultAtTimeOfMeasurement(
                  binaryCharToBoolean(bit));
          break;
        case 1:
          sensorStatusAnnunciation
              .setSensorReadInterruptedBecauseStripWasPulledTooSoonAtTimeOfMeasurement(
                  binaryCharToBoolean(bit));
          break;
        case 2:
          sensorStatusAnnunciation
              .setGeneralDeviceFaultHasOccurredInTheSensor(binaryCharToBoolean(bit));
          break;
        case 3:
          sensorStatusAnnunciation.setTimeFaultHasOccurredInTheSensorAndTimeMayBeInaccurate(
              binaryCharToBoolean(bit));
          break;
        default:
          break;
      }
      bitCount++;
    }
  }

  private static void setGlucoseConcentrationValueFromRawData(byte[] bytes,
      GlucoseMeasurement glucoseMeasurement) {
    String exponent = byteToBinaryString(bytes, 13).substring(0, 4);
    exponent = new StringBuilder(exponent).reverse().toString();
    String mantissa = byteToBinaryString(bytes, 13).substring(4, 8)
        + byteToBinaryString(bytes, 12);
    int mantissaValue = Integer.parseInt(mantissa, 2);
    int exponentValue = Integer.parseInt(exponent.substring(1), 2);

    if (exponent.charAt(0) == '1') {
      exponentValue = -exponentValue;
    }

    glucoseMeasurement
        .setGlucoseConcentration((float) (mantissaValue * (Math.pow(10, exponentValue))));
  }

  private static void setSecondsFromRawData(byte[] bytes,
      GlucoseMeasurement glucoseMeasurement) {
    String rawSequenceNumberBits = byteToBinaryString(bytes, 9);
    int seconds = Integer.parseInt(rawSequenceNumberBits, 2);
    glucoseMeasurement.setSecond(seconds);
  }

  private static void setMinutesFromRawData(byte[] bytes,
      GlucoseMeasurement glucoseMeasurement) {
    String rawSequenceNumberBits = byteToBinaryString(bytes, 8);
    int minutes = Integer.parseInt(rawSequenceNumberBits, 2);
    glucoseMeasurement.setMinute(minutes);
  }

  private static void setHourFromRawData(byte[] bytes,
      GlucoseMeasurement glucoseMeasurement) {
    String rawSequenceNumberBits = byteToBinaryString(bytes, 7);
    int hour = Integer.parseInt(rawSequenceNumberBits, 2);
    glucoseMeasurement.setHour(hour);
  }

  private static void setDayFromRawData(byte[] bytes,
      GlucoseMeasurement glucoseMeasurement) {
    String rawSequenceNumberBits = byteToBinaryString(bytes, 6);
    int day = Integer.parseInt(rawSequenceNumberBits, 2);
    glucoseMeasurement.setDay(day);
  }

  private static void setMonthFromRawData(byte[] bytes,
      GlucoseMeasurement glucoseMeasurement) {
    String rawSequenceNumberBits = byteToBinaryString(bytes, 5);
    int month = Integer.parseInt(rawSequenceNumberBits, 2);
    glucoseMeasurement.setMonth(month);
  }

  private static void setYearFromRawData(byte[] bytes,
      GlucoseMeasurement glucoseMeasurement) {
    String rawSequenceNumberBits =
        byteToBinaryString(bytes, 4) + byteToBinaryString(bytes, 3);
    int year = Integer.parseInt(rawSequenceNumberBits, 2);
    glucoseMeasurement.setYear(year);
  }

  private static void setTimeOffsetFromRawData(byte[] bytes,
      GlucoseMeasurement glucoseMeasurement) {
    String rawSequenceNumberBits =
        byteToBinaryString(bytes, 11) + byteToBinaryString(bytes, 10);
    int timeOffset = Integer.parseInt(rawSequenceNumberBits, 2);
    glucoseMeasurement.setTimeOffset(timeOffset);
  }

  private static void setSequenceNumberFromRawData(byte[] bytes,
      GlucoseMeasurement glucoseMeasurement) {
    String rawSequenceNumberBits =
        byteToBinaryString(bytes, 2) + byteToBinaryString(bytes, 1);
    glucoseMeasurement.setSequenceNumber(Integer.parseInt(rawSequenceNumberBits, 2));
  }

  private static void setFlagsFromRawData(byte[] bytes, GlucoseMeasurementFlags flags) {
    String rawFlagsBits = byteToBinaryString(bytes, 0);
    rawFlagsBits = new StringBuilder(rawFlagsBits).reverse().toString();
    int bitCount = 0;

    for (char bit : rawFlagsBits.toCharArray()) {
      switch (bitCount) {
        case 0:
          flags.setTimeOffsetPresent(binaryCharToBoolean(bit));
          break;
        case 1:
          flags.setGlucoseConcentrationTypeSamplePresent(binaryCharToBoolean(bit));
          break;
        case 2:
          flags.setGlucoseConcentrationUnitFlag(binaryCharToBoolean(bit));
          break;
        case 3:
          flags.setSensorStatusAnnunciationPresent(binaryCharToBoolean(bit));
          break;
        case 4:
          flags.setContextInformationsFollows(binaryCharToBoolean(bit));
          break;
        default:
          break;
      }
      bitCount++;
    }
  }
}
