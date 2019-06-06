package com.levio.lab.bt.services.glucose.measurement;

public class GlucoseMeasurement {

  private GlucoseMeasurementFlags flags;
  private int sequenceNumber;
  private int year;
  private int month;
  private int day;
  private int hour;
  private int minute;
  private int second;
  private int timeOffset;
  private float glucoseConcentration;
  private String type;
  private String sampleLocation;
  private SensorStatusAnnunciation sensorStatusAnnunciation;

  public GlucoseMeasurementFlags getFlags() {
    return flags;
  }

  public void setFlags(GlucoseMeasurementFlags flags) {
    this.flags = flags;
  }

  public int getSequenceNumber() {
    return sequenceNumber;
  }

  public void setSequenceNumber(int sequenceNumber) {
    this.sequenceNumber = sequenceNumber;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public int getDay() {
    return day;
  }

  public void setDay(int day) {
    this.day = day;
  }

  public int getHour() {
    return hour;
  }

  public void setHour(int hour) {
    this.hour = hour;
  }

  public int getMinute() {
    return minute;
  }

  public void setMinute(int minute) {
    this.minute = minute;
  }

  public int getSecond() {
    return second;
  }

  public void setSecond(int second) {
    this.second = second;
  }

  public int getTimeOffset() {
    return timeOffset;
  }

  public void setTimeOffset(int timeOffset) {
    this.timeOffset = timeOffset;
  }

  public float getGlucoseConcentration() {
    return glucoseConcentration;
  }

  public void setGlucoseConcentration(float glucoseConcentration) {
    this.glucoseConcentration = glucoseConcentration;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSampleLocation() {
    return sampleLocation;
  }

  public void setSampleLocation(String sampleLocation) {
    this.sampleLocation = sampleLocation;
  }

  public SensorStatusAnnunciation getSensorStatusAnnunciation() {
    return sensorStatusAnnunciation;
  }

  public void setSensorStatusAnnunciation(
      SensorStatusAnnunciation sensorStatusAnnunciation) {
    this.sensorStatusAnnunciation = sensorStatusAnnunciation;
  }
}
