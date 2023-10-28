package com.fb.facebook.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Utils {
    public static Gender getGenderFromString(String type) {
        if(type.equalsIgnoreCase("male")) {
            return Gender.MALE;
        } else if(type.equalsIgnoreCase("female")) {
            return Gender.FEMALE;
        } else {
            return Gender.OTHERS;
        }
    }

    public static String getAvatarFromGender(Gender gender) {
        if(gender == Gender.MALE) {
            // 🧑🏽‍ 👩🏽‍🦱 🧑🏽‍🦱 👩🏽‍
            return "\uD83E\uDDD1\uD83C\uDFFD\u200D\uD83E\uDDB1";
        } else if(gender == Gender.FEMALE) {
            return "\uD83D\uDC69\uD83C\uDFFD\u200D\uD83E\uDDB1";
        } else {
            return "\uD83E\uDDD1\uD83C\uDFFD\u200D";
        }
    }

    public static String formatDateTime(String inputDateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        LocalDateTime inputDateTime = LocalDateTime.parse(inputDateTimeString, formatter);
        LocalDateTime currentDateTime = LocalDateTime.now();

        long yearsDiff = ChronoUnit.YEARS.between(inputDateTime, currentDateTime);
        long monthsDiff = ChronoUnit.MONTHS.between(inputDateTime, currentDateTime);
        long daysDiff = ChronoUnit.DAYS.between(inputDateTime, currentDateTime);
        long hoursDiff = ChronoUnit.HOURS.between(inputDateTime, currentDateTime);
        long minutesDiff = ChronoUnit.MINUTES.between(inputDateTime, currentDateTime);

        if (yearsDiff == 1) {
            return yearsDiff + " yr ago";
        } else if (yearsDiff > 0) {
            return yearsDiff + " yrs ago";
        } else if (monthsDiff == 1) {
            return monthsDiff + " month ago";
        } else if (monthsDiff > 0) {
            return monthsDiff + " months ago";
        } else if (daysDiff == 1) {
            return daysDiff + " day ago";
        } else if (daysDiff > 0) {
            return daysDiff + " days ago";
        } else if (hoursDiff == 1) {
            return hoursDiff + " hr ago";
        } else if (hoursDiff > 0) {
            return hoursDiff + " hr ago";
        } else if (minutesDiff > 0) {
            return minutesDiff + " mins ago";
        } else {
            return "Just now";
        }
    }
}
