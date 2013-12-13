package vcal.util;

import java.util.Calendar;
import java.util.Date;

public class Utility {

    public static Date dateAddDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static enum TimeEnum {
        startofday("00:00:00 AM"),
        noon("12:00:00 PM"),
        midnight("11:59:59 PM"),
        dusk("07:00:00 PM"),
        dawn("06:00:00 AM"),
        moonrise("08:30:00 PM"),
        sunset("07:00:00 PM");

        private String time;

        TimeEnum(String t) {
            time = t;
        }

        public String getTime() {
            return time;
        }
    }

    public static enum MonthEnum {
        Visnu("Chaitra"), Madhusudana("Vaishakh"), Trivikrama("Jyaistha"), Vamana("Ashadh"), Sridhara("Shravan"), Hrsikesa(
                "Bhadrapad"), Padmanabha("Ashvin"), Damodara("Kartik"), Kesava("Margashirsa"), Narayana("Paus"), Madhava(
                "Magh"), Govinda("Phalgun"), Purusottama("Adhik");

        private String hindiName;

        MonthEnum(String m) {
            hindiName = m;
        }

        public String getHindiName() {
            return hindiName;
        }
    }

    public static String getMonthName(String monthCode) {
        return monthCode + " (" + MonthEnum.valueOf(monthCode).getHindiName() + ")";
    }

    public static enum EventType {
        FastingPartial, FastingFull, BreakFast, Sankranti, Caturmasya, AdhikMasa, Normal;

    }
}
