package com.freightfox.calender.helper.common;

import com.freightfox.calender.exceptions.BookingException;
import com.freightfox.calender.model.booking.BookRequest;
import com.freightfox.calender.model.common.Slot;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CalenderHelperTest {

  @Test
  public void test_validateEmployeeName(){
    Assert.assertEquals(false, CalenderHelper.validateEmployeeName("abc"));
    Assert.assertEquals(true, CalenderHelper.validateEmployeeName("charlie"));
  }

  @Test
  public void test_validateTimePair(){
    Assert.assertEquals(false, CalenderHelper.validateTimePair("", ""));
    Assert.assertEquals(false, CalenderHelper.validateTimePair("0800", "1230"));
    Assert.assertEquals(false, CalenderHelper.validateTimePair("0800", "1730"));
    Assert.assertEquals(false, CalenderHelper.validateTimePair("0966", "1230"));
    Assert.assertEquals(false, CalenderHelper.validateTimePair("0899", "1700"));
    Assert.assertEquals(false, CalenderHelper.validateTimePair("1312", "1230"));
    Assert.assertEquals(true, CalenderHelper.validateTimePair("0900", "1600"));
  }

  @Test
  public void test_timeSlotAvailable() throws BookingException {
    List<List<Slot>> meetingSlots = setUpMeetingSlots();
    BookRequest request = setupBookrequest();
    Assert.assertEquals(true, CalenderHelper.timeSlotAvailable("0900", "0930", meetingSlots, request));
    Assert.assertEquals(true, CalenderHelper.timeSlotAvailable("1645", "1700", meetingSlots, request));
    try{
      CalenderHelper.timeSlotAvailable("0900", "1530", meetingSlots, request);
    }catch(BookingException e){
      Assert.assertEquals("alpha", e.getMessage());
    }
  }

  public List<List<Slot>> setUpMeetingSlots(){
    List<List<Slot>> meetingSlots = new ArrayList<>();
    List<Slot> slots = new ArrayList<>();
    Slot slot = new Slot();
    slot.setStart("1020");
    slot.setEnd("1030");
    slots.add(slot);
    slot = new Slot();
    slot.setStart("1300");
    slot.setEnd("1310");
    slots.add(slot);
    meetingSlots.add(slots);
    slots = new ArrayList<>();
    slot = new Slot();
    slot.setStart("1400");
    slot.setEnd("1430");
    slots.add(slot);
    slot = new Slot();
    slot.setStart("1630");
    slot.setEnd("1645");
    slots.add(slot);
    meetingSlots.add(slots);
    return meetingSlots;
  }

  public BookRequest setupBookrequest(){
    List<String> emp = new ArrayList<>();
    emp.add("alpha");
    emp.add("bravo");
    BookRequest request = new BookRequest();
    request.setEmployees(emp);
    return request;
  }
}
