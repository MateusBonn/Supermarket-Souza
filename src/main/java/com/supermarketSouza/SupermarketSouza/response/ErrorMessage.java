package com.supermarketSouza.SupermarketSouza.response;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

  private Date timestamp;
  private Throwable cause;
  private String message;

}
