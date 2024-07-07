package org.collectionFactory;

import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class collectionModifier {
    private String path = "/Users/sde-intern/Desktop/Automation 2/collectionFiles/"; // Set your file path appropriately

    public void replaceValue(String key, String newValue, String environment) {
        JSONParser parser = new JSONParser();
        try {
            // Parse the JSON file
            Object obj = parser.parse(new FileReader(path + environment));
            JSONObject jsonObject = (JSONObject) obj;

            // Navigate to the specific item containing the key
            JSONArray items = (JSONArray) jsonObject.get("item");
            for (Object itemObj : items) {
                JSONObject item = (JSONObject) itemObj;
                JSONObject request = (JSONObject) item.get("request");
                if (request != null) {
                    JSONObject body = (JSONObject) request.get("body");
                    if (body != null && "formdata".equals(body.get("mode"))) {
                        JSONArray formdata = (JSONArray) body.get("formdata");
                        for (Object formdataObj : formdata) {
                            JSONObject formdataItem = (JSONObject) formdataObj;
                            if (key.equals(formdataItem.get("key"))) {
                                formdataItem.put("src", newValue);
                                break;
                            }
                        }
                    }
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

    public static void main(String[] args) {
        collectionModifier replacer = new collectionModifier();
        replacer.replaceValue("test[images_attributes][][pic]", "/Users/sde-intern/Desktop/Automation 2/uploads/original_image_AEF.png", "createTest.postman_collection.json");
    }
}

