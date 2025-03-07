package com.sensor.util;

import com.sensor.model.SensorData;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CSVWriter {
    public static void writeCSV(String filePath, List<String[]> data) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(filePath))) {
            for (String[] line : data) {
                bw.write(String.join(",", line));
                bw.newLine();
            }
        }
    }
}
