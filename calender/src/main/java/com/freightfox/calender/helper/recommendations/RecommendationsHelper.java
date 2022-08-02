package com.freightfox.calender.helper.recommendations;

import com.freightfox.calender.constants.CalenderConstants;
import com.freightfox.calender.helper.availability.AvailabilityHelper;
import com.freightfox.calender.helper.common.CalenderHelper;
import com.freightfox.calender.helper.common.ErrorHelper;
import com.freightfox.calender.model.availability.ErrorMessage;
import com.freightfox.calender.model.availability.TimeSlot;
import com.freightfox.calender.model.common.Slot;
import com.freightfox.calender.model.common.SlotList;
import com.freightfox.calender.model.recommendations.RecommendationReply;
import com.freightfox.calender.model.recommendations.RecommendationRequest;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecommendationsHelper {

  /**
  * Validate recommendation request other wise add corresponding error
  *
  * @param request
  * @param reply
  * @return RecommendationRequest
  **/
  public static boolean validationRecommendationsRequest(RecommendationRequest request, RecommendationReply reply){
    if(request.getDate() == null || request.getEmployees() == null || request.getEmployees().size() == 0){
      ErrorHelper.addInvalidRecommendationsRequestError(reply);
      return false;
    }
    if(!CalenderHelper.validateDate(request.getDate())){
      ErrorHelper.buildInvalidDateError(reply);
      return false;
    }
    if(!CalenderHelper.validateEmployeeList(request.getEmployees())){
      ErrorHelper.buildInvalidNameError(reply);
      return false;
    }
    return true;
  }

  /**
  * Fetch data from DB
  *
  * @param request
  * @param reply
  **/
  public static void getSlotRecommendations(RecommendationRequest request, RecommendationReply reply)
      throws IOException, ParseException {
    List<List<Slot>> allSlots = CalenderHelper.getMeetingSlots(request.getEmployees(), request.getDate());
    List<Slot> slots = mergeAllSlots(allSlots);
    CalenderHelper.sortSlotList(slots);
    if(slots != null && slots.size() == 0){
      List<TimeSlot> listSlot = new ArrayList<>();
      TimeSlot timeSlot = new TimeSlot();
      timeSlot.setStartTime(CalenderConstants.START_TIME);
      timeSlot.setEndTime(CalenderConstants.END_TIME);
      listSlot.add(timeSlot);
      reply.setData(listSlot);
    }else{
      processOverlappingSlots(slots);
      slots = AvailabilityHelper.getAvailableSlots(slots);
      SlotList slotList = new SlotList();
      slotList.setSlotList(slots);
      processResponse(slotList, reply);
    }
  }

  /**
  * Process response for recommendations
  *
  * @param list
  * @param reply
  **/
  public static void processResponse(SlotList list, RecommendationReply reply){
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
  * Find overlapping slots of employees and combine them into one
  *
  * @param slots
  **/
  public static void processOverlappingSlots(List<Slot> slots){
    for(int i=0;i<slots.size()-1;i++){
      if(doSlotsOverlap(slots.get(i), slots.get(i+1))){
        Integer slot1End = Integer.parseInt(slots.get(i).getEnd());
        Integer slot2End = Integer.parseInt(slots.get(i+1).getEnd());
        if(slot1End < slot2End){
          slots.get(i).setEnd(slots.get(i+1).getEnd());
          slots.remove(i+1);
          i-=1;
        }else{
          slots.remove(i+1);
          i-=1;
        }
      }
    }
  }

  /**
  * Check if 2 slots overlap or not
  *
  * @param slot1
  * @param slot2
  * @return boolean
  **/
  public static boolean doSlotsOverlap(Slot slot1, Slot slot2){
    if(Integer.parseInt(slot2.getStart()) >= Integer.parseInt(slot1.getStart()) &&
        Integer.parseInt(slot2.getStart()) <= Integer.parseInt(slot1.getEnd())){
      return true;
    }
    return false;
  }

  /**
  * Merge all employee slots into a single slot
  *
  * @param allSlots
  * @return List<Slot>
  **/
  public static List<Slot> mergeAllSlots(List<List<Slot>> allSlots){
    List<Slot> slots = new ArrayList<>();
    Iterator allItr = allSlots.iterator();
    while(allItr.hasNext()){
      List<Slot> empSlots = (List<Slot>)allItr.next();
      Iterator empSlotsItr = empSlots.iterator();
      while(empSlotsItr.hasNext()){
        Slot slot = (Slot)empSlotsItr.next();
        slots.add(slot);
      }
    }
    return slots;
  }
}
