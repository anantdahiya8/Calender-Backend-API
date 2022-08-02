package com.freightfox.calender.helper.availability;

import com.freightfox.calender.model.common.Slot;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class AvailabilityHelperTest {

  @Test
  public void test_getAvailableSlots(){
    List<Slot> listSlot = setupListSlot();
    List<Slot> result = AvailabilityHelper.getAvailableSlots(listSlot);

    Assert.assertEquals(3, result.size());
    Assert.assertEquals("0925", result.get(0).getStart());
    Assert.assertEquals("1420", result.get(1).getEnd());
    Assert.assertEquals("1630", result.get(2).getEnd());
  }

  public List<Slot> setupListSlot(){
    List<Slot> listSlot = new ArrayList<>();
    Slot slot = new Slot();
    slot.setStart("0900");
    slot.setEnd("0925");
    listSlot.add(slot);
    slot = new Slot();
    slot.setStart("1030");
    slot.setEnd("1100");
    listSlot.add(slot);
    slot = new Slot();
    slot.setStart("1420");
    slot.setEnd("1430");
    listSlot.add(slot);
    slot = new Slot();
    slot.setStart("1630");
    slot.setEnd("1700");
    listSlot.add(slot);
    return listSlot;
  }
}
