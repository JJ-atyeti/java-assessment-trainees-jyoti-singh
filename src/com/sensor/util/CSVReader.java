package com.sensor.util;

import com.sensor.model.SensorData;
import com.sensor.model.Threshold;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CSVReader {

    // ✅ Method to Read Sensor Data from CSV
    public static List<SensorData> readSensorData(String filePath) throws IOException {
        List<SensorData> sensorDataList = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            br.readLine(); // Skip header row

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // ✅ Ensure we have exactly 5 columns
                if (data.length < 5) {
                    System.out.println("⚠ Skipping invalid row (too few columns): " + Arrays.toString(data));
                    continue;
                }

                try {
                    // ✅ Check if date column is missing
                    if (data[0].trim().isEmpty()) {
                        System.out.println("⚠ Skipping row with missing date: " + Arrays.toString(data));
                        continue;
                    }

                    // ✅ Extract and format date
                    String rawDate = data[0].trim();
                    String cleanDate = rawDate.split(" ")[0]; // Extracts "yyyy-MM-dd"
                    LocalDate date = LocalDate.parse(cleanDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    String sensorType = data[1].trim();

                    // ✅ Check if `value` column is empty (assign default 0.0)
                    double value = data[2].trim().isEmpty() ? 0.0 : Double.parseDouble(data[2].trim());

                    String unit = data[3].trim();

                    // ✅ Convert decimal `location_id` to integer properly
                    int locationId = (int) Double.parseDouble(data[4].trim());

                    sensorDataList.add(new SensorData(date, sensorType, value, unit, locationId));
                } catch (Exception e) {
                    System.out.println("⚠ Error processing row: " + Arrays.toString(data) + " → " + e.getMessage());
                }
            }
        }
        return sensorDataList;
    }

    // ✅ Method to Read Threshold Data from CSV
    public static Map<String, Threshold> readThresholds(String filePath) throws IOException {
        Map<String, Threshold> thresholdMap = new HashMap<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            br.readLine(); // Skip header row

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // ✅ Validate CSV row format (Skip invalid or empty rows)
                if (data.length < 3 || data[0].trim().isEmpty() || data[1].trim().isEmpty() || data[2].trim().isEmpty()) {
                    System.out.println("⚠ Skipping invalid threshold row: " + Arrays.toString(data));
                    continue;
                }

                try {
                    String sensorType = data[0].trim();
                    double minThreshold = Double.parseDouble(data[1].trim());
                    double maxThreshold = Double.parseDouble(data[2].trim());

                    thresholdMap.put(sensorType, new Threshold(sensorType, minThreshold, maxThreshold));
                } catch (NumberFormatException e) {
                    System.out.println("⚠ Invalid number format in threshold row: " + Arrays.toString(data));
                }
            }
        }
        return thresholdMap;
    }
}
