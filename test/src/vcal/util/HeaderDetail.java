package vcal.util;

import java.util.HashMap;
import java.util.Map;

public enum HeaderDetail {
    SUBJECT(1, "Subject"),
    STARTDATE(2, "Start Date"),
    STARTTIME(3, "Start Time"),
    ENDDATE(4, "End Date"),
    ENDTIME(5, "End Time"),
    ALLDAYEVENT(6, "All day event"),
    REMINDERONOFF(7, "Reminder on/off"),
    REMINDERDATE(8, "Reminder Date"),
    REMINDERTIME(9, "Reminder Time"),
    MEETINGORGANIZER(10, "Meeting Organizer"),
    REQUIREDATTENDEES(11, "Required Attendees"),
    OPTIONALATTENDEES(12, "Optional Attendees"),
    MEETINGRESOURCES(13, "Meeting Resources"),
    BILLINGINFORMATION(14, "Billing Information"),
    CATEGORIES(15, "Categories"),
    DESCRIPTION(16, "Description"),
    LOCATION(17, "Location"),
    MILEAGE(18, "Mileage"),
    PRIORITY(19, "Priority"),
    PRIVATEFLG(20, "Private"),
    SENSITIVITY(21, "Sensitivity"),
    SHOWTIMEAS(22, "Show time as");

    private int code;
    private String label;

    /**
     * A mapping between the integer code and its corresponding label to facilitate lookup by code.
     */
    private static Map<Integer, HeaderDetail> codeToStatusMapping;

    private HeaderDetail(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public static HeaderDetail getHeaderDetail(int i) {
        if (codeToStatusMapping == null) {
            initMapping();
        }
        HeaderDetail result = null;
        for (HeaderDetail s : values()) {
            result = codeToStatusMapping.get(i);
        }
        return result;
    }

    public static String getLabel(int i) {
        return getHeaderDetail(i).getLabel();
    }

    private static void initMapping() {
        codeToStatusMapping = new HashMap<Integer, HeaderDetail>();
        for (HeaderDetail s : values()) {
            codeToStatusMapping.put(s.code, s);
        }
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    //    public static void main(String[] args) {
    //        System.out.println(HeaderDetail.DESCRIPTION.getLabel());
    //        System.out.println(HeaderDetail.getLabel(1));
    //    }

}
