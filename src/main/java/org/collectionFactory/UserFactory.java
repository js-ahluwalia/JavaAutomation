package org.collectionFactory;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;


public class UserFactory extends MakeApiCall {

//    public static void signIn(JSONObject userData) throws IOException,InterruptedException{
//        makeApiCall.signIn(userData);
//    }
//
//    public static void createUser(JSONObject userData) throws IOException, InterruptedException {
//        makeApiCall.createUser(userData);
//    }
//    public static void createOnBoardedUserWithReader(JSONObject userData) throws IOException, InterruptedException{
//        makeApiCall.createOnBoardedUserWithReader(userData);
//    }
//    public static void createBBT(JSONObject userData) throws IOException, InterruptedException{
//        makeApiCall.createBBT(userData);
//    }
//    public static void createTest(JSONObject userData) throws IOException, InterruptedException{
//        makeApiCall.createTest(userData);
//    }
//    public static void onboardedFalseReaderFalse (JSONObject userData ) throws IOException, InterruptedException{
//        makeApiCall.createUser(userData);
//    }
//    public static void onboardedFalseReaderTrue (JSONObject userData) throws IOException, InterruptedException{
//        makeApiCall.onboardedFalseReaderTrue(userData);
//    }

    public static String toJsonString(String[] array) {
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


    public static void main(String[] args) throws InterruptedException, IOException {

            JSONObject userData = new JSONObject();
            userData.put("username", "9rt@test.com");
            userData.put("password", "12345678");
            String[] periodDates = {
                    "2024-05-01", "2024-05-02", "2024-05-03",
                    "2024-05-04", "2024-06-01", "2024-06-02",
                    "2024-06-03", "2024-06-04"
            };
            userData.put("reader_code", "2C85G4DE");
            userData.put("period_dates",toJsonString(periodDates));
            userData.put("date_bbt","2023-03-02");
            userData.put("bbt_value","100");
            userData.put("test[images_attributes][][pic]" ,upload("original_image_AEF.png"));
            userData.put("answer_trying_to_get_pregnant", "pregnant" );
            userData.put("answer_want_to_track_fertility_level", "yes" );
            userData.put("pregnancy_positive_date", "2023-08-20" );
            userData.put("gestation_days", "5" );
            userData.put("end_date", "2024-05-28" );
            userData.put("test[done_date]", "2024-09-05" );
//            createUser(userData);
//            signIn(userData);
//            onboardedTTCUserReaderTrue(userData);
//        ***************************** calling methods *******************************************

        List<String> response = generateUserWithState("onboardedPregnancyUserReaderFalse");
        System.out.println(response);

    }

}
