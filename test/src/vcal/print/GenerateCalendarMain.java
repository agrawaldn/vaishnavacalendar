package vcal.print;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import vcal.util.PropertyReader;

public class GenerateCalendarMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        IPrintCalender pc = null;
        if (args.length < 4) {
            System.err.println("Need at least three arguments");
            System.err.println("example 2014 1 PUN13-53.VCC cal2014.csv");
            System.exit(1);
        }
        int startYear = Integer.parseInt(args[0]);
        int noOfYears = Integer.parseInt(args[1]);
        int endYear = startYear;
        if (noOfYears > 0) {
            endYear = startYear + (noOfYears - 1);
        }
        String outputFileName = args[3];
        Date startDt = null;
        Date endDt = null;
        DateFormat df = new SimpleDateFormat(PropertyReader.getInstance().getProperty("input.date.format"));
        try {
            startDt = df.parse("01-01-" + startYear);
            endDt = df.parse("31-12-" + endYear);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            pc = PrintCalendarFactory.buildPrintCalendar(args[2], outputFileName);
            pc.print(startDt, endDt, PropertyReader.getInstance().getProperty("output.path")
                    + outputFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
