package CinemaAPP;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class IdGenerator {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
    private static final Pattern BOOKING_PATTERN = Pattern.compile("^BK-(\\d{8})-(\\d{4})$");
    private static final Pattern SHOWTIME_MOVIE_PATTERN = Pattern.compile(".*-M(\\d+)$");

    private IdGenerator() {
    }

    public static String generateNextBookingId(List<TicketData> existingBookings) {
        String today = LocalDate.now().format(DATE_FORMAT);
        int maxRunningNo = 0;

        for (TicketData booking : existingBookings) {
            String bookingId = booking.getBookingId();
            if (bookingId == null) {
                continue;
            }

            Matcher matcher = BOOKING_PATTERN.matcher(bookingId.trim());
            if (matcher.matches() && today.equals(matcher.group(1))) {
                int runningNo = Integer.parseInt(matcher.group(2));
                if (runningNo > maxRunningNo) {
                    maxRunningNo = runningNo;
                }
            }
        }

        return String.format("BK-%s-%04d", today, maxRunningNo + 1);
    }

    public static String generateShowtimeId(String hall, String time, int movieId) {
        String date = LocalDate.now().format(DATE_FORMAT);
        String hhmm = normalizeTimeToHHMM(time);
        int hallNumber = extractHallNumber(hall);
        return String.format("ST-%s-%s-H%d-M%d", date, hhmm, hallNumber, movieId);
    }

    public static int extractMovieIdFromShowtimeId(String showtimeId) {
        if (showtimeId == null || showtimeId.trim().isEmpty()) {
            return -1;
        }

        Matcher matcher = SHOWTIME_MOVIE_PATTERN.matcher(showtimeId.trim());
        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(1));
        }

        return -1;
    }

    private static int extractHallNumber(String hall) {
        if (hall == null || hall.trim().isEmpty()) {
            return 0;
        }

        String digits = hall.replaceAll("\\D+", "");
        if (!digits.isEmpty()) {
            return Integer.parseInt(digits);
        }

        return 0;
    }

    private static String normalizeTimeToHHMM(String time) {
        if (time == null || time.trim().isEmpty()) {
            return "0000";
        }

        String value = time.trim().toUpperCase(Locale.ENGLISH);

        try {
            LocalTime parsed = LocalTime.parse(value, TIME_FORMAT);
            return parsed.format(DateTimeFormatter.ofPattern("HHmm"));
        } catch (DateTimeParseException ignored) {
        }

        String digits = value.replaceAll("\\D+", "");
        if (digits.length() >= 4) {
            return digits.substring(0, 4);
        }
        if (digits.length() == 3) {
            return "0" + digits;
        }
        if (digits.length() == 2) {
            return digits + "00";
        }
        if (digits.length() == 1) {
            return "0" + digits + "00";
        }

        return "0000";
    }
}