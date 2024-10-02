import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FlowLogTags {

    //Function to load values from lookup_table.csv into a HashMap
    private static Map<String, String> loadLookupTable(String filePath) throws IOException {
        HashMap<String, String> lookupTable = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] entries = line.split(",");
                if (entries.length == 3) {
                    String key = entries[0].trim().toLowerCase() + "," + entries[1].trim().toLowerCase();
                    String value = entries[2].trim().toLowerCase();
                    lookupTable.put(key, value);
                }
            }
        }
        return lookupTable;
    }

    //Function to return appropriate protocol based on the flow log
    private static String getProtocol(String protocolNum) {
        switch (protocolNum) {
            case "6":
                return "tcp";
            case "17":
                return "udp";
            case "1":
                return "icmp";
            default:
                return "unknown";
        }
    }

    //Function to load values from flow_logs.txt into a List
    private static List<FlowLogEntry> loadFlowLogs(String filePath) throws IOException {
        List<FlowLogEntry> flowLogs = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] entries = line.split("\\s+");
                if (entries.length >= 14) {
                    String dstPort = entries[6].trim();
                    String protocolNum = entries[7].trim();
                    String protocol = getProtocol(protocolNum);
                    flowLogs.add(new FlowLogEntry(dstPort.toLowerCase(), protocol));
                }
            }
        }
        return flowLogs;
    }

    //Function to assimilate all the results for the output file
    private static void writeOutput(String filePath, Map<String, Integer> tagCounts, Map<String, Integer> portProtocolCounts) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            writer.write("Tag Counts:\n");
            writer.write("Tag, Count\n");
            for (Map.Entry<String, Integer> entry : tagCounts.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }

            writer.write("\nPort/Protocol Combination Counts:\n");
            writer.write("Port, Protocol, Count\n");
            for (Map.Entry<String, Integer> entry : portProtocolCounts.entrySet()) {
                String[] parts = entry.getKey().split(",");
                writer.write(parts[0] + "," + parts[1] + "," + entry.getValue() + "\n");
            }
        }
    }

    public static void main(String[] args) throws IOException {

        //Defining filepaths
        String lookupFilePath = "lookup_table.csv";
        String flowLogFilePath = "flow_logs.txt";
        String outputFilePath = "output.txt";

        //Loading logs and lookup table into data structures
        Map<String, String> lookupTable = loadLookupTable(lookupFilePath);
        List<FlowLogEntry> flowLogs = loadFlowLogs(flowLogFilePath);

        //Defining result data structures
        Map<String, Integer> tagCounts = new HashMap<>();
        Map<String, Integer> portProtocolCounts = new HashMap<>();

        //Iterating the flow logs and adding the desired values to the result data structures
        for (FlowLogEntry entry : flowLogs) {
            String key = entry.dstPort + "," + entry.protocol;
            String value = lookupTable.getOrDefault(key, "untagged");

            tagCounts.put(value, tagCounts.getOrDefault(value, 0) + 1);
            portProtocolCounts.put(key, portProtocolCounts.getOrDefault(key, 0) + 1);
        }

        //Generating output file with the desired results
        writeOutput(outputFilePath, tagCounts, portProtocolCounts);
    }

    //Defining the essential parts of the flow logs required in the result into a class
    private static class FlowLogEntry {
        String dstPort;
        String protocol;

        FlowLogEntry(String dstPort, String protocol) {
            this.dstPort = dstPort;
            this.protocol = protocol;
        }
    }
    
}
