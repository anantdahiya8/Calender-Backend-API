package com.freightfox.calender.helper.common;

import com.freightfox.calender.constants.CalenderConstants;
import com.freightfox.calender.exceptions.BookingException;
import com.freightfox.calender.model.booking.BookRequest;
import com.freightfox.calender.model.common.Slot;
import com.freightfox.calender.service.FirebaseService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CalenderHelper {

  /**
  * Validate if employee name is configured
  *
  * @param name api request
  * @return boolean
  **/
  public static boolean validateEmployeeName(String name){
    if(CalenderConstants.validEmployeeList.contains(name)){
      return true;
    }
    return false;
  }

  /**
  * Validate id date provided is in correct format and parseable
  *
  * @param date
  * @return boolean
  **/
  public static boolean validateDate(String date){
    try{
      DateTimeFormatter format = DateTimeFormat.forPattern("dd-MM-yyyy");
      DateTime inputDate = format.parseDateTime(date);
      DateTime dateTime = new DateTime();
      if(inputDate.isAfter(dateTime)){
        return true;
      }
      return false;
    }catch (Exception e){
      System.err.println("Unable to parse date");
      return false;
    }
  }

  /**
  * Update the date to a common format
  *
  * @param date
  * @return String
  **/
  public static String updateDate(String date) throws java.text.ParseException {
    try{
      SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
      Date inputDate = format.parse(date);
      return format.format(inputDate);
    }catch (Exception e){
      System.err.println("Unable to update date");
    }
    return null;
  }

  /**
   * Validate if time entered is correct
   *
   * @param startTime
   * @param endTime
   * @return boolean
   **/
  public static boolean validateTimePair(String startTime , String endTime){
    try{
      if(startTime.length() != 4 || endTime.length() != 4){
        return false;
      }
      Integer start = Integer.parseInt(startTime);
      Integer end = Integer.parseInt(endTime);
      if(start >= end){
        return false;
      }
      if(Integer.parseInt(CalenderConstants.START_TIME) > start  || Integer.parseInt(CalenderConstants.END_TIME) < start){
        return false;
      }
      if(Integer.parseInt(CalenderConstants.START_TIME) > end  || Integer.parseInt(CalenderConstants.END_TIME) < end){
        return false;
      }
      if(!validateTime(start)){
        return false;
      }
      if(!validateTime(end)){
        return false;
      }
    }catch (Exception e){
      System.err.println("Invalid time");
      return false;
    }
    return true;
  }

  public static boolean validateTime(Integer time){
    Integer mins = time % 100;
    if(mins >= 60){
      return false;
    }
    return true;
  }

  public static boolean validateEmployeeList(List<String> empList){
    if(empList.size()>0){
      Iterator itr = empList.iterator();
      while (itr.hasNext()){
        String name = (String)itr.next();
        if(!CalenderConstants.validEmployeeList.contains(name)){
          return false;
        }
      }
      return true;
    }
    return false;
  }

  /**
  * Check if time slot can be booked
  * Check if it overlaps any other meetings
  *
  * @param startTime
  * @param endTime
  * @param meetingSlots
  * @return boolean
  **/
  public static boolean timeSlotAvailable(String startTime, String endTime, List<List<Slot>> meetingSlots, BookRequest request)
      throws BookingException {
    Integer start = Integer.parseInt(startTime);
    Integer end = Integer.parseInt(endTime);
    Iterator empItr = meetingSlots.iterator();
    int emp = 0;
    while(empItr.hasNext()){
      List<Slot> slots = (List<Slot>)empItr.next();
      Iterator slotItr = slots.iterator();
      while(slotItr.hasNext()){
        Slot slot = (Slot)slotItr.next();
        Integer slotStart = Integer.parseInt(slot.getStart());
        Integer slotEnd = Integer.parseInt(slot.getEnd());
        if((start.compareTo(slotStart) == 1 && start.compareTo(slotEnd) == -1) || (end.compareTo(slotStart) == 1 && end.compareTo(slotEnd) == -1)){
          throw new BookingException(request.getEmployees().get(emp));
        }
        if(start.compareTo(slotStart) == 0 && end.compareTo(slotEnd) == 0){
          throw new BookingException(request.getEmployees().get(emp));
        }
        if(start.compareTo(slotStart) == -1 && end.compareTo(slotEnd) == 1){
          throw new BookingException(request.getEmployees().get(emp));
        }
        if(start.compareTo(slotStart) == 1 && end.compareTo(slotEnd) == -1){
          throw new BookingException(request.getEmployees().get(emp));
        }
      }
      emp+=1;
    }
    return true;
  }

  /**
  * Get meeting slot for multiple employees and process it
  *
  * @param employees
  * @param date
  * @return List<List<Slot>>
  **/
  public static List<List<Slot>> getMeetingSlots(List<String> employees, String date) throws IOException,
      ParseException {
    Iterator itr = employees.iterator();
    List<List<Slot>> meetingSlots = new ArrayList<>();
    while (itr.hasNext()) {
      String name = (String)itr.next();
      JSONObject json = FirebaseService.getAvailabilityEmployeeDate(name, date);
      List<Slot> slotList = new ArrayList<Slot>();
      if (json != null) {
        Iterator<String> keys = json.keySet().iterator();
        while (keys.hasNext()) {
          String key = (String)keys.next();
          Slot slot = new Slot();
          if (json.get(key) instanceof JSONObject) {
            JSONObject ob = (JSONObject)json.get(key);
            slot.setStart(ob.get("start").toString());
            slot.setEnd(ob.get("end").toString());
          }
          slotList.add(slot);
        }
        meetingSlots.add(slotList);
      }else{
        meetingSlots.add(new ArrayList<Slot>());
      }

    }
    return meetingSlots;
  }

  /**
  * sort the list of slots according to start time
  *
  * @param slotList
  **/
  public static void sortSlotList(List<Slot> slotList){
    Collections.sort(slotList, new Comparator<Slot>() {
      @Override
      public int compare(Slot o1, Slot o2) {
        return Integer.compare(Integer.parseInt(o1.getStart()), Integer.parseInt(o2.getStart()));
      }
    });
  }

}
