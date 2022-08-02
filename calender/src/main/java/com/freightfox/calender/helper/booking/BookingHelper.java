package com.freightfox.calender.helper.booking;

import com.freightfox.calender.exceptions.BookingException;
import com.freightfox.calender.helper.common.CalenderHelper;
import com.freightfox.calender.helper.common.ErrorHelper;
import com.freightfox.calender.model.booking.BookReply;
import com.freightfox.calender.model.booking.BookRequest;
import com.freightfox.calender.model.common.Slot;
import com.freightfox.calender.service.FirebaseService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class BookingHelper {

  /**
  * Process the booking request data
  *
  * @param request
  * @param reply
  * @return BookRequest
  **/
  public static BookRequest processBookingRequest(Object request, BookReply reply){
    BookRequest req = new BookRequest();
    try{
      req.setEmployees((List)((LinkedHashMap)request).get("employees"));
      req.setStartTime((String)((LinkedHashMap)request).get("startTime"));
      req.setEndTime((String)((LinkedHashMap)request).get("endTime"));
      req.setDate((String)((LinkedHashMap)request).get("date"));
    }catch(Exception e){
      System.err.println("Enable to process booking input");
      ErrorHelper.addInvalidRequestError(reply);
    }
    return req;
  }

  /**
  * Validate booking request data
  *
  * @param request
  * @param reply
  * @return boolean
  **/
  public static boolean validateInput(BookRequest request, BookReply reply){
    if(request.getEndTime() == null || request.getStartTime() == null || request.getEmployees() == null ||
      request.getEmployees().size() <= 0 || request.getDate() == null){
      ErrorHelper.addInvalidRequestError(reply);
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
    if(!CalenderHelper.validateTimePair(request.getStartTime(), request.getEndTime())){
      ErrorHelper.addInvalidTimeError(reply);
      return false;
    }
    return true;
  }

  /**
  * Check if time slot is vailable for booking
  *
  * @param request
  * @return boolean
  **/
  public static boolean isTimeSlotAvailable(BookRequest request) throws BookingException {
    List<String> emps = request.getEmployees();
    List<List<Slot>> meetingSlots = new ArrayList<>();
    try{
      meetingSlots = CalenderHelper.getMeetingSlots(emps, request.getDate());
    }catch (Exception e){
      System.err.println("Enable to fetch or process employee data");
      return false;
    }
    return CalenderHelper.timeSlotAvailable(request.getStartTime(), request.getEndTime(), meetingSlots, request);
  }

  public static boolean bookSlot(BookRequest request) throws IOException {
    return FirebaseService.bookEmployeeSlot(request.getStartTime(), request.getEndTime(), request.getEmployees(), request.getDate());
  }
}
