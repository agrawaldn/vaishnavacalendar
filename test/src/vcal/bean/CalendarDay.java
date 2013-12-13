package vcal.bean;

import java.util.ArrayList;
import java.util.List;

public class CalendarDay {
    private String day;
    private String tithi;
    private String month;
    private String monthCode;
    private String year;
    private String paksha;
    private String yoga;
    private String nakshatra;
    private boolean fast;
    private List<Event> eventList;
    private boolean firstDayOfMonth;

    public CalendarDay() {
        eventList = new ArrayList<Event>();
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTithi() {
        return tithi;
    }

    public void setTithi(String tithi) {
        this.tithi = tithi;
    }

    public String getPaksha() {
        return paksha;
    }

    public void setPaksha(String paksha) {
        this.paksha = paksha;
    }

    public String getYoga() {
        return yoga;
    }

    public void setYoga(String yoga) {
        this.yoga = yoga;
    }

    public String getNakshatra() {
        return nakshatra;
    }

    public void setNakshatra(String nakshatra) {
        this.nakshatra = nakshatra;
    }

    public boolean getFast() {
        return fast;
    }

    public void setFast(boolean fast) {
        this.fast = fast;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void addEvent(Event event) {
        this.getEventList().add(event);
    }

    @Override
    public String toString() {
        String eventString = "";
        for (Event event : eventList) {
            if (eventString.length() > 0) {
                eventString += ";";
            }
            eventString += event.getEventName();
            //eventString += event.getStartTime();
            //eventString += event.getEndTime();
        }
        String ret = "Day=" + day + " Tithi=" + tithi + " Paksha=" + paksha + " Month=" + month + " Year=" + year
                + " Yoga=" + yoga + " Nakshatra=" + nakshatra + " Fast=" + fast + " Events=" + eventString;
        return ret;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonth() {
        return month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setFirstDayOfMonth(boolean firstDayOfMonth) {
        this.firstDayOfMonth = firstDayOfMonth;
    }

    public boolean isFirstDayOfMonth() {
        return firstDayOfMonth;
    }

    public void setMonthCode(String monthCode) {
        this.monthCode = monthCode;
    }

    public String getMonthCode() {
        return monthCode;
    }
}
