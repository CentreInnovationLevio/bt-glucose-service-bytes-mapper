package com.levio.lab.bt.mappers;


import com.levio.lab.bt.services.glucose.feature.GlucoseFeature;
import com.levio.lab.bt.services.glucose.measurement.GlucoseMeasurement;
import com.levio.lab.bt.services.glucose.measurementcontext.GlucoseMeasurementContext;

public class GlucoseServicesBytesMapper {

  public static GlucoseFeature bytesToGlucoseFeature(byte[] bytes) {
    return GlucoseFeatureBytesMapper.bytesToGlucoseFeature(bytes);
  }

  public static GlucoseMeasurement bytesToGlucoseMeasurement(byte[] bytes) {
    return GlucoseMeasurementBytesMapper.bytesToGlucoseMeasurement(bytes);
  }

  public static GlucoseMeasurementContext bytesToGlucoseMeasurementContext(byte[] bytes) {
    return GlucoseMeasurementContextBytesMapper.bytesToGlucoseMeasurementContext(bytes);
  }
}
