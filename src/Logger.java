import java.io.PrintStream;
import java.text.SimpleDateFormat;

import CrFormatter.Formatter;
import CrFormatter.FormatterOptions;

public class Logger {
      FormatterOptions formatError = new FormatterOptions("B", "R");
      FormatterOptions formatWarning = new FormatterOptions("", "Y");
      FormatterOptions formatInfo = new FormatterOptions("", "W");
      PrintStream debugOutput = System.out;

      String getTime() {
        return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS").format(new java.util.Date());
    }
      void logError(String message) {
        log(message, formatError);
    }
      void logWarning(String message) {
        log(message, formatWarning);
    }
      void logInfo(String message) {
        log(message, formatInfo);
    }
      void log(String message) {
        log(message, formatInfo);
    }
      void log(String message, FormatterOptions options) {
        debugOutput.println(Formatter.format("["+getTime()+"] - "+message, options));
    }

    private static final Logger instance = new Logger();
    public static Logger getLogger() {
        return instance;
    }

}
