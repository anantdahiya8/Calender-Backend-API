package com.freightfox.calender;

import com.freightfox.calender.model.availability.AvailabilityReply;
import com.freightfox.calender.model.booking.BookReply;
import com.freightfox.calender.model.recommendations.RecommendationReply;
import com.freightfox.calender.model.recommendations.RecommendationRequest;

public interface CalenderApi {
  /**
  * Get availability of a employee at a given day
  **/
  public AvailabilityReply getAvailability(String employeeName, String date);

  /**
  * Book meeting slots for employees for a given day and time
  **/
  public BookReply bookMeeting(Object request);

  /**
  * Get Slot Recommendations for employees
  **/
  public RecommendationReply getRecommendations(RecommendationRequest request);

}
