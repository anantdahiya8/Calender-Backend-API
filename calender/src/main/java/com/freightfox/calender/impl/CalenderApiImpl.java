package com.freightfox.calender.impl;

import com.freightfox.calender.CalenderApi;
import com.freightfox.calender.exceptions.BookingException;
import com.freightfox.calender.helper.common.ErrorHelper;
import com.freightfox.calender.model.availability.AvailabilityReply;
import com.freightfox.calender.model.booking.BookReply;
import com.freightfox.calender.model.recommendations.RecommendationReply;
import com.freightfox.calender.model.recommendations.RecommendationRequest;
import com.freightfox.calender.service.CalenderService;

public class CalenderApiImpl implements CalenderApi {

  CalenderService calenderService = new CalenderService();

  @Override
  public AvailabilityReply getAvailability(String employeeName, String date){
    AvailabilityReply reply = new AvailabilityReply();
    try{
      reply = calenderService.getAvailability(employeeName, date);
    }catch(Exception e){
      System.err.println("Error while processing api response");
      ErrorHelper.buildDefaultError(reply);
    }
    return reply;
  }

  @Override
  public BookReply bookMeeting(Object request){
    BookReply reply = new BookReply();
    try{
      reply = calenderService.bookMeeting(request);
    }catch(BookingException e){
      System.err.println("Slot not available");
      ErrorHelper.buildExceptionError(e.getMessage(), reply);
    }catch(Exception e){
      System.err.println("Error while processing api response");
      ErrorHelper.buildDefaultError(reply);
    }
    return reply;
  }

  @Override
  public RecommendationReply getRecommendations(RecommendationRequest request){
    RecommendationReply reply = new RecommendationReply();
    try{
      reply = calenderService.getRecommendations(request);
    }catch(Exception e){
      System.err.println("Error while processing api response");
      ErrorHelper.buildDefaultError(reply);
    }
    return reply;
  }
}
