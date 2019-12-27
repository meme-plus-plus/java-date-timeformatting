
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class DateFormatExamplesApp {
	
	public static void main(String args[]) {
		
		final String UTC_TIMESTAMP = "2019-11-28T22:30:19Z";
		final String UTC_OFFSET_TIMESTAMP = "2019-11-28T22:30:19-06:00";
		final String NON_TIMEZONED_TIMESTAMP = "2019-11-28T22:30:19";
		
		try {
			
			//Legacy Time utilization: java.util: Date, Calendar, TimeZone (Primarily Java 7 and under)
			//The date string representation will follow the timeZone on the running JVM
			printResult("Java.util.Date",  UTC_TIMESTAMP, getUtilDate(UTC_TIMESTAMP).toString());
			
			//Java 8 Time API: java.time - loosely based on joda and preferred method if your application is on 1.8
			//Provides syntactical sugar, clarity, thread-safety and more.
			
			printResult("LocalDateTime -> ZonedDateTime", NON_TIMEZONED_TIMESTAMP,  getZonedDateTime(NON_TIMEZONED_TIMESTAMP).toString());
			printResult("OffsetDateTime ", UTC_OFFSET_TIMESTAMP,  getOffsetDateTime(UTC_OFFSET_TIMESTAMP).toString());
			printResult("Formatted date", UTC_OFFSET_TIMESTAMP,  getFormattedDate(UTC_OFFSET_TIMESTAMP));

			
			//Current UTC time stamp
			printResult("Instant", "Current Time", Instant.now().toString());

			
		} catch (Exception exe) {
			exe.printStackTrace();
		}
		
	}
	
	
	//java.util
	//You cannot interact with the time zone in Java Date; as it is defined by the JVM. The object only records the # of milliseconds since epoch time (January 1st, 1970).
	//It is through the use of formatters and other such objects that you can specify a time zone.
	public static Date getUtilDate(String dateString) throws ParseException  {
		SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return isoFormat.parse(dateString);
	}
	
	//LocalDatetime require that no timezone designators are assigned in the input. Otherwise a parse exceptions will be thrown
	//but you can define the timezone in which the date returns
	public static ZonedDateTime getZonedDateTime(String dateString) throws ParseException {
		return LocalDateTime.parse(dateString).atZone(TimeZone.getTimeZone("US/Central").toZoneId());
	}
	
	//A java.time object that stores the time zone offset
	public static OffsetDateTime getOffsetDateTime(String dateString) throws ParseException {
		return OffsetDateTime.parse(dateString);
	}
	

	//Because OffsetDateTime stores the time zone offset, you can return the value as a localized time format through DateTimeFormatter
	//This is the evolution of SimpleDateFormat/Date
	public static String getFormattedDate(String dateString) throws ParseException  {
		OffsetDateTime dateTime = OffsetDateTime.parse(dateString);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd'T'hh:mm:ss a");
				
		return dateTime.format(formatter);
	}
	
	public static void printResult(String message, String input, String result) {
		System.out.println("---------------------------------");
		System.out.println(message + " Input: [" + input + "] Result: [" + result +"]");
	}
	

}
