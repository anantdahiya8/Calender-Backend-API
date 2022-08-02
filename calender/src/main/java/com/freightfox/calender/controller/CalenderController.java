package com.freightfox.calender.controller;

import com.freightfox.calender.CalenderApi;
import com.freightfox.calender.impl.CalenderApiImpl;
import com.freightfox.calender.model.availability.AvailabilityReply;
import com.freightfox.calender.model.booking.BookReply;
import com.freightfox.calender.model.recommendations.RecommendationReply;
import com.freightfox.calender.model.recommendations.RecommendationRequest;
import org.springframework.web.bind.annotation.*;
import io.swagger.util.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/calender")
public class CalenderController {

  CalenderApi api = new CalenderApiImpl();

  @RequestMapping(value = "/availability/{employeeName}/{date}", method = RequestMethod.GET)
  public void getAvailability(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse,
      @PathVariable()
        String employeeName,
      @PathVariable()
        String date){
    AvailabilityReply reply = api.getAvailability(employeeName, date);
    String json = Json.pretty(reply);
    setBody(httpServletResponse, json);
  }

  @RequestMapping(value = "/book", method = RequestMethod.POST)
  public void getAvailability(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse,
      @RequestBody()
      Object request){
    BookReply reply = api.bookMeeting(request);
    String json = Json.pretty(reply);
    setBody(httpServletResponse, json);
  }

  @RequestMapping(value = "/recommendations/{date}", method = RequestMethod.GET)
  public void getRecommendation(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse,
      @PathVariable
          String date,
      @RequestParam(name = "employees")
          List<String> employees){
    RecommendationRequest request = new RecommendationRequest();
    request.setEmployees(employees);
    request.setDate(date);
    RecommendationReply reply = api.getRecommendations(request);
    String json = Json.pretty(reply);
    setBody(httpServletResponse, json);
  }

  private void setBody(HttpServletResponse httpServletResponse, String body) {
    PrintWriter writer;
    try {
      writer = httpServletResponse.getWriter();
    } catch (IOException e) {
      System.err.println("Could not write into the response stream");
      return;
    }
    writer.write(body);
    writer.flush();
  }

}
