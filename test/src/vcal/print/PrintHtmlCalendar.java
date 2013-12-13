package vcal.print;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import vcal.bean.CalendarDay;
import vcal.bean.Event;
import vcal.parse.ParseCalendar;
import vcal.util.DateIterator;
import vcal.util.PropertyReader;
import vcal.util.Utility;

public class PrintHtmlCalendar
        implements IPrintCalender {
    PrintStream out;
    ParseCalendar parser;
    Date startDt;
    Date endDt;

    public PrintHtmlCalendar(String inputFilePath) throws IOException, ParseException, Exception {
        parser = new ParseCalendar(inputFilePath);
    }

    private void printDay(CalendarDay day, Date date) {
        DateFormat df = new SimpleDateFormat("dd");
        String styleClass = "";
        if (getDayNumberOfWeek(date) % 2 == 0) {
            styleClass = "even-column";
        } else {
            styleClass = "odd-column";
        }
        if (day.getFast()) {
            styleClass = "fasting";
        }
        out.println("<td class=\"" + styleClass + "\" valign=top>");
        out.println("<font size=20><b>" + df.format(date) + "</b></font>");
        out.println("<font size=1 align=right ><i>" + day.getTithi() + "</i></font>");
        //if(day.getTithi().contains("Pratipat") && day.getPaksha().equals("K")){
        if (day.isFirstDayOfMonth()) {
            out.println("<br><b>" + day.getMonth() + " </b>");
        }
        if (day.getTithi().equals("Purnima")) {
            out.println("<img src=purnima.gif alt=Purnima border=0>");
        } else if (day.getTithi().equals("Amavasya")) {
            out.println("<img src=amavasya.gif alt=Amavasya border=0>");
        }
        if (day.getEventList().size() > 0) {
            for (Event event : day.getEventList()) {
                if (event.getEventName().contains("Disappearance")) {
                    event.setEventName(event.getEventName().replace("Disappearance",
                            "<img src=dis.gif alt=Disappearance border=0>"));
                } else if (event.getEventName().contains("Appearance")) {
                    event.setEventName(event.getEventName().replace("Appearance",
                            "<img src=ap.gif alt=Appearance border=0>"));
                }
                out.println("<br>" + event.getEventName());
                if (event.getEventType().equals(Utility.EventType.BreakFast)) {
                    out.println(event.getStartTime() + " - " + event.getEndTime());
                }
                if (!event.getEventDescription().isEmpty()) {
                    out.println(" (" + event.getEventDescription() + ")");
                }
            }
        }
        out.println("</td>");
    }

    private int getDayNumberOfWeek(Date date) {
        DateFormat dayDateFormat = new SimpleDateFormat(PropertyReader.getInstance().getProperty("day.format"));
        int dayNumberOfWeek = 0;
        String dayOfWeek = dayDateFormat.format(date);
        if (dayOfWeek.equals("Monday")) {
            dayNumberOfWeek = 1;
        } else if (dayOfWeek.equals("Tuesday")) {
            dayNumberOfWeek = 2;
        } else if (dayOfWeek.equals("Wednesday")) {
            dayNumberOfWeek = 3;
        } else if (dayOfWeek.equals("Thursday")) {
            dayNumberOfWeek = 4;
        } else if (dayOfWeek.equals("Friday")) {
            dayNumberOfWeek = 5;
        } else if (dayOfWeek.equals("Saturday")) {
            dayNumberOfWeek = 6;
        }
        return dayNumberOfWeek;
    }

    private void printMonth(String month) {

        out.println("</tr></table><br>");
        //out.println("<TABLE BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=100%>");
        out.println("<table class=\"TABLE\">");
        out.println("<tr>");
        out.println("<td class=\"month-title\" colspan=7>" + month + "</td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td class=\"weekday-title-even\">Sunday</td>");
        out.println("<td class=\"weekday-title-odd\">Monday</td>");
        out.println("<td class=\"weekday-title-even\">Tuesday</td>");
        out.println("<td class=\"weekday-title-odd\">Wednesday</td>");
        out.println("<td class=\"weekday-title-even\">Thursday</td>");
        out.println("<td class=\"weekday-title-odd\">Friday</td>");
        out.println("<td class=\"weekday-title-even\">Saturday</td>");
        out.println("</tr>");
    }

    public void print(Date startDate, Date endDate, String outputFilePath) throws IOException {
        startDt = startDate;
        endDt = endDate;
        try {
            out = new PrintStream(outputFilePath);
        } catch (FileNotFoundException e1) {
            System.out.println("Output file " + outputFilePath + " does not exists. Attempting to create it...");
            File f = new File(outputFilePath);
            f.mkdirs();
            f.createNewFile();
        }
        out.println("<html>");
        out.println("<head>");
        out.println("<link rel='stylesheet' href='style.css' type='text/css'>");

        out.println("</head>");
        out.println("<body>");

        DateFormat monthDateFormat = new SimpleDateFormat(PropertyReader.getInstance().getProperty("month.format"));
        DateFormat dayDateFormat = new SimpleDateFormat(PropertyReader.getInstance().getProperty("day.format"));
        String month = null;
        Iterator<Date> i = new DateIterator(startDt, endDt);
        out.println("<center>");
        //out.println("<TABLE BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=100%>");
        out.println("<table class=\"TABLE\">");
        boolean firstDayOfMonth = false;
        while (i.hasNext()) {
            Date date = i.next();
            CalendarDay day;
            try {
                day = parser.getDateInfo(date);
                String mnth = monthDateFormat.format(date);
                if (month == null || !month.equals(mnth)) {
                    printMonth(mnth + " - " + day.getYear());
                    month = mnth;
                    firstDayOfMonth = true;
                } else {
                    firstDayOfMonth = false;
                }
                if (firstDayOfMonth) {
                    out.println("<tr>");
                    int dayNumberOfWeek = getDayNumberOfWeek(date);
                    for (int j = 0; j < dayNumberOfWeek; j++) {
                        out.println("<td></td>");
                    }
                } else if (dayDateFormat.format(date).equals("Sunday")) {
                    out.println("<tr>");
                }
                printDay(day, date);
                if (dayDateFormat.format(date).equals("Saturday")) {
                    out.println("</tr>");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        out.println("</table>");
        out.println("</center>");
        out.println("</body>");
        out.println("</html>");
    }

    //    public static void main(String[] args) throws IOException, ParseException {
    //        PrintHtmlCalendar pc = null;
    //        try {
    //            pc = new PrintHtmlCalendar("2012-13.VCC");
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        pc.print("01-01-2013", "31-01-2013", "./text/cal.html");
    //
    //    }

}
