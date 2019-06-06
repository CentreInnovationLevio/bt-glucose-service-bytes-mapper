package com.levio.lab.bt.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.levio.lab.bt.services.glucose.measurement.GlucoseMeasurement;
import org.junit.Before;
import org.junit.Test;

public class GlucoseMeasurementBytesMapperTest {

  private GlucoseMeasurementBytesMapper underTest;
  private GlucoseMeasurement glucoseMeasurementToBeMapped;
  private byte[] someValidBytesArrayFilledWithOnes = {(byte) 255, (byte) 255, (byte) 255,
      (byte) 255,
      (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255,
      (byte) 255, (byte) 2, (byte) 3, (byte) 248, (byte) 255, (byte) 255};
  private byte[] someValidBytesArray = {(byte) 1, (byte) 2, (byte) 3, (byte) 4,
      (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10, (byte) 11, (byte) 12,
      (byte) 13, (byte) 14, (byte) 10, (byte) 16, (byte) 1};

  private static final double HIGH_PRECISION_LEVEL = 0.0000001;

  private static final int SOME_SEQUENCE_NUMBER = 65535;
  private static final int SOME_SEQUENCE_NUMBER_2 = 770;
  private static final int SOME_YEAR = 65535;
  private static final int SOME_YEAR_2 = 1284;
  private static final int SOME_MONTH = 255;
  private static final int SOME_MONTH_2 = 6;
  private static final int SOME_DAY = 255;
  private static final int SOME_DAY_2 = 7;
  private static final int SOME_HOUR = 255;
  private static final int SOME_HOUR_2 = 8;
  private static final int SOME_MINUTE = 255;
  private static final int SOME_MINUTE_2 = 9;
  private static final int SOME_SECOND = 255;
  private static final int SOME_SECOND_2 = 10;
  private static final int SOME_TIME_OFFSET = 65535;
  private static final int SOME_TIME_OFFSET_2 = 3083;
  private static final float SOME_GLUCOSE_CONCENTRATION = 770;
  private static final float SOME_GLUCOSE_CONCENTRATION_2 = 3597;
  private static final String SOME_TYPE = "Undetermined Plasma";
  private static final String SOME_TYPE_2 = "Control Solution";
  private static final String SOME_SAMPLE_LOCATION = "Sample Location value not available";
  private static final String SOME_SAMPLE_LOCATION_2 = "Reserved for future use";

  @Before
  public void setup() {
    underTest = new GlucoseMeasurementBytesMapper();
  }

  @Test
  public void givenValidArrayOfBytesFilledWithOnes_whenMappingBytesToGlucoseMeasurementEntity_thenCorrectFieldsAreSet() {
    glucoseMeasurementToBeMapped = underTest
        .bytesToGlucoseMeasurement(someValidBytesArrayFilledWithOnes);
    validateAllFlags();
    validateAllData();
    validateAllSensorStatusAnnunciationFlags();
  }

  private void validateAllSensorStatusAnnunciationFlags() {
    assertTrue(glucoseMeasurementToBeMapped.getSensorStatusAnnunciation()
        .isDeviceBatteryLowAtTimeOfMeasurement());
    assertTrue(glucoseMeasurementToBeMapped.getSensorStatusAnnunciation()
        .isSensorMalfunctionOrFaultingAtTimeOfMeasurement());
    assertTrue(glucoseMeasurementToBeMapped.getSensorStatusAnnunciation()
        .isSampleSizeForBloodOrControlSolutionInsufficientAtTimeOfMeasurement());
    assertTrue(glucoseMeasurementToBeMapped.getSensorStatusAnnunciation().isStripInsertionError());
    assertTrue(
        glucoseMeasurementToBeMapped.getSensorStatusAnnunciation().isStripTypeIncorrectForDevice());
    assertTrue(glucoseMeasurementToBeMapped.getSensorStatusAnnunciation()
        .isSensorResultHigherThanTheDeviceCanProcess());
    assertTrue(glucoseMeasurementToBeMapped.getSensorStatusAnnunciation()
        .isSensorResultLowerThanTheDeviceCanProcess());
    assertTrue(glucoseMeasurementToBeMapped.getSensorStatusAnnunciation()
        .isSensorTemperatureTooHighForValidTestResultAtTimeOfMeasurement());
    assertTrue(glucoseMeasurementToBeMapped.getSensorStatusAnnunciation()
        .isSensorTemperatureTooLowForValidTestResultAtTimeOfMeasurement());
    assertTrue(glucoseMeasurementToBeMapped.getSensorStatusAnnunciation()
        .isSensorReadInterruptedBecauseStripWasPulledTooSoonAtTimeOfMeasurement());
    assertTrue(glucoseMeasurementToBeMapped.getSensorStatusAnnunciation()
        .isGeneralDeviceFaultHasOccurredInTheSensor());
    assertTrue(glucoseMeasurementToBeMapped.getSensorStatusAnnunciation()
        .isTimeFaultHasOccurredInTheSensorAndTimeMayBeInaccurate());
  }

  private void validateAllData() {
    assertEquals(SOME_SEQUENCE_NUMBER, glucoseMeasurementToBeMapped.getSequenceNumber());
    assertEquals(SOME_YEAR, glucoseMeasurementToBeMapped.getYear());
    assertEquals(SOME_MONTH, glucoseMeasurementToBeMapped.getMonth());
    assertEquals(SOME_DAY, glucoseMeasurementToBeMapped.getDay());
    assertEquals(SOME_HOUR, glucoseMeasurementToBeMapped.getHour());
    assertEquals(SOME_MINUTE, glucoseMeasurementToBeMapped.getMinute());
    assertEquals(SOME_SECOND, glucoseMeasurementToBeMapped.getSecond());
    assertEquals(SOME_TIME_OFFSET, glucoseMeasurementToBeMapped.getTimeOffset());
    assertEquals(SOME_GLUCOSE_CONCENTRATION, glucoseMeasurementToBeMapped.getGlucoseConcentration(),
        HIGH_PRECISION_LEVEL);
    assertEquals(SOME_TYPE, glucoseMeasurementToBeMapped.getType());
    assertEquals(SOME_SAMPLE_LOCATION, glucoseMeasurementToBeMapped.getSampleLocation());
  }

  private void validateAllFlags() {
    assertTrue(glucoseMeasurementToBeMapped.getFlags().isTimeOffsetPresent());
    assertTrue(glucoseMeasurementToBeMapped.getFlags().isGlucoseConcentrationTypeSamplePresent());
    assertTrue(glucoseMeasurementToBeMapped.getFlags().isSensorStatusAnnunciationPresent());
    assertTrue(glucoseMeasurementToBeMapped.getFlags().isContextInformationsFollows());
    assertTrue(glucoseMeasurementToBeMapped.getFlags().isGlucoseConcentrationUnitFlag());
  }

  @Test
  public void givenValidArrayOfBytes_whenMappingBytesToGlucoseMeasurementEntity_thenCorrectFieldsAreSet() {
    glucoseMeasurementToBeMapped = underTest
        .bytesToGlucoseMeasurement(someValidBytesArray);

    assertTrue(glucoseMeasurementToBeMapped.getFlags().isTimeOffsetPresent());
    assertEquals(SOME_SEQUENCE_NUMBER_2, glucoseMeasurementToBeMapped.getSequenceNumber());
    assertEquals(SOME_YEAR_2, glucoseMeasurementToBeMapped.getYear());
    assertEquals(SOME_MONTH_2, glucoseMeasurementToBeMapped.getMonth());
    assertEquals(SOME_DAY_2, glucoseMeasurementToBeMapped.getDay());
    assertEquals(SOME_HOUR_2, glucoseMeasurementToBeMapped.getHour());
    assertEquals(SOME_MINUTE_2, glucoseMeasurementToBeMapped.getMinute());
    assertEquals(SOME_SECOND_2, glucoseMeasurementToBeMapped.getSecond());
    assertEquals(SOME_TIME_OFFSET_2, glucoseMeasurementToBeMapped.getTimeOffset());
    assertEquals(SOME_GLUCOSE_CONCENTRATION_2,
        glucoseMeasurementToBeMapped.getGlucoseConcentration(), HIGH_PRECISION_LEVEL);
    assertEquals(SOME_TYPE_2, glucoseMeasurementToBeMapped.getType());
    assertEquals(SOME_SAMPLE_LOCATION_2, glucoseMeasurementToBeMapped.getSampleLocation());
    assertTrue(
        glucoseMeasurementToBeMapped.getSensorStatusAnnunciation().isStripTypeIncorrectForDevice());
    assertTrue(glucoseMeasurementToBeMapped.getSensorStatusAnnunciation()
        .isSensorTemperatureTooLowForValidTestResultAtTimeOfMeasurement());
  }
}

