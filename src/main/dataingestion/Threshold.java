package main.dataingestion;

public class Threshold {
    private String sensorType;
    private double minThreshold;
    private double maxThreshold;

    public Threshold(String sensorType, double minThreshold, double maxThreshold) {
        this.sensorType = sensorType;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
    }

    public String getSensorType() {
        return sensorType;
    }

    public double getMinThreshold() {
        return minThreshold;
    }

    public double getMaxThreshold() {
        return maxThreshold;
    }

    @Override
    public String toString() {
        return sensorType + "," + minThreshold + "," + maxThreshold;
    }
}
