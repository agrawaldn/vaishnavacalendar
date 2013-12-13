package vcal.print;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;

import vcal.bean.CalendarDay;
import vcal.bean.Event;
import vcal.bean.OutlookEvent;
import vcal.parse.ParseCalendar;
import vcal.util.DateIterator;
import vcal.util.HeaderDetail;
import vcal.util.PropertyReader;
import vcal.util.Utility;

public class PrintCSVCalendar
        implements IPrintCalender {
    PrintStream out;
    ParseCalendar parser;
    Date startDt;
    Date endDt;
    private static String outlook_reminder_time = PropertyReader.getInstance().getProperty("outlook.reminder.time");

    public PrintCSVCalendar(String inputFilePath) throws IOException, ParseException, Exception {
        parser = new ParseCalendar(inputFilePath);
    }

    private void printDay(CalendarDay day, Date date) {
        if (day.getTithi().contains("Purnima")) {
            printEvent(date, new Event(day.getTithi()));
        }
        if (day.isFirstDayOfMonth()) {
            Event event = new Event(day.getMonth());
            if (day.getMonthCode().equals(Utility.MonthEnum.Purusottama.name())) {
                event.setEventType(Utility.EventType.AdhikMasa);
            }
            printEvent(date, event);
        }
        if (day.getEventList().size() > 0) {
            for (Event event : day.getEventList()) {
                printEvent(date, event);
            }
        }
    }

    private void printEvent(Date date, Event event) {
        OutlookEvent Oevent = new OutlookEvent();
        Oevent.setSubject(event.getEventName());
        Oevent.setDescription(event.getEventDescription());
        Oevent.setStartDate(date);
        Oevent.setEndDate(date);
        Oevent.setAllDayEvent("FALSE");

        if (event.getEventType().equals(Utility.EventType.FastingFull)) {
            setReminderDateTime(Oevent, Utility.dateAddDays(date, -1), outlook_reminder_time);
            Oevent.setAllDayEvent("TRUE");
        } else if (event.getEventType().equals(Utility.EventType.Caturmasya)) {
            setReminderDateTime(Oevent, Utility.dateAddDays(date, -1), outlook_reminder_time);
            Oevent.setAllDayEvent("TRUE");
        } else if (event.getEventType().equals(Utility.EventType.AdhikMasa)) {
            setReminderDateTime(Oevent, Utility.dateAddDays(date, -1), outlook_reminder_time);
        } else if (event.getEventType().equals(Utility.EventType.FastingPartial)) {
            setReminderDateTime(Oevent, Utility.dateAddDays(date, -1), outlook_reminder_time);
        } else if (event.getEventType().equals(Utility.EventType.BreakFast)) {
            setEventTimeForBreakFast(event);
            setReminderDateTime(Oevent, date, event.getStartTime());
        } else {
            Oevent.setAllDayEvent("TRUE");
            Oevent.setReminderOnOff("FALSE");

        }
        Oevent.setStartTime(event.getStartTime());
        Oevent.setEndTime(event.getEndTime());
        Oevent.setPriority("Normal");
        Oevent.setPrivateFlag("TRUE");
        Oevent.setSensitivity("Private");
        Oevent.setShowTimeAs("3");

        out.println(Oevent.toString());
    }

    private void setReminderDateTime(OutlookEvent Oevent, Date date, String time) {
        Oevent.setReminderOnOff("TRUE");
        Oevent.setReminderDate(date);
        Oevent.setReminderTime(time);
    }

    private void setEventTimeForBreakFast(Event event) {
        String start = event.getStartTime();
        String end = event.getEndTime();
        String ampm = "AM";
        String startHour = start.split(":")[0];
        String endHour = end.split(":")[0];
        if (Integer.parseInt(startHour) >= 12) {
            ampm = "PM";
        }
        event.setStartTime(start + ":00 " + ampm);
        if (Integer.parseInt(endHour) >= 12) {
            ampm = "PM";
        }
        event.setEndTime(end + ":00 " + ampm);
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
            out = new PrintStream(outputFilePath);
        }

        //Print header
        int size = HeaderDetail.values().length;
        for (int j = 1; j <= size; j++) {
            out.print("\"" + HeaderDetail.getLabel(j) + "\"");
            if (j < size) {
                out.print(PropertyReader.getInstance().getProperty("outlook.delim"));
            } else {
                out.print("\n");
            }
        }
        //iterate through dates and print calendar
        Iterator<Date> i = new DateIterator(startDt, endDt);
        while (i.hasNext()) {
            Date date = i.next();
            CalendarDay day = null;
            try {
                day = parser.getDateInfo(date);
                printDay(day, date);
            } catch (Exception e) {
                e.printStackTrace();
                //System.exit(1);
            }
        }
    }

}
