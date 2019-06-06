package com.levio.lab.bt.mappers;

import static com.levio.lab.bt.utils.ByteUtils.binaryCharToBoolean;
import static com.levio.lab.bt.utils.ByteUtils.byteToBinaryString;

import com.levio.lab.bt.services.glucose.feature.GlucoseFeature;

class GlucoseFeatureBytesMapper {

  static GlucoseFeature bytesToGlucoseFeature(byte[] bytes) {
    GlucoseFeature glucoseFeature = new GlucoseFeature();

    setGlucoseFeatureFlags(bytes, glucoseFeature);
    return glucoseFeature;
  }

  private static void setGlucoseFeatureFlags(byte[] bytes, GlucoseFeature glucoseFeature) {
    String rawGlucoseFeatureByte1 = byteToBinaryString(bytes, 0);
    String rawGlucoseFeatureByte2 = byteToBinaryString(bytes, 1);
    rawGlucoseFeatureByte1 = new StringBuilder(rawGlucoseFeatureByte1).reverse().toString();
    rawGlucoseFeatureByte2 = new StringBuilder(rawGlucoseFeatureByte2).reverse().toString();

    int bitCount = 0;

    for (char bit : rawGlucoseFeatureByte1.toCharArray()) {
      switch (bitCount) {
        case 0:
          glucoseFeature
              .setLowBatteryDetectionDuringMeasurementSupported(binaryCharToBoolean(bit));
          break;
        case 1:
          glucoseFeature.setSensorMalfunctionDetectionSupported(binaryCharToBoolean(bit));
          break;
        case 2:
          glucoseFeature.setSensorSampleSizeSupported(binaryCharToBoolean(bit));
          break;
        case 3:
          glucoseFeature
              .setSensorStripInsertionErrorDetectionSupported(binaryCharToBoolean(bit));
          break;
        case 4:
          glucoseFeature.setSensorStripTypeErrorDetectionSupported(binaryCharToBoolean(bit));
          break;
        case 5:
          glucoseFeature.setSensorResultHighLowDetectionSupported(binaryCharToBoolean(bit));
          break;
        case 6:
          glucoseFeature
              .setSensorTemperatureHighLowDetectionSupported(binaryCharToBoolean(bit));
          break;
        case 7:
          glucoseFeature.setSensorReadInterruptDetectionSupported(binaryCharToBoolean(bit));
          break;
        default:
          break;
      }
      bitCount++;
    }

    bitCount = 0;

    for (char bit : rawGlucoseFeatureByte2.toCharArray()) {
      switch (bitCount) {
        case 0:
          glucoseFeature.setGeneralDeviceFaultSupported(binaryCharToBoolean(bit));
          break;
        case 1:
          glucoseFeature.setTimeFaultSupported(binaryCharToBoolean(bit));
          break;
        case 2:
          glucoseFeature.setMultipleBondSupported(binaryCharToBoolean(bit));
          break;
        default:
          break;
      }
      bitCount++;
    }
  }
}
