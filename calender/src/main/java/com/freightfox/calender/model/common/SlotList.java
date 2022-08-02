package com.freightfox.calender.model.common;

import java.util.List;

public class SlotList {
    private List<Slot> slotList;

    public void setSlotList(List<Slot> slotList){
      this.slotList = slotList;
    }

    public List<Slot> getSlotList(){
      return this.slotList;
    }
}
