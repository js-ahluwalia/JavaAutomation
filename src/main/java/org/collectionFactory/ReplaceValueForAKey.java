package org.collectionFactory;
import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;
//
//public class ReplaceValueForAKey {
//    String path="";
//    public void replaceValue(String key,String newValue,String environment) {
//        JSONParser parser = new JSONParser();
//        String valueToBeReplaced = null;
//        try {
//            Object obj = parser.parse(new FileReader(path+environment));
//
//            System.out.println(path+environment);
//            JSONObject jsonObject = (JSONObject) obj;
//            JSONArray subjects = (JSONArray) jsonObject.get("values");
//
//            Iterator iterator = subjects.iterator();
//            while (iterator.hasNext()) {
//                String h1 = iterator.next().toString();
//                if (h1.contains(key)) {
//                    Object obj2 = parser.parse(h1);
//                    JSONObject jsonObject2 = (JSONObject) obj2;
//                    valueToBeReplaced = jsonObject2.get("value").toString();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        replaceOldValue(environment, valueToBeReplaced, newValue);
//
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void replaceOldValue(String filePath, String valueToBeReplaced, String newValue)
//    {
//        System.out.println(valueToBeReplaced + ":" + newValue);
//        File fileToBeModified = new File(path+filePath);
//
//        String oldContent = "";
//
//        BufferedReader reader = null;
//
//        FileWriter writer = null;
//
//        try
//        {
//            reader = new BufferedReader(new FileReader(fileToBeModified));
//
//            //Reading all the lines of input text file into oldContent
//
//            String line = reader.readLine();
//
//            while (line != null)
//            {
//                oldContent = oldContent + line + System.lineSeparator();
//
//                line = reader.readLine();
//            }
//
//            //Replacing oldString with newString in the oldContent
//
//            String newContent = oldContent.replaceAll(valueToBeReplaced, newValue);
//
//            //Rewriting the input text file with newContent
//
//            writer = new FileWriter(fileToBeModified);
//
//            writer.write(newContent);
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        finally
//        {
//            try
//            {
//                //Closing the resources
//
//                reader.close();
//
//                writer.close();
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
//        }
//    }
//
//}
//
//
//
//
public class ReplaceValueForAKey {
    private String path = ""; // Set your file path appropriately

    public void replaceValue(String key, String newValue, String environment) {
        JSONParser parser = new JSONParser();
        try {
            // Parse the JSON file
            Object obj = parser.parse(new FileReader(path + environment));
            JSONObject jsonObject = (JSONObject) obj;

            // Find the correct object in the JSON array based on the key
            JSONArray subjects = (JSONArray) jsonObject.get("values");
            Iterator<JSONObject> iterator = subjects.iterator();
            while (iterator.hasNext()) {
                JSONObject obj2 = iterator.next();
                if (obj2.containsKey("key") && obj2.get("key").equals(key)) {
                    // Update the "value" field with newValue
                    obj2.put("value", newValue);
                    break;
                }
            }

            // Write the updated JSON back to the file
            FileWriter fileWriter = new FileWriter(path + environment);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();

            System.out.println("Successfully updated value for key: " + key);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}

