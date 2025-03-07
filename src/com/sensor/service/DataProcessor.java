package com.sensor.service;

import com.sensor.model.SensorData;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class DataProcessor {
    public static Map<String, Map<Month, double[]>> calculateMonthlyStats(List<SensorData> sensorDataList) {
        Map<String, Map<Month, double[]>> monthlyStats = new HashMap<>();

        sensorDataList.stream()
                .collect(Collectors.groupingBy(SensorData::getSensorType))
                .forEach((sensorType, data) -> {
                    Map<Month, double[]> monthMap = new HashMap<>();

                    data.stream().collect(Collectors.groupingBy(d -> d.getDate().getMonth()))
                            .forEach((month, readings) -> {
                                double sum = readings.stream().mapToDouble(SensorData::getValue).sum();
                                double avg = sum / readings.size();
                                double max = readings.stream().mapToDouble(SensorData::getValue).max().orElse(0);
                                double min = readings.stream().mapToDouble(SensorData::getValue).min().orElse(0);
                                monthMap.put(month, new double[]{avg, max, min});
                            });

                    monthlyStats.put(sensorType, monthMap);
                });

        return monthlyStats;
    }
}
