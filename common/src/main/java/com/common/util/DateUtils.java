package com.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class DateUtils {
	private static final int[] DAY_OF_MONTH = new int[]{31, 28, 31, 30, 31, 30,
			31, 31, 30, 31, 30, 31};

	public static Date addDay(Date date, int dayAmount) {
		if (date == null) {
			return null;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(5, dayAmount);
			return calendar.getTime();
		}
	}

	public static Date addHour(Date date, int hourAmount) {
		if (date == null) {
			return null;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(10, hourAmount);
			return calendar.getTime();
		}
	}

	public static Date addMinute(Date date, int minuteAmount) {
		if (date == null) {
			return null;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(12, minuteAmount);
			return calendar.getTime();
		}
	}

	public static int compareHourAndMinute(Date date, Date anotherDate) {
		if (date == null) {
			date = new Date();
		}

		if (anotherDate == null) {
			anotherDate = new Date();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hourOfDay1 = cal.get(11);
		int minute1 = cal.get(12);
		cal.setTime(anotherDate);
		int hourOfDay2 = cal.get(11);
		int minute2 = cal.get(12);
		return hourOfDay1 > hourOfDay2 ? 1 : (hourOfDay1 == hourOfDay2
				? (minute1 > minute2 ? 1 : (minute1 == minute2 ? 0 : -1))
				: -1);
	}

	public static int compareIgnoreSecond(Date date, Date anotherDate) {
		if (date == null) {
			date = new Date();
		}

		if (anotherDate == null) {
			anotherDate = new Date();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(13, 0);
		cal.set(14, 0);
		date = cal.getTime();
		cal.setTime(anotherDate);
		cal.set(13, 0);
		cal.set(14, 0);
		anotherDate = cal.getTime();
		return date.compareTo(anotherDate);
	}

	public static String currentDate2String() {
		return date2String(new Date());
	}

	public static String currentDate2StringByDay() {
		return date2StringByDay(new Date());
	}

	public static Date currentEndDate() {
		return getEndDate(new Date());
	}

	public static Date currentStartDate() {
		return getStartDate(new Date());
	}

	public static String date2String(Date date) {
		return date2String(date, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	public static String date2String(Date date, String pattern) {
		return date == null ? null : (new SimpleDateFormat(pattern))
				.format(date);
	}

	public static String date2StringByDay(Date date) {
		return date2String(date, "yyyy-MM-dd");
	}

	public static String date2StringByMinute(Date date) {
		return date2String(date, "yyyy-MM-dd HH:mm");
	}

	public static String date2StringBySecond(Date date) {
		return date2String(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String getChineseWeekNumber(String englishWeekName) {
		return "monday".equalsIgnoreCase(englishWeekName) ? "一" : ("tuesday"
				.equalsIgnoreCase(englishWeekName) ? "二" : ("wednesday"
				.equalsIgnoreCase(englishWeekName) ? "三" : ("thursday"
				.equalsIgnoreCase(englishWeekName) ? "四" : ("friday"
				.equalsIgnoreCase(englishWeekName) ? "五" : ("saturday"
				.equalsIgnoreCase(englishWeekName) ? "六" : ("sunday"
				.equalsIgnoreCase(englishWeekName) ? "日" : null))))));
	}

	public static Date getDate(int year, int month, int date) {
		return getDate(year, month, date, 0, 0);
	}

	public static Date getDate(int year, int month, int date, int hourOfDay,
			int minute) {
		return getDate(year, month, date, hourOfDay, minute, 0);
	}

	public static Date getDate(int year, int month, int date, int hourOfDay,
			int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, date, hourOfDay, minute, second);
		cal.set(14, 0);
		return cal.getTime();
	}

	public static int getDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(7);
	}

	public static Date getEndDate(Date date) {
		if (date == null) {
			return null;
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(11, 23);
			cal.set(12, 59);
			cal.set(13, 59);
			cal.set(14, 999);
			return cal.getTime();
		}
	}

	public static int getMaxDayOfMonth(int year, int month) {
		return month == 1 && isLeapYear(year) ? 29 : DAY_OF_MONTH[month];
	}

	public static Date getNextDay(Date date) {
		return addDay(date, 1);
	}

	public static Date getStartDate(Date date) {
		if (date == null) {
			return null;
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(11, 0);
			cal.set(12, 0);
			cal.set(13, 0);
			cal.set(14, 0);
			return cal.getTime();
		}
	}

	public static String getTime(Date date) {
		if (date == null) {
			return null;
		} else {
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			return format.format(date);
		}
	}

	public static String getTimeIgnoreSecond(Date date) {
		if (date == null) {
			return null;
		} else {
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			return format.format(date);
		}
	}

	public static boolean isLeapYear(int year) {
		Calendar calendar = Calendar.getInstance();
		return ((GregorianCalendar) calendar).isLeapYear(year);
	}

	public static Date string2Date(String str) {
		return string2Date(str, "yyyy-MM-dd");
	}

	public static Date string2Date(String str, String pattern) {
		if (ValidatorUtils.isEmpty(str)) {
			return null;
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			Date date = null;

			try {
				date = dateFormat.parse(str);
			} catch (ParseException arg4) {
				;
			}

			return date;
		}
	}

	public static Date string2DateTime(String str) {
		return string2Date(str, "yyyy-MM-dd HH:mm:ss");
	}

	public static int getWeekOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(3);
	}

	public static Date getDateOfPreviousWeek(int dayOfWeek) {
		if (dayOfWeek <= 7 && dayOfWeek >= 1) {
			return getDateOfRange(dayOfWeek, -7);
		} else {
			throw new IllegalArgumentException("参数必须是1-7之间的数字");
		}
	}

	public static Date getDateOfCurrentWeek(int dayOfWeek) {
		if (dayOfWeek <= 7 && dayOfWeek >= 1) {
			return getDateOfRange(dayOfWeek, 0);
		} else {
			throw new IllegalArgumentException("参数必须是1-7之间的数字");
		}
	}

	public static Date getDateOfNextWeek(int dayOfWeek) {
		if (dayOfWeek <= 7 && dayOfWeek >= 1) {
			return getDateOfRange(dayOfWeek, 7);
		} else {
			throw new IllegalArgumentException("参数必须是1-7之间的数字");
		}
	}

	private static Date getDateOfRange(int dayOfWeek, int dayOfRange) {
		Calendar cal = Calendar.getInstance();
		cal.set(7, dayOfWeek);
		cal.set(5, cal.get(5) + dayOfRange);
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);
		return cal.getTime();
	}
}