package com.tech.employee.cmd;

import java.util.List;
import java.util.Map;

class TableDisplayUtils {
    private TableDisplayUtils() {
    }

    static void displayTable(List<Map<String, Object>> data, String... headers) {
        if (data == null || data.isEmpty()) {
            System.out.println("No data to display.");
            return;
        }

        // Determine column widths dynamically based on data
        int[] columnWidths = new int[headers.length];

        // Calculate the max width for each column
        for (int i = 0; i < headers.length; i++) {
            columnWidths[i] = headers[i].length(); // Start with header length

            for (Map<String, Object> row : data) {
                String value = row.get(headers[i]) != null ? row.get(headers[i]).toString() : null;
                if (value != null && value.length() > columnWidths[i]) {
                    columnWidths[i] = value.length();
                }
            }
        }

        // Print the table headers
        printRow(headers, columnWidths);
        printSeparator(columnWidths);

        // Print each data row
        for (Map<String, Object> row : data) {
            String[] rowValues = new String[headers.length];
            for (int i = 0; i < headers.length; i++) {
                rowValues[i] = row.get(headers[i]) != null ? row.get(headers[i]).toString() : "";
            }
            printRow(rowValues, columnWidths);
        }

        // Print closing separator
        printSeparator(columnWidths);
    }

    // Helper method to print a row of data
    private static void printRow(String[] row, int[] columnWidths) {
        for (int i = 0; i < row.length; i++) {
            System.out.printf("| %-" + columnWidths[i] + "s ", row[i]);
        }
        System.out.println("|");
    }

    // Helper method to print a separator row
    private static void printSeparator(int[] columnWidths) {
        for (int width : columnWidths) {
            System.out.print("+");
            for (int i = 0; i < width + 2; i++) {
                System.out.print("-");
            }
        }
        System.out.println("+");
    }
}
