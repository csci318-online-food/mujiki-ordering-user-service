package com.csci318.microservice.user.Constants;

public enum OrderStatus {
     // Keep in chronological order.
     CONFIRMED,
     IN_PROGRESS,
     DELIVERING,
     COMPLETED,
 
     // Except for the "CANCELLED" status, always last.
     CANCELLED,
 }
