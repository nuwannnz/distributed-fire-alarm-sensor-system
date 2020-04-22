import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateHelper {
    public static LocalDate getDateFromString(String dateStr){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(dateStr, formatter);
        return date;
    }
}
