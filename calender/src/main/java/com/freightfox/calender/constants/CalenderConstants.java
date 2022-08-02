package com.freightfox.calender.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface CalenderConstants {

  public static final String START_TIME = "0900";
  public static final String END_TIME = "1700";

  public static final String INVALID_NAME_CODE = "1001";
  public static final String INVALID_NAME_ERROR = "INVALID EMPLOYEE NAME";
  public static final String INVALID_NAME_DESC = "Employee name entered does not exist.";

  public static final String DEFAULT_CODE = "1000";
  public static final String DEFAULT_ERROR = "UNABLE TO PROCESS";
  public static final String DEFAULT_DESC = "Please try again later.";

  public static final String INVALID_DATE_CODE = "1002";
  public static final String INVALID_DATE_ERROR = "INVALID DATE";
  public static final String INVALID_DATE_DESC = "Entered date format is invalid or in the past.";

  public static final String NO_SLOTS_CODE = "1004";
  public static final String NO_SLOTS_ERROR = "NO SLOTS AVAILABLE";
  public static final String NO_SLOTS_DESC = "No slots available for the date.";

  public static final String INVALID_INPUT_CODE = "1005";
  public static final String INVALID_INPUT_ERROR = "INVALID REQUEST DATA";
  public static final String INVALID_INPUT_DESC = "Unable to parse request data.";

  public static final String INVALID_TIME_CODE = "1006";
  public static final String INVALID_TIME_ERROR = "INVALID TIME";
  public static final String INVALID_TIME_DESC = "Time combination is incorrect.";

  public static final String BOOKING_FAILED_CODE = "1007";
  public static final String BOOKING_FAILED_ERROR = "UNABLE TO BOOK SLOT";
  public static final String BOOKING_FAILED_DESC = "Slot not available or connection error.";

  public static final String INVALID_REC_INPUT_CODE = "1008";
  public static final String INVALID_REC_INPUT_ERROR = "INVALID REQUEST DATA";
  public static final String INVALID_REC_INPUT_DESC = "Unable to parse request data.";

  public static final String SLOT_OVERLAP_CODE = "1009";
  public static final String SLOT_OVERLAP_ERROR = "SLOT NOT AVAILABLE";
  public static final String SLOT_OVERLAP_DESC = "Slot overlap with ";

  public static final List<String> validEmployeeList = new ArrayList<String>(Arrays.asList("alpha", "bravo", "charlie"));
}
