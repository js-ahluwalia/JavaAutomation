package org.collectionFactory;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class MakeApiCall extends HelperMethods{
   static String envFileName =  ConfigReader.getProperty("ENVFILENAME");
    static String envFilePath =  ConfigReader.getProperty("ENVDIRNAME")+"/"+envFileName; // Adjust the path as needed

    static HitAPI hitAPI = new HitAPI();
    static ReplaceValueForAKey replace = new ReplaceValueForAKey();

    public static void createUser(JSONObject userData) throws IOException, InterruptedException {
        JSONObject attributes = new JSONObject();
        attributes.put("name", ConfigReader.getProperty("NAME"));
        attributes.put("phone", ConfigReader.getProperty("PHONE"));
        attributes.put("email", userData.getString("username"));
        String json = attributes.toString();
        byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8);
        String base64Encoded = Base64.getEncoder().encodeToString(jsonBytes);
       replace.replaceValue("base64encodedAttribute",base64Encoded,envFilePath);
       replace.replaceValue("username",userData.getString("username"),envFilePath);
       replace.replaceValue("password",userData.getString("password"),envFilePath);
       hitAPI.runApi("createUser", envFileName);
    }

    public static void signIn(JSONObject userData) throws InterruptedException {

        replace.replaceValue("username", userData.getString("username"), envFilePath);
        replace.replaceValue("password", userData.getString("password"), envFilePath);
        hitAPI.runApi("signIn",envFileName);
    }

    public static void createOnBoardedUserWithReader(JSONObject userData) throws InterruptedException {

        replace.replaceValue("reader_code",userData.getString("reader_code"),envFilePath);
        replace.replaceValue("period_dates", userData.getString("period_dates"),envFilePath);
        //  "[\"2024-05-01\", \"2024-05-02\", \"2024-05-03\", \"2024-05-04\", \"2024-06-01\", \"2024-06-02\", \"2024-06-03\", \"2024-06-04\"]\n";
        hitAPI.runApi("createOnboardedUserWithReader",envFileName);
    }


    public static void createBBT(JSONObject userData) throws InterruptedException{

        replace.replaceValue("period_dates", userData.getString("period_dates"),envFilePath);
        replace.replaceValue("reader_code",userData.getString("reader_code"),envFilePath);
        replace.replaceValue("date_bbt", userData.getString("date_bbt"), envFilePath);
        replace.replaceValue("bbt_value", userData.getString("bbt_value"), envFilePath);
        hitAPI.runApi("createBBT",envFileName);
    }

    public static void createTest(JSONObject userData) throws InterruptedException{

        CollectionModifier collectionReplacer = new CollectionModifier();
        collectionReplacer.replaceValue("test[images_attributes][][pic]", userData.getString("test[images_attributes][][pic]"), "createTest.postman_collection.json");
        replace.replaceValue("test[done_date]", userData.getString("test[done_date]"),envFilePath);
        replace.replaceValue("period_dates", userData.getString("period_dates"),envFilePath);
        replace.replaceValue("reader_code",userData.getString("reader_code"),envFilePath);
        hitAPI.runApi("createTest",envFileName);
    }

    public static void onboardedFalseReaderTrue(JSONObject userData) throws InterruptedException{

        replace.replaceValue("period_dates", userData.getString("period_dates"),envFilePath);
        hitAPI.runApi("onboardedFalseReaderTrue",envFileName);
    }
    public static void onboardedFalseReaderFalse(JSONObject userData) throws IOException, InterruptedException {

        replace.replaceValue("username",userData.getString("username"),envFilePath);
        replace.replaceValue("password",userData.getString("password"),envFilePath);
        hitAPI.runApi("createUser", envFileName);
    }

    public static void onboardedNonTTCFertilityRatingUserReaderFalse() throws IOException, InterruptedException {
        hitAPI.runApi("onboardedNonTTCFertilityRatingUserReaderFalse", envFileName);
    }
    public static void onboardedNonTTCFertilityRatingUserReaderTrue() throws IOException, InterruptedException {
        hitAPI.runApi("onboardedNonTTCFertilityRatingUserReaderTrue", envFileName);
    }
    public static void onboardedNonTTCHormoneOnlyUserReaderFalse() throws IOException, InterruptedException {
        hitAPI.runApi("onboardedNonTTCHormoneOnlyUserReaderFalse", envFileName);
    }
    public static void onboardedNonTTCHormoneOnlyUserReaderTrue() throws IOException, InterruptedException {
        hitAPI.runApi("onboardedNonTTCHormoneOnlyUserReaderTrue", envFileName);
    }
    public static void onboardedPregnancyUserReaderFalse(JSONObject userData) throws IOException, InterruptedException {
        replace.replaceValue("period_dates", userData.getString("period_dates"),envFilePath);
        hitAPI.runApi("onboardedPregnancyUserReaderFalse", envFileName);
    }
    public static void onboardedPregnancyUserReaderTrue(JSONObject userData) throws IOException, InterruptedException {
        replace.replaceValue("period_dates", userData.getString("period_dates"),envFilePath);
        hitAPI.runApi("onboardedPregnancyUserReaderTrue", envFileName);
    }
    public static void onboardedTTCUserReaderFalse() throws IOException, InterruptedException {
        hitAPI.runApi("onboardedTTCUserReaderFalse", envFileName);
    }
    public static void onboardedTTCUserReaderTrue() throws IOException, InterruptedException {
        hitAPI.runApi("onboardedTTCUserReaderTrue", envFileName);
    }


    public static List<String> generateUserWithState(String state) throws IOException, InterruptedException {

        List<String> credentials = new ArrayList<String>();
        String username = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "@test.com";
        String password = ConfigReader.getProperty("PASSWORD");
        JSONObject userData = new JSONObject();
        userData.put("username", username);
        userData.put("password", password);
        System.out.println(checkStatus(envFileName));
        createUser(userData);
        signIn(userData);
        String[] periodDates=generatePeriodDates();
        userData.put("period_dates",toJsonString(periodDates));
        userData.put("reader_code", ConfigReader.getProperty("READER_CODE"));
        credentials.add("username: " + username);
        credentials.add("password: " + password);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        switch (state) {

            case "createOnBoardedUserWithReader":
                   createOnBoardedUserWithReader(userData);
                   if(checkStatus(envFileName)) {
                       return credentials;
                   }
                   else{
                       credentials.clear();
                       credentials.add("Some API in the collection failed to return OK response");
                        return credentials;
                   }


            case "createBBT":
                userData.put("date_bbt",formattedDate);
                userData.put("bbt_value","98");

                    createBBT(userData);
                    if(checkStatus(envFileName)) {
                        return credentials;
                    }
                    else{
                        credentials.clear();
                        credentials.add("Some API in the collection failed to return OK response");
                        return credentials;
                    }


            case "createTest":
                userData.put("test[images_attributes][][pic]" ,upload("original_image_AEF.png"));
                userData.put("test[done_date]", formattedDate);
                    createTest(userData);
                if(checkStatus(envFileName)) {
                    return credentials;
                }
                else{
                    credentials.clear();
                    credentials.add("Some API in the collection failed to return OK response");
                    return credentials;
                }

            case "onboardedFalseReaderTrue":

                    onboardedFalseReaderTrue(userData);
                if(checkStatus(envFileName)) {
                    return credentials;
                }
                else{
                    credentials.clear();
                    credentials.add("Some API in the collection failed to return OK response");
                    return credentials;
                }

            case "onboardedFalseReaderFalse":
                    return credentials;


            case "onboardedNonTTCFertilityRatingUserReaderFalse":
                onboardedNonTTCFertilityRatingUserReaderFalse();
                if(checkStatus(envFileName)) {
                    return credentials;
                }
                else{
                    credentials.clear();
                    credentials.add("Some API in the collection failed to return OK response");
                    return credentials;
                }


            case  "onboardedNonTTCFertilityRatingUserReaderTrue":
                onboardedNonTTCFertilityRatingUserReaderTrue();
                if(checkStatus(envFileName)) {
                    return credentials;
                }
                else{
                    credentials.clear();
                    credentials.add("Some API in the collection failed to return OK response");
                    return credentials;
                }

            case  "onboardedNonTTCHormoneOnlyUserReaderFalse":
                onboardedNonTTCHormoneOnlyUserReaderFalse();
                if(checkStatus(envFileName)) {
                    return credentials;
                }
                else{
                    credentials.clear();
                    credentials.add("Some API in the collection failed to return OK response");
                    return credentials;
                }

            case  "onboardedNonTTCHormoneOnlyUserReaderTrue":
                onboardedNonTTCHormoneOnlyUserReaderTrue();
                if(checkStatus(envFileName)) {
                    return credentials;
                }
                else{
                    credentials.clear();
                    credentials.add("Some API in the collection failed to return OK response");
                    return credentials;
                }

            case  "onboardedPregnancyUserReaderFalse":

                periodDates=generatePregnancyPeriodDates();
                userData.put("period_dates",toJsonString(periodDates));
                onboardedPregnancyUserReaderFalse(userData);


                if(checkStatus(envFileName)) {
                    return credentials;
                }
                else{
                    credentials.clear();
                    credentials.add("Some API in the collection failed to return OK response");
                    return credentials;
                }

            case  "onboardedPregnancyUserReaderTrue":
                periodDates=generatePregnancyPeriodDates();
                userData.put("period_dates",toJsonString(periodDates));
                onboardedPregnancyUserReaderTrue(userData);
                if(checkStatus(envFileName)) {
                    return credentials;
                }
                else{
                    credentials.clear();
                    credentials.add("Some API in the collection failed to return OK response");
                    return credentials;
                }

            case  "onboardedTTCUserReaderFalse":
                onboardedTTCUserReaderFalse();
                if(checkStatus(envFileName)) {
                    return credentials;
                }
                else{
                    credentials.clear();
                    credentials.add("Some API in the collection failed to return OK response");
                    return credentials;
                }

            case  "onboardedTTCUserReaderTrue":
                onboardedTTCUserReaderTrue();
                if(checkStatus(envFileName)) {
                    return credentials;
                }
                else{
                    credentials.clear();
                    credentials.add("Some API in the collection failed to return OK response");
                    return credentials;
                }
        }
        credentials.clear();
        credentials.add("invalid method name ");
        return credentials;
    }


}
