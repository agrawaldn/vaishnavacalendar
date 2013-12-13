package vcal.bean;

import vcal.util.Utility;

public class Event {

    private String eventName;
    private String eventDescription;
    private String startTime;
    private String endTime;
    //private boolean fasting;
    private Utility.EventType eventType;

    public Event(String name) {
        eventName = name;
        eventDescription = "";
        startTime = Utility.TimeEnum.valueOf("startofday").getTime();
        endTime = Utility.TimeEnum.valueOf("midnight").getTime();
        eventType = Utility.EventType.Normal;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription += eventDescription.replace("(", "").replace(")", "");
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    //    public void setFasting(boolean fasting) {
    //        this.fasting = fasting;
    //    }
    //
    //    public boolean isFasting() {
    //        return fasting;
    //    }

    public void setEventType(Utility.EventType eventType) {
        this.eventType = eventType;
    }

    public Utility.EventType getEventType() {
        return eventType;
    }

}
