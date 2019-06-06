package com.levio.lab.bt.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.levio.lab.bt.services.glucose.measurementcontext.GlucoseMeasurementContext;
import org.junit.Before;
import org.junit.Test;

public class GlucoseMeasurementsContextBytesMapperTest {

  private static final double HIGH_PRECISION_LEVEL = 0.0000001;
  private GlucoseMeasurementContextBytesMapper underTest;
  private byte[] someArrayOfBytes = {(byte) 255, 6, 0, 0, 1, 2, 3, 4, 3, 2, 2, 8, 4, 5, 1, 2, 3};
  private GlucoseMeasurementContext mappedContext;

  @Before
  public void setup() {
    underTest = new GlucoseMeasurementContextBytesMapper();
  }

  @Test
  public void givenValidArrayOfBytes_whenMappingToGlucoseMeasurementContext_thenCorrectFieldsAreMapped() {
    mappedContext = underTest.bytesToGlucoseMeasurementContext(someArrayOfBytes);

    validateAllFlags();

    assertEquals(mappedContext.getSequenceNumber(), 6);
    assertTrue(mappedContext.getCarbohydrateId().equals("Breakfast"));
    assertEquals(mappedContext.getCarbohydrateQuantity(), 770, HIGH_PRECISION_LEVEL);
    assertTrue(mappedContext.getMeal().equals("Casual (snacks, drinks, etc.)"));
    assertTrue(mappedContext.getTester().equals("Reserved for future use"));
    assertTrue(mappedContext.getHealth().equals("During Menses"));
    assertEquals(mappedContext.getExerciceDuration(), 514);
    assertEquals(mappedContext.getExerciceIntensity(), 8);
    assertTrue(mappedContext.getMedicationId().equals("Long acting insulin"));
    assertTrue(mappedContext.getMedicationUnits().equals("Units of liters"));
    assertEquals(mappedContext.getMedicationQuantity(), 261, HIGH_PRECISION_LEVEL);
    assertEquals(mappedContext.getHbA1c(), 770, HIGH_PRECISION_LEVEL);
  }

  private void validateAllFlags() {
    assertTrue(mappedContext.getFlags().isCarbohydrateIdAndCarbohydratePresent());
    assertTrue(mappedContext.getFlags().isMealPresent());
    assertTrue(mappedContext.getFlags().isTesterHealthPresent());
    assertTrue(mappedContext.getFlags().isExerciseDurationAndExerciseIntensityPresent());
    assertTrue(mappedContext.getFlags().isMedicationIdAndMedicationPresent());
    assertTrue(mappedContext.getFlags().isMedicationValueUnits());
    assertTrue(mappedContext.getFlags().isHba1CPresent());
    assertTrue(mappedContext.getFlags().isExtendedFlagsPresent());
  }

}
