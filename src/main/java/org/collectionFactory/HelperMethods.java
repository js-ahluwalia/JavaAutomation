package org.collectionFactory;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HelperMethods {
    static String envFileName =  ConfigReader.getProperty("ENVFILENAME");
    static String envFilePath =  ConfigReader.getProperty("ENVDIRNAME")+"/"+envFileName;

    public static String add15Days(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        LocalDate newDate = localDate.plusDays(15);
        return newDate.format(formatter);
    }

    public static boolean checkStatus(String environment) throws InterruptedException {
        ReadValueOfPostManEnvironment readEnvValue = new ReadValueOfPostManEnvironment();
        int statusCode= Integer.parseInt(readEnvValue.readValueOfPostManEnvironmentKey("status_of_response",environment));
        if(statusCode>=200 && statusCode<300){
            return true;
        }
        else{
            return false;
        }

    }
    public static String toJsonString(String[] array) {
        ReplaceValueForAKey replace = new ReplaceValueForAKey();
        String newDate = add15Days(array[4]);

        replace.replaceValue("last_period_date", array[4], envFilePath);
        replace.replaceValue("pregnancy_positive_date",newDate,envFilePath);
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < array.length; i++) {
            sb.append("\"").append(array[i]).append("\"");
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }

        sb.append("]\n");
        return sb.toString();
    }
    public static String pwd() {

        Path currentDirectoryPath = FileSystems.getDefault().getPath("");
        String currentDirectoryName = currentDirectoryPath.toAbsolutePath().toString();
        return currentDirectoryName;
    }

    public static String upload(String imageName) {
        return pwd() + "/" + "uploads/" + imageName;
    }

    public static String[] generatePeriodDates() {
        List<String> periodDates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();
        LocalDate previousMonthDate = currentDate.minusMonths(1);
        for (int day = 1; day <= 4; day++) {
            periodDates.add(previousMonthDate.withDayOfMonth(day).format(formatter));
        }
        for (int day = 1; day <= 4; day++) {
            periodDates.add(currentDate.withDayOfMonth(day).format(formatter));
        }
        return periodDates.toArray(new String[0]);
    }
    public static String[] generatePregnancyPeriodDates() {
        List<String> periodDates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();
        LocalDate previousMonthDate = currentDate.minusMonths(1);
        LocalDate previousPreviousMonthDate = currentDate.minusMonths(2);

        for (int day = 1; day <= 4; day++) {
            periodDates.add(previousPreviousMonthDate.withDayOfMonth(day).format(formatter));
        }
        for (int day = 1; day <= 4; day++) {
            periodDates.add(previousMonthDate.withDayOfMonth(day).format(formatter));
        }
        return periodDates.toArray(new String[0]);
    }


}
