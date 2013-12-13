package vcal.print;

import java.io.IOException;
import java.util.Date;

public interface IPrintCalender {
    public void print(Date startDate, Date endDate, String outputFilePath) throws IOException, Exception;
}
