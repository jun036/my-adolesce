package com.adolesce.server.javabasic.meetquestion.zerenlian;

public interface Handler {
   void setNext(Handler handler);
   void handle(Integer number,StringBuilder result);
}
