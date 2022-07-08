package com.devsu.demo.expose.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import io.swagger.annotations.ApiModelProperty;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

  @ApiModelProperty(value = "numero de solicitud con la compra de la poliza", example = "12345678912345")
  
  @NotBlank
  private String message;

  @NotBlank
  private String to;

  @NotBlank
  private String from;
  
  private Long timeToLifeSec;

}
