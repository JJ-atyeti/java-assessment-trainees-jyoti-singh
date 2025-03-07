package com.sensor.main;

import com.sensor.model.SensorData;
import com.sensor.model.Threshold;
import com.sensor.service.DataProcessor;
import com.sensor.service.OutlierDetector;
import com.sensor.util.CSVReader;
import com.sensor.util.CSVWriter;

import java.util.List;
import java.util.Map;

public class SensorDataApp {
    public static void main(String[] args) {
        try {
            List<SensorData> sensorData = CSVReader.readSensorData("sensor_data.csv");
            Map<String, Threshold> thresholds = CSVReader.readThresholds("thresholds.csv");

            List<SensorData> outliers = OutlierDetector.detectOutliers(sensorData, thresholds);
            Map<String, Map<java.time.Month, double[]>> monthlyStats = DataProcessor.calculateMonthlyStats(sensorData);

            // Writing CSV files
            // (Implementation similar to previous CSVWriter example)

            System.out.println("Processing completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
