package com.freightfox.calender.helper.common;

import com.google.gson.JsonObject;
import org.json.simple.JSONObject;

import java.util.*;

public class FirebaseServiceHelper {

  public static Map<String, Object> buildUpdateJSONObject(String startTime, String endTime, List<String> employees, String date, List<String> updateKeys){
    Map<String, Object> updateMap = new HashMap<>();
    Iterator itr = employees.iterator();
    int i=0;
    while(itr.hasNext()){
      String name = (String)itr.next();
      String path = name+"/"+date+"/"+updateKeys.get(i++);
      JSONObject data = new JSONObject();
      data.put("start", startTime);
      data.put("end", endTime);
      updateMap.put(path, data);
    }
    return updateMap;
  }

  public static List<String> updatePaths(List<String> employees, String date){
    List<String> paths = new ArrayList<>();
    Iterator itr = employees.iterator();
    while(itr.hasNext()){
      String name = (String)itr.next();
      String path = name+"/"+date;
      paths.add(path);
    }
    return paths;
  }
}
