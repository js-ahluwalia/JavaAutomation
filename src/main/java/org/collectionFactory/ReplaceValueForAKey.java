package org.collectionFactory;
import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

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

