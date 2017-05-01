package cc.gilmore.apps.dressmike;

/**
 * Created by Mike on 3/31/2016.
 */
public class Logger {
    static public enum LogLevel {
        INFO,
        WARNING,
        ERROR,
        FATAL
    };

    static public void log(String msg, LogLevel level) {
        String levelPrefix = "INFO";
        switch (level) {
            case WARNING: levelPrefix = "WARNING"; break;
            case ERROR: levelPrefix = "ERROR"; break;
            case FATAL: levelPrefix = "FATAL"; break;
        }

        System.out.println(Util.getDateTime() + " " + levelPrefix + ": " + msg);
    }

    static public void log(String msg, Exception e, LogLevel level) {
        log(msg + "; Exception: " + e.getMessage(), level);
    }
}
