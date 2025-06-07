import java.io.PrintStream;
import java.text.SimpleDateFormat;

import CrFormatter.Formatter;
import CrFormatter.FormatterOptions;

import static java.lang.Math.min;

public class Logger {
    FormatterOptions formatError = new FormatterOptions("B", "R");
    FormatterOptions formatWarning = new FormatterOptions("", "Y");
    FormatterOptions formatInfo = new FormatterOptions("", "D");
    FormatterOptions formatWeak = new FormatterOptions("", "W");
    FormatterOptions formatSuccess = new FormatterOptions("B", "G");
    PrintStream debugOutput = System.out;


    /**
     * logLevel: </br>
     * 0 - Off </br>
     * 1 - Errors and Success </br>
     * 2 - Warnings </br>
     * 3 - Info </br>
     * 4 - Weak
     */
    int logLevel = 4;
    static boolean LogStackTrace = true;

    String getTime() {
    return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS").format(new java.util.Date());
    }
    void logWeak(String message) { if (logLevel>3) log(message, formatWeak); }
    void logError(String message) {
      if (logLevel>0) log(message, formatError);
    }
    void logWarning(String message) {
      if (logLevel>1) log(message, formatWarning);
    }
    void logInfo(String message) {
      if (logLevel>2) log(message, formatInfo);
    }
    void logSuccess(String message) {if (logLevel>0) log(message, formatSuccess); }
    private void log(String message) {
    log(message, formatInfo);
    }
    private void log(String message, FormatterOptions options) {
        if (Logger.LogStackTrace) {
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            instance.logBare("Log from", formatWeak);
            for (int i = min(5, stackTraceElements.length-1); i >= min(2, stackTraceElements.length-1); i--) {
                StackTraceElement stackTraceElement = stackTraceElements[i];
                instance.logBare(":" + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + "()", formatWeak);
            }
            instance.logBare(" ", formatWeak);
        }
        debugOutput.println(Formatter.format("["+getTime()+"] - "+message, options));
    }

    private void logBare(String message, FormatterOptions options) { debugOutput.print(Formatter.format(message, options)); }

    private static final Logger instance = new Logger();
    public static Logger getLogger() {
        return instance;
    }

    public void setLogLevel(int logLevel) { this.logLevel = logLevel; }
}
