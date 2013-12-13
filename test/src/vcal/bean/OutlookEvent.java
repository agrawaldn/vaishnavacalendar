package vcal.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import vcal.util.HeaderDetail;
import vcal.util.PropertyReader;

public class OutlookEvent {

    private Map<String, String> eventDetailMap;
    DateFormat df = new SimpleDateFormat(PropertyReader.getInstance().getProperty("outlook.date.format"));

    public OutlookEvent() {
        eventDetailMap = new HashMap<String, String>();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        int size = HeaderDetail.values().length;
        for (int i = 1; i <= size; i++) {
            String value = "";
            if (eventDetailMap.containsKey(HeaderDetail.getLabel(i))) {
                value = eventDetailMap.get(HeaderDetail.getLabel(i));
            }
            sb.append("\"").append(value).append("\"");
            if (i < size) {
                sb.append(PropertyReader.getInstance().getProperty("outlook.delim"));
            }
        }
        return sb.toString();
    }

    public void setAllDayEvent(String allDayEvent) {
        eventDetailMap.put(HeaderDetail.ALLDAYEVENT.getLabel(), allDayEvent);
    }

    public void setBillingInformation(String billingInformation) {
        eventDetailMap.put(HeaderDetail.BILLINGINFORMATION.getLabel(), billingInformation);
    }

    public void setCategories(String categories) {
        eventDetailMap.put(HeaderDetail.CATEGORIES.getLabel(), categories);
    }

    public void setDescription(String description) {
        eventDetailMap.put(HeaderDetail.DESCRIPTION.getLabel(), description);
    }

    public void setEndDate(Date endDate) {
        eventDetailMap.put(HeaderDetail.ENDDATE.getLabel(), df.format(endDate));
    }

    public void setEndTime(String endTime) {
        eventDetailMap.put(HeaderDetail.ENDTIME.getLabel(), endTime);
    }

    public void setLocation(String location) {
        eventDetailMap.put(HeaderDetail.LOCATION.getLabel(), location);
    }

    public void setMeetingOrganizer(String meetingOrganizer) {
        eventDetailMap.put(HeaderDetail.MEETINGORGANIZER.getLabel(), meetingOrganizer);
    }

    public void setMeetingResources(String meetingResources) {
        eventDetailMap.put(HeaderDetail.MEETINGRESOURCES.getLabel(), meetingResources);
    }

    public void setMileage(String mileage) {
        eventDetailMap.put(HeaderDetail.MILEAGE.getLabel(), mileage);
    }

    public void setOptionalAttendees(String optionalAttendees) {
        eventDetailMap.put(HeaderDetail.OPTIONALATTENDEES.getLabel(), optionalAttendees);
    }

    public void setPriority(String priority) {
        eventDetailMap.put(HeaderDetail.PRIORITY.getLabel(), priority);
    }

    public void setPrivateFlag(String privateFlag) {
        eventDetailMap.put(HeaderDetail.PRIVATEFLG.getLabel(), privateFlag);
    }

    public void setReminderDate(Date reminderDate) {
        eventDetailMap.put(HeaderDetail.REMINDERDATE.getLabel(), df.format(reminderDate));
    }

    public void setReminderOnOff(String reminderOnOff) {
        eventDetailMap.put(HeaderDetail.REMINDERONOFF.getLabel(), reminderOnOff);
    }

    public void setReminderTime(String reminderTime) {
        eventDetailMap.put(HeaderDetail.REMINDERTIME.getLabel(), reminderTime);
    }

    public void setRequiredAttendees(String requiredAttendees) {
        eventDetailMap.put(HeaderDetail.REQUIREDATTENDEES.getLabel(), requiredAttendees);
    }

    public void setSensitivity(String sensitivity) {
        eventDetailMap.put(HeaderDetail.SENSITIVITY.getLabel(), sensitivity);
    }

    public void setShowTimeAs(String showTimeAs) {
        eventDetailMap.put(HeaderDetail.SHOWTIMEAS.getLabel(), showTimeAs);
    }

    public void setStartDate(Date startDate) {
        eventDetailMap.put(HeaderDetail.STARTDATE.getLabel(), df.format(startDate));
    }

    public void setStartTime(String startTime) {
        eventDetailMap.put(HeaderDetail.STARTTIME.getLabel(), startTime);
    }

    public void setSubject(String subject) {
        eventDetailMap.put(HeaderDetail.SUBJECT.getLabel(), subject);
    }

}
