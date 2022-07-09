package com.devsu.demo.expose.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

  @NotBlank
  private String message;

  @NotBlank
  private String to;

  @NotBlank
  private String from;
  
  private Long timeToLifeSec;

}
