package com.levio.lab.bt.mappers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.levio.lab.bt.services.glucose.feature.GlucoseFeature;
import org.junit.Before;
import org.junit.Test;

public class GlucoseFeatureBytesMapperTest {

  private GlucoseFeatureBytesMapper underTest;
  private GlucoseFeature glucoseFeatureToBeMapped;
  private byte[] someValidBytesArrayFilledWithOnes = {(byte) 255, (byte) 255};
  private byte[] someValidBytesArrayFilledWithZeros = {(byte) 0, (byte) 0};
  private byte[] someValidBytesArray = {(byte) 46, (byte) 11};

  @Before
  public void setup() {
    underTest = new GlucoseFeatureBytesMapper();
  }

  @Test
  public void givenValidArrayOfBytesFilledWithOnes_whenMappingBytesToGlucoseFeatureEntity_thenAllFieldsAreTrue() {
    glucoseFeatureToBeMapped = underTest.bytesToGlucoseFeature(someValidBytesArrayFilledWithOnes);

    assertTrue(glucoseFeatureToBeMapped.isLowBatteryDetectionDuringMeasurementSupported());
    assertTrue(glucoseFeatureToBeMapped.isSensorMalfunctionDetectionSupported());
    assertTrue(glucoseFeatureToBeMapped.isSensorSampleSizeSupported());
    assertTrue(glucoseFeatureToBeMapped.isSensorStripInsertionErrorDetectionSupported());
    assertTrue(glucoseFeatureToBeMapped.isSensorStripTypeErrorDetectionSupported());
    assertTrue(glucoseFeatureToBeMapped.isSensorResultHighLowDetectionSupported());
    assertTrue(glucoseFeatureToBeMapped.isSensorTemperatureHighLowDetectionSupported());
    assertTrue(glucoseFeatureToBeMapped.isSensorReadInterruptDetectionSupported());
    assertTrue(glucoseFeatureToBeMapped.isGeneralDeviceFaultSupported());
    assertTrue(glucoseFeatureToBeMapped.isTimeFaultSupported());
    assertTrue(glucoseFeatureToBeMapped.isMultipleBondSupported());
  }

  @Test
  public void givenValidArrayOfBytesFilledWithZeros_whenMappingBytesToGlucoseFeatureEntity_thenAllFieldsAreFalse() {
    glucoseFeatureToBeMapped = underTest.bytesToGlucoseFeature(someValidBytesArrayFilledWithZeros);

    assertFalse(glucoseFeatureToBeMapped.isLowBatteryDetectionDuringMeasurementSupported());
    assertFalse(glucoseFeatureToBeMapped.isSensorMalfunctionDetectionSupported());
    assertFalse(glucoseFeatureToBeMapped.isSensorSampleSizeSupported());
    assertFalse(glucoseFeatureToBeMapped.isSensorStripInsertionErrorDetectionSupported());
    assertFalse(glucoseFeatureToBeMapped.isSensorStripTypeErrorDetectionSupported());
    assertFalse(glucoseFeatureToBeMapped.isSensorResultHighLowDetectionSupported());
    assertFalse(glucoseFeatureToBeMapped.isSensorTemperatureHighLowDetectionSupported());
    assertFalse(glucoseFeatureToBeMapped.isSensorReadInterruptDetectionSupported());
    assertFalse(glucoseFeatureToBeMapped.isGeneralDeviceFaultSupported());
    assertFalse(glucoseFeatureToBeMapped.isTimeFaultSupported());
    assertFalse(glucoseFeatureToBeMapped.isMultipleBondSupported());
  }

  @Test
  public void givenValidArrayOfBytes_whenMappingBytesToGlucoseFeatureEntity_thenAllFieldsAreCorrectlyMapped() {
    glucoseFeatureToBeMapped = underTest.bytesToGlucoseFeature(someValidBytesArray);

    assertFalse(glucoseFeatureToBeMapped.isLowBatteryDetectionDuringMeasurementSupported());
    assertTrue(glucoseFeatureToBeMapped.isSensorMalfunctionDetectionSupported());
    assertTrue(glucoseFeatureToBeMapped.isSensorSampleSizeSupported());
    assertTrue(glucoseFeatureToBeMapped.isSensorStripInsertionErrorDetectionSupported());
    assertFalse(glucoseFeatureToBeMapped.isSensorStripTypeErrorDetectionSupported());
    assertTrue(glucoseFeatureToBeMapped.isSensorResultHighLowDetectionSupported());
    assertFalse(glucoseFeatureToBeMapped.isSensorTemperatureHighLowDetectionSupported());
    assertFalse(glucoseFeatureToBeMapped.isSensorReadInterruptDetectionSupported());
    assertTrue(glucoseFeatureToBeMapped.isGeneralDeviceFaultSupported());
    assertTrue(glucoseFeatureToBeMapped.isTimeFaultSupported());
    assertFalse(glucoseFeatureToBeMapped.isMultipleBondSupported());
  }

}
