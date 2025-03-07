package com.sensor.service;

import com.sensor.model.SensorData;
import com.sensor.model.Threshold;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OutlierDetector {
    public static List<SensorData> detectOutliers(List<SensorData> sensorDataList, Map<String, Threshold> thresholdMap) {
        return sensorDataList.stream()
                .filter(data -> {
                    Threshold threshold = thresholdMap.get(data.getSensorType());
                    return threshold != null && (data.getValue() < threshold.getMinThreshold() || data.getValue() > threshold.getMaxThreshold());
                })
                .collect(Collectors.toList());
    }
}
