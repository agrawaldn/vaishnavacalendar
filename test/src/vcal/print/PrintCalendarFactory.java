package vcal.print;

import java.io.IOException;
import java.text.ParseException;

import vcal.util.PropertyReader;

public class PrintCalendarFactory {
    public static IPrintCalender buildPrintCalendar(String inputFileName, String outputFileName) throws IOException,
            ParseException, Exception {
        if (outputFileName.contains("csv")) {
            return new PrintCSVCalendar(PropertyReader.getInstance().getProperty("input.path") + inputFileName);
        }
        return new PrintHtmlCalendar(PropertyReader.getInstance().getProperty("input.path") + inputFileName);
    }

}
