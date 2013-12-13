package vcal.parse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import vcal.bean.CalendarDay;
import vcal.bean.Event;
import vcal.util.Utility;

public class ParseCalendar {

    BufferedReader in;
    PrintStream out;
    Map<String, CalendarDay> dataMap;

    public ParseCalendar(String inputFilePath) throws IOException, ParseException, Exception {
        if (inputFilePath == null || inputFilePath.length() < 1) {
            throw new Exception("Input file was not specified");
        }
        in = new BufferedReader(new FileReader(inputFilePath));
        dataMap = new HashMap<String, CalendarDay>();
        this.parse();
    }

    @SuppressWarnings("null")
    private void parse() throws IOException, ParseException, Exception {
        String line = null;
        CalendarDay calendarDay = null;
        String key = null;
        String month = null;
        String monthCode = null;
        String year = null;
        boolean firstDayOfMonth = false;
        Event event = null;
        while ((line = in.readLine()) != null) {
            if (line.matches("\\d+ .*") || line.matches(" \\d .*")) {
                if (key != null) {
                    calendarDay.setMonth(month);
                    calendarDay.setMonthCode(monthCode);
                    calendarDay.setYear(year);
                    dataMap.put(key, calendarDay);
                }
                key = line.substring(0, 11).trim(); //Sample key value: "11 May 2012"
                calendarDay = createCalendarDay(line);
                calendarDay.setFirstDayOfMonth(firstDayOfMonth);
                firstDayOfMonth = false;
            } else if (line.contains("Gaurabda")) {
                monthCode = line.split("Masa")[0].trim();
                monthCode = monthCode.replace("-adhika", "");
                month = Utility.getMonthName(monthCode) + " Month";
                year = line.split("Vcal")[0].split(",")[1].trim();
                firstDayOfMonth = true;
            } else if (line.contains("Sankranti")) {
                event = new Event(line.replace("-", " ").trim());
                event.setEventType(Utility.EventType.Sankranti);
                calendarDay.addEvent(event);
            } else if (!line.contains("Gaurabda") && !line.contains("Time zone") && !line.startsWith("DATE")
                    && !line.startsWith("--") && !(line.trim().length() < 1)) {
                //if line starts with ( then it is continuation of previous event ex. (Fasting till noon)
                if (line.trim().startsWith("(") || line.trim().startsWith("***")) {
                    setEventTimeBasedOnDescription(event, line.trim());
                    setEventType(event, calendarDay, line);
                } else if (line.trim().startsWith("Break fast")) {
                    event = new Event("Break fast");
                    event.setEventType(Utility.EventType.BreakFast);
                    setEventTimeForBreakFast(event, line.trim());

                    calendarDay.addEvent(event);
                } else {
                    event = new Event(line.trim());
                    setEventType(event, calendarDay, line);
                    calendarDay.addEvent(event);
                }
            }
        }
        in.close();
        //out.close();
    }

    private void setEventType(Event event, CalendarDay calendarDay, String line) {
        if ((line.trim().contains("Fasting") && calendarDay.getFast())) {
            if (event.getStartTime().equals(Utility.TimeEnum.valueOf("startofday").getTime())
                    && event.getEndTime().equals(Utility.TimeEnum.valueOf("midnight").getTime())) {
                event.setEventType(Utility.EventType.FastingFull);
            } else {
                event.setEventType(Utility.EventType.FastingPartial);
            }
        } else if (line.trim().contains("month of Caturmasya begins")) {
            event.setEventType(Utility.EventType.Caturmasya);
        }
    }

    private void setEventTimeBasedOnDescription(Event event, String line) {
        if (line.contains("Fasting till")) {
            event.setStartTime(Utility.TimeEnum.valueOf("startofday").getTime());
            String end = line.split("till")[1].replace(")", "").trim();
            event.setEndTime(Utility.TimeEnum.valueOf(end.replace(".", "")).getTime());
            event.setEventType(Utility.EventType.FastingFull);
        }
        event.setEventDescription(line);
    }

    private void setEventTimeForBreakFast(Event event, String line) {
        String start = line.split(" - ")[0].split(" ")[2];
        String end = line.split(" - ")[1].split(" ")[0];
        event.setStartTime(start);
        event.setEndTime(end);
        event.setEventDescription(line.split("\\(")[1]);
    }

    private CalendarDay createCalendarDay(String line) throws Exception {
        CalendarDay calendarDay = new CalendarDay();
        calendarDay.setDay(line.substring(12, 14).trim());
        calendarDay.setTithi(line.substring(16, 50).trim());
        calendarDay.setPaksha(line.substring(50, 51).trim());
        calendarDay.setYoga(line.substring(52, 62).trim());
        calendarDay.setNakshatra(line.substring(62, 78).trim());
        //String star = line.substring(78, line.length()).trim();
        boolean fast = false;
        if (line.trim().endsWith("*")) {
            fast = true;
        }
        calendarDay.setFast(fast);
        if (calendarDay.getPaksha() == null || calendarDay.getPaksha().trim().length() == 0) {
            if (line.contains(" G ")) {
                calendarDay.setPaksha("G");
            } else if (line.contains(" K ")) {
                calendarDay.setPaksha("K");
            } else {
                throw new Exception("Paksha is not parsable for " + calendarDay.toString());
            }
        }
        return calendarDay;
    }

    public String getTodaysInfo() throws Exception {
        Date today = new Date();
        CalendarDay calendarDay = getDateInfo(today);
        return calendarDay.toString();
    }

    public CalendarDay getDateInfo(Date date) throws Exception {
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        String searchKey = df.format(date);
        if (searchKey.startsWith("0")) {
            searchKey = searchKey.substring(1);
        }
        CalendarDay calendarDay = dataMap.get(searchKey);
        if (calendarDay == null) {
            throw new Exception("Input file povided does not contain data for " + searchKey);
        }
        return calendarDay;
    }

    //    public static void main(String[] args) throws IOException, ParseException {
    //        ParseCalendar parser = null;
    //        try {
    //            parser = new ParseCalendar(PropertyReader.getInstance().getProperty("input.path") + "MUM13-33.VCC");
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    //        Date testDate = df.parse("16-09-2013");
    //        //System.out.println(parser.getTodaysInfo());
    //        System.out.println(parser.getDateInfo(testDate).toString());
    //
    //    }

}
