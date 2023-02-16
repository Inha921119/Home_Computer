import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
	public static String getNowDateTime() {
		LocalDateTime nowdatetime = LocalDateTime.now();
		String NowDateTime = nowdatetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		return NowDateTime;
	}
}