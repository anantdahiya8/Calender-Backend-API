package com.freightfox.calender.service;

import com.freightfox.calender.helper.common.FirebaseServiceHelper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.*;

public class FirebaseService {

  private static FirebaseDatabase database = null;

  /**
  * Fetch meeting data of employee from DB with date
  * @param date
  * @param employeeName
  * @return JSONObject
  **/
  public static JSONObject getAvailabilityEmployeeDate(String employeeName, String date)
      throws IOException, ParseException {
    URL url = new URL("https://calender-f7353-default-rtdb.firebaseio.com/"+employeeName+"/"+date+".json");
    HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
    con.setRequestMethod("GET");
    con.setRequestProperty("Content-Type", "application/json");
    con.setConnectTimeout(5000);
    con.setReadTimeout(5000);
    System.out.println(con.getResponseCode());
    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();
    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    con.disconnect();
    String responses = response.toString();
    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject)parser.parse(responses);
    return json;
  }

  /**
  *
  * Process multi-path update in the DB to avoid data inconsistency
  * Add meeting data in the DB
  *
  * @param startTime
  * @param endTime
  * @param employees
  * @param date
  * @return JSONObject
  **/
  public static boolean bookEmployeeSlot(String startTime, String endTime, List<String> employees, String date)
      throws IOException {

    if(FirebaseApp.getApps().size() == 0){
      FirebaseApp app = null;
      FileInputStream serviceAccount =
          new FileInputStream("src/main/resources/firebase/calender-f7353-firebase-adminsdk-4r4kz-81fa185bb7.json");
      FirebaseOptions options = new FirebaseOptions.Builder()
          .setCredentials(GoogleCredentials.fromStream(serviceAccount))
          .setDatabaseUrl("https://calender-f7353-default-rtdb.firebaseio.com")
          .build();

      app = FirebaseApp.initializeApp(options);
      database = FirebaseDatabase.getInstance(app);
    }
    List<String> pathsUpdate = FirebaseServiceHelper.updatePaths(employees, date);
    List<String> updateKeys = new ArrayList<>();
    Iterator pItr = pathsUpdate.iterator();
    while(pItr.hasNext()){
      String path = (String)pItr.next();
      updateKeys.add(database.getReference(path).push().getKey());
    }
    if(updateKeys.size() != employees.size()){
      return false;
    }
    Map<String, Object> updateMap = FirebaseServiceHelper.buildUpdateJSONObject(startTime, endTime, employees, date, updateKeys);
    database.getReference().updateChildrenAsync(updateMap);
    return true;
  }
}
