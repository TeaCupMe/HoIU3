import java.io.PrintStream;
import java.text.SimpleDateFormat;

import CrFormatter.Formatter;
import CrFormatter.FormatterOptions;

import static java.lang.Math.min;

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
          if (Game.LogStackTrace) {
              StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
              debugOutput.print("Log from");
              for (int i = min(5, stackTraceElements.length); i >= 2; i--) {
                  StackTraceElement stackTraceElement = stackTraceElements[i];
                  debugOutput.print(":" + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + "()");
              }
              debugOutput.print(" ");
          }
          debugOutput.println(Formatter.format("["+getTime()+"] - "+message, options));
    }

    private static final Logger instance = new Logger();
    public static Logger getLogger() {
        return instance;
    }

}
