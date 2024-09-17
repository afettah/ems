package com.tech.employee.exposition.cli;

import java.util.List;
import java.util.Map;

class TableDisplayUtils {
    private TableDisplayUtils() {
    }

    static String displayTable(List<Map<String, Object>> data, String... headers) {
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
