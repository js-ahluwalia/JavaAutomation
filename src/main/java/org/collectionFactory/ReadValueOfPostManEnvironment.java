package org.collectionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.util.Iterator;

public class ReadValueOfPostManEnvironment {
    String path="environmentFiles/";
    public String readValueOfPostManEnvironmentKey(String key,String environment) {
        JSONParser parser = new JSONParser();
        String valueToBeRead = null;
        try {
            Object obj = parser.parse(new FileReader(path+environment));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray subjects = (JSONArray) jsonObject.get("values");
            Iterator iterator = subjects.iterator();
            while (iterator.hasNext()) {
                String h1 = iterator.next().toString();
                if (h1.contains(key)) {
                    Object obj2 = parser.parse(h1);
                    JSONObject jsonObject2 = (JSONObject) obj2;
                    valueToBeRead = jsonObject2.get("value").toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return valueToBeRead;
    }


}
