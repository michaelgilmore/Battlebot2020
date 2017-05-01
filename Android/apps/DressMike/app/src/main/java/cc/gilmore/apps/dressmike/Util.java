package cc.gilmore.apps.dressmike;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mike on 3/31/2016.
 */
public class Util {
    public final static String getDateTime()
    {
        DateFormat df = new SimpleDateFormat("yyyyMMdd_hhmmss");
        return df.format(new Date());
    }

    public final static String formatDate(Date d, String format)
    {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(d);
    }

    public final static Date parseDate(String s, String format) {
        DateFormat df = new SimpleDateFormat(format);
        Date parsedDate;
        try {
            parsedDate = df.parse(s);
        }
        catch(ParseException e) {
            Logger.log("Unable to parse date (" + s + ")", Logger.LogLevel.ERROR);
            parsedDate = null;
        }
        return parsedDate;
    }
}
