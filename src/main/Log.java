package main;

public class Log {
    // Singleton instance of Log
    private static Log instance;
    // Buffer to store log events
    private StringBuffer logBuffer;

    // Private constructor to prevent instantiation
    private Log() {
        logBuffer = new StringBuffer();
    }

    // Synchronized method to get the singleton instance
    public static synchronized Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    // Adds an event to the log
    public void addEvent(String event) {
        logBuffer.append(event).append("\n");
    }

    // Alias for addEvent for backward compatibility or readability
    public void addLog(String event) {
        addEvent(event);
    }

    // Returns the current log as a string
    public String getLog() {
        return logBuffer.toString();
    }

    // Clears the log buffer
    public void clearLog() {
        logBuffer.setLength(0);
    }

    // Writes the log to a specified file
    public void writeLogToFile(String filename) {
        try (java.io.FileWriter writer = new java.io.FileWriter(filename)) {
            writer.write(logBuffer.toString());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    // Returns the string representation of the log
    @Override
    public String toString() {
        return logBuffer.toString();
    }
}
