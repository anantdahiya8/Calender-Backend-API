package com.freightfox.calender.helper.common;

import com.freightfox.calender.constants.CalenderConstants;
import com.freightfox.calender.model.availability.AvailabilityReply;
import com.freightfox.calender.model.availability.ErrorMessage;
import com.freightfox.calender.model.booking.BookReply;
import com.freightfox.calender.model.booking.BookRequest;
import com.freightfox.calender.model.recommendations.RecommendationReply;

import java.util.ArrayList;
import java.util.List;

public class ErrorHelper {

  public static void buildInvalidNameError(AvailabilityReply reply){
    ErrorMessage error = new ErrorMessage();
    error.setCode(CalenderConstants.INVALID_NAME_CODE);
    error.setTitle(CalenderConstants.INVALID_NAME_ERROR);
    error.setDetail(CalenderConstants.INVALID_NAME_DESC);
    List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
    errorList.add(error);
    reply.setErrors(errorList);
  }

  public static void buildInvalidNameError(BookReply reply){
    ErrorMessage error = new ErrorMessage();
    error.setCode(CalenderConstants.INVALID_NAME_CODE);
    error.setTitle(CalenderConstants.INVALID_NAME_ERROR);
    error.setDetail(CalenderConstants.INVALID_NAME_DESC);
    List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
    errorList.add(error);
    reply.setErrors(errorList);
  }

  public static void buildInvalidNameError(RecommendationReply reply){
    ErrorMessage error = new ErrorMessage();
    error.setCode(CalenderConstants.INVALID_NAME_CODE);
    error.setTitle(CalenderConstants.INVALID_NAME_ERROR);
    error.setDetail(CalenderConstants.INVALID_NAME_DESC);
    List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
    errorList.add(error);
    reply.setErrors(errorList);
  }

  public static void buildInvalidDateError(AvailabilityReply reply){
    ErrorMessage error = new ErrorMessage();
    error.setCode(CalenderConstants.INVALID_DATE_CODE);
    error.setTitle(CalenderConstants.INVALID_DATE_ERROR);
    error.setDetail(CalenderConstants.INVALID_DATE_DESC);
    List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
    errorList.add(error);
    reply.setErrors(errorList);
  }

  public static void buildInvalidDateError(RecommendationReply reply){
    ErrorMessage error = new ErrorMessage();
    error.setCode(CalenderConstants.INVALID_DATE_CODE);
    error.setTitle(CalenderConstants.INVALID_DATE_ERROR);
    error.setDetail(CalenderConstants.INVALID_DATE_DESC);
    List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
    errorList.add(error);
    reply.setErrors(errorList);
  }

  public static void buildInvalidDateError(BookReply reply){
    ErrorMessage error = new ErrorMessage();
    error.setCode(CalenderConstants.INVALID_DATE_CODE);
    error.setTitle(CalenderConstants.INVALID_DATE_ERROR);
    error.setDetail(CalenderConstants.INVALID_DATE_DESC);
    List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
    errorList.add(error);
    reply.setErrors(errorList);
  }

  public static void buildDefaultError(AvailabilityReply reply){
    ErrorMessage error = new ErrorMessage();
    error.setCode(CalenderConstants.DEFAULT_CODE);
    error.setTitle(CalenderConstants.DEFAULT_ERROR);
    error.setDetail(CalenderConstants.DEFAULT_DESC);
    List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
    errorList.add(error);
    reply.setErrors(errorList);
  }

  public static void buildDefaultError(BookReply reply){
    ErrorMessage error = new ErrorMessage();
    error.setCode(CalenderConstants.DEFAULT_CODE);
    error.setTitle(CalenderConstants.DEFAULT_ERROR);
    error.setDetail(CalenderConstants.DEFAULT_DESC);
    List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
    errorList.add(error);
    reply.setErrors(errorList);
  }

  public static void buildDefaultError(RecommendationReply reply){
    ErrorMessage error = new ErrorMessage();
    error.setCode(CalenderConstants.DEFAULT_CODE);
    error.setTitle(CalenderConstants.DEFAULT_ERROR);
    error.setDetail(CalenderConstants.DEFAULT_DESC);
    List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
    errorList.add(error);
    reply.setErrors(errorList);
  }

  public static List<ErrorMessage> getNoSlotsError(){
    ErrorMessage error = new ErrorMessage();
    error.setCode(CalenderConstants.NO_SLOTS_CODE);
    error.setTitle(CalenderConstants.NO_SLOTS_ERROR);
    error.setDetail(CalenderConstants.NO_SLOTS_DESC);
    List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
    errorList.add(error);
    return errorList;
  }

  public static void addInvalidRequestError(BookReply reply){
    ErrorMessage error = new ErrorMessage();
    error.setCode(CalenderConstants.INVALID_INPUT_CODE);
    error.setTitle(CalenderConstants.INVALID_INPUT_ERROR);
    error.setDetail(CalenderConstants.INVALID_INPUT_DESC);
    List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
    errorList.add(error);
    reply.setErrors(errorList);
  }

  public static void addInvalidRecommendationsRequestError(RecommendationReply reply){
    ErrorMessage error = new ErrorMessage();
    error.setCode(CalenderConstants.INVALID_REC_INPUT_CODE);
    error.setTitle(CalenderConstants.INVALID_REC_INPUT_ERROR);
    error.setDetail(CalenderConstants.INVALID_REC_INPUT_DESC);
    List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
    errorList.add(error);
    reply.setErrors(errorList);
  }

  public static void addInvalidTimeError(BookReply reply){
    ErrorMessage error = new ErrorMessage();
    error.setCode(CalenderConstants.INVALID_TIME_CODE);
    error.setTitle(CalenderConstants.INVALID_TIME_ERROR);
    error.setDetail(CalenderConstants.INVALID_TIME_DESC);
    List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
    errorList.add(error);
    reply.setErrors(errorList);
  }

  public static void addNoSlotAvailableError(BookReply reply){
    ErrorMessage error = new ErrorMessage();
    error.setCode(CalenderConstants.BOOKING_FAILED_CODE);
    error.setTitle(CalenderConstants.BOOKING_FAILED_ERROR);
    error.setDetail(CalenderConstants.BOOKING_FAILED_DESC);
    List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
    errorList.add(error);
    reply.setErrors(errorList);
  }

  public static void buildExceptionError(String name, BookReply reply){
    ErrorMessage error = new ErrorMessage();
    error.setCode(CalenderConstants.SLOT_OVERLAP_CODE);
    error.setTitle(CalenderConstants.SLOT_OVERLAP_ERROR);
    error.setDetail(CalenderConstants.SLOT_OVERLAP_DESC + name);
    List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
    errorList.add(error);
    reply.setErrors(errorList);
  }
}
