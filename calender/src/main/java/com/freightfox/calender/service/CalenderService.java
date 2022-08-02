package com.freightfox.calender.service;


import com.freightfox.calender.exceptions.BookingException;
import com.freightfox.calender.helper.availability.AvailabilityHelper;
import com.freightfox.calender.helper.booking.BookingHelper;
import com.freightfox.calender.helper.common.CalenderHelper;
import com.freightfox.calender.helper.common.ErrorHelper;
import com.freightfox.calender.helper.recommendations.RecommendationsHelper;
import com.freightfox.calender.model.availability.AvailabilityReply;
import com.freightfox.calender.model.booking.BookReply;
import com.freightfox.calender.model.booking.BookRequest;
import com.freightfox.calender.model.common.SlotList;
import com.freightfox.calender.model.recommendations.RecommendationReply;
import com.freightfox.calender.model.recommendations.RecommendationRequest;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.IOException;

public class CalenderService {

  /**
  * Process availability api response
  * @param date
  * @param employeeName
  * @return AvailabilityReply
  **/
  public AvailabilityReply getAvailability(String employeeName, String date)
      throws IOException, ParseException, java.text.ParseException {
    AvailabilityReply reply = new AvailabilityReply();
    boolean isInputValid = false;
    isInputValid = CalenderHelper.validateEmployeeName(employeeName);
    if(!isInputValid){
      ErrorHelper.buildInvalidNameError(reply);
      return reply;
    }
    isInputValid = CalenderHelper.validateDate(date);
    if(!isInputValid){
      ErrorHelper.buildInvalidDateError(reply);
      return reply;
    }
    date = CalenderHelper.updateDate(date);
    SlotList meetingSlotList = AvailabilityHelper.getSlotList(employeeName, date);
    SlotList availableSlots = AvailabilityHelper.processAvailableSlots(meetingSlotList);
    AvailabilityHelper.processResponse(availableSlots, reply);
    return reply;
  }

  /**
  * Book meeting slots for the employees
  * @param request api request
  * @return BookReply
  **/
  public BookReply bookMeeting(Object request)
      throws IOException, ParseException, BookingException, java.text.ParseException {
    BookReply reply = new BookReply();
    BookRequest req = BookingHelper.processBookingRequest(request, reply);
    if(reply.getErrors() != null && reply.getErrors().size() > 0){
      return reply;
    }
    if(!BookingHelper.validateInput(req, reply)){
      return reply;
    }
    req.setDate(CalenderHelper.updateDate(req.getDate()));
    if(BookingHelper.isTimeSlotAvailable(req)){
      boolean slotBooked = false;
      try{
        slotBooked = BookingHelper.bookSlot(req);
      }catch (Exception e){
        System.err.println("Error while updating the database");
        ErrorHelper.addNoSlotAvailableError(reply);
      }
      if(slotBooked){
        JSONObject obj = new JSONObject();
        obj.put("bookingSuccess", true);
        reply.setData(obj);
      }else{
        ErrorHelper.addNoSlotAvailableError(reply);
      }
    }else{
      ErrorHelper.addNoSlotAvailableError(reply);
    }
    return reply;
  }

  /**
  * Book meeting slots for the employees
  * @param request api request
  * @return RecommendationReply api response
  **/
  public RecommendationReply getRecommendations(RecommendationRequest request)
      throws IOException, ParseException, java.text.ParseException {
    RecommendationReply reply = new RecommendationReply();
    if(!RecommendationsHelper.validationRecommendationsRequest(request, reply)){
      return reply;
    }
    request.setDate(CalenderHelper.updateDate(request.getDate()));
    RecommendationsHelper.getSlotRecommendations(request, reply);
    return reply;
  }

}
