package com.tech.shared.cli;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Component
public class TableDisplayMapper {

    private final ObjectMapper objectMapper;

    public <T> String display(List<T> data,  String... headers) {
        if (data == null || data.isEmpty()) {
            return "No data to display.";
        }

        List<Map<String, Object>> dataList = new ArrayList<>();
        for (Object item : data) {
            dataList.add(objectMapper.convertValue(item, new TypeReference<>() {
            }));
        }

        return displayTable(dataList, headers);
    }

    private static String displayTable(List<Map<String, Object>> data, String... headers) {
        StringBuilder builder = new StringBuilder();
        if (data == null || data.isEmpty()) {
            builder.append("No data to display.");
            return builder.toString();
        }

        int[] columnWidths = computeColumnsWidth(data, headers);

        // Print the table headers
        printSeparator(columnWidths, builder);
        printRow(headers, columnWidths, builder);
        printSeparator(columnWidths, builder);

        // Print each data row
        for (Map<String, Object> row : data) {
            String[] rowValues = new String[headers.length];
            for (int i = 0; i < headers.length; i++) {
                rowValues[i] = row.get(headers[i]) != null ? row.get(headers[i]).toString() : "";
            }
            printRow(rowValues, columnWidths, builder);
        }

        // Print closing separator
        printSeparator(columnWidths, builder);

        builder.append("Total: ").append(data.size()).append(" rows\n");

        return builder.toString();
    }

    private static int[] computeColumnsWidth(List<Map<String, Object>> data, String[] headers) {
        // Determine column widths dynamically based on data
        int[] columnWidths = new int[headers.length];

        // Calculate the max width for each column
        for (int i = 0; i < headers.length; i++) {
            columnWidths[i] = headers[i].length(); // Start with header length

            for (Map<String, Object> row : data) {
                String value = row.get(headers[i]) != null ? row.get(headers[i]).toString() : "";
                if (value.length() > columnWidths[i]) {
                    columnWidths[i] = value.length();
                }
            }
        }
        return columnWidths;
    }

    // Helper method to print a row of data
    private static void printRow(String[] row, int[] columnWidths, StringBuilder builder) {
        for (int i = 0; i < row.length; i++) {
            builder.append("| ").append(String.format("%-" + columnWidths[i] + "s", row[i])).append(" ");
        }
        builder.append("|\n");
    }

    // Helper method to print a separator row
    private static void printSeparator(int[] columnWidths, StringBuilder builder) {
        for (int width : columnWidths) {
            builder.append("+");
            for (int i = 0; i < width + 2; i++) {
                builder.append("-");
            }
        }
        builder.append("+\n");
    }
}
