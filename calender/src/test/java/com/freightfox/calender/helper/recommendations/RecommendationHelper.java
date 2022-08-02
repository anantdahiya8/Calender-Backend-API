package com.freightfox.calender.helper.recommendations;

import com.freightfox.calender.model.common.Slot;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class RecommendationHelper {

  @Test
  public void test_processOverlappingSlots(){
    List<Slot> slot = setupListSlot();
    Assert.assertEquals(6, slot.size());
    RecommendationsHelper.processOverlappingSlots(slot);
    Assert.assertEquals(4, slot.size());
    Assert.assertEquals("0900", slot.get(0).getStart());
    Assert.assertEquals("0925", slot.get(0).getEnd());
    Assert.assertEquals("1030", slot.get(1).getStart());
    Assert.assertEquals("1420", slot.get(2).getStart());
    Assert.assertEquals("1700", slot.get(3).getEnd());
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
    slot.setStart("1045");
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
    slot = new Slot();
    slot.setStart("1635");
    slot.setEnd("1659");
    listSlot.add(slot);
    return listSlot;
  }

}
