package org.collectionFactory;

import java.io.*;
import java.text.MessageFormat;

public class HitAPI {
    @SuppressWarnings("deprecation")
    public void runApi(String fileName, String environment) throws InterruptedException {
        String file=fileName+".postman_collection.json";
        String apiFilepath="collectionFiles/"; //deleteuserwala
        String envpath="environmentFiles/";
        String env=environment;
        System.out.print(System.getProperty("user.dir"));
        final Process output;

        try {
            String command = MessageFormat.format("newman run {0} -e {1} --export-environment {2}", apiFilepath+file,envpath+env,envpath+env);
            output = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new Thread(new Runnable() {
            public void run() {
                BufferedReader input = new BufferedReader(new InputStreamReader(output.getInputStream()));
                String line = null;
                try {
                    while ((line = input.readLine()) != null)
                        System.out.println(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        output.waitFor();


    }


}
