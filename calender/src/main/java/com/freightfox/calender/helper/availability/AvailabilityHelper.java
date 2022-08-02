package com.freightfox.calender.helper.availability;

import com.freightfox.calender.constants.CalenderConstants;
import com.freightfox.calender.helper.common.CalenderHelper;
import com.freightfox.calender.helper.common.ErrorHelper;
import com.freightfox.calender.model.availability.AvailabilityReply;
import com.freightfox.calender.model.availability.ErrorMessage;
import com.freightfox.calender.model.availability.TimeSlot;
import com.freightfox.calender.model.common.Slot;
import com.freightfox.calender.model.common.SlotList;
import com.freightfox.calender.service.FirebaseService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

public class AvailabilityHelper {

  /**
  * Fetch slot list from DB and process the data
  *
  * @param employeeName api request
  * @param date
  * @return SlotList
  **/
  public static SlotList getSlotList(String employeeName, String date) throws IOException, ParseException {
    List<Slot> slotList = new ArrayList<Slot>();
    SlotList sl = new SlotList();
    JSONObject json = FirebaseService.getAvailabilityEmployeeDate(employeeName, date);
    if(json != null){
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
      sl.setSlotList(slotList);
    }
    return sl;
  }

  /**
  * Process available slots from meeting slots
  *
  * @param slotList
  * @return SlotList
  **/
  public static SlotList processAvailableSlots(SlotList slotList){
    if(slotList.getSlotList() == null){
      Slot slot= new Slot();
      List<Slot> sl = new ArrayList<Slot>();
      slot.setStart(CalenderConstants.START_TIME);
      slot.setEnd(CalenderConstants.END_TIME);
      sl.add(slot);
      slotList = new SlotList();
      slotList.setSlotList(sl);
      return slotList;
    }
    List<Slot> sl = slotList.getSlotList();
    CalenderHelper.sortSlotList(sl);
    List<Slot> availableSlots = getAvailableSlots(sl);
    slotList.setSlotList(availableSlots);
    return slotList;
  }

  /**
  * Insert data is api response
  *
  * @param list
  * @param reply
  **/
  public static void processResponse(SlotList list, AvailabilityReply reply){
    if(list == null || list.getSlotList().size() < 1){
      List<ErrorMessage> errors = ErrorHelper.getNoSlotsError();
      reply.setErrors(errors);
    }else{
      Iterator<Slot> itr = list.getSlotList().iterator();
      List<TimeSlot> listts = new ArrayList<TimeSlot>();
      while(itr.hasNext()){
        Slot slot = itr.next();
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setStartTime(slot.getStart());
        timeSlot.setEndTime(slot.getEnd());
        listts.add(timeSlot);
      }
      reply.setData(listts);
    }
  }

  /**
  * Process available slots from meeting slots
  *
  * @param slotList
  * @@return  List<Slot>
  **/
  public static List<Slot> getAvailableSlots(List<Slot> slotList){
    List<Slot> availSlot = new ArrayList<Slot>();
    Iterator<Slot> itr = slotList.iterator();
    for(int i=0;i<slotList.size()-1;i++){
      if(Integer.compare(Integer.parseInt(slotList.get(i).getEnd()), Integer.parseInt((slotList.get(i+1).getStart()))) == -1){
        Slot slot = new Slot();
        slot.setStart(slotList.get(i).getEnd());
        slot.setEnd(slotList.get(i+1).getStart());
        availSlot.add(slot);
      }
    }
    if(!slotList.get(0).getStart().equalsIgnoreCase(CalenderConstants.START_TIME)){
      Slot slot = new Slot();
      slot.setStart(CalenderConstants.START_TIME);
      slot.setEnd(slotList.get(0).getStart());
      availSlot.add(slot);
    }
    if(!slotList.get(slotList.size()-1).getEnd().equalsIgnoreCase(CalenderConstants.END_TIME)){
      Slot slot = new Slot();
      slot.setStart(slotList.get(slotList.size()-1).getEnd());
      slot.setEnd(CalenderConstants.END_TIME);
      availSlot.add(slot);
    }
    CalenderHelper.sortSlotList(availSlot);
    return availSlot;
  }
}
