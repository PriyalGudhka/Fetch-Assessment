package com.fetch.assessment.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessReceiptsRequestJson {

    @NotNull(message = "retailer must not be blank")
    @Pattern(regexp = "^[\\w\\s\\-&]+$", message = "The receipt is invalid.")
    private String retailer;

    @NotNull(message = "purchaseDate must not be blank")
    @Pattern(regexp = "^(?:19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = "The receipt is invalid.")
    private String purchaseDate;

    @NotNull(message = "purchaseTime must not be blank")
    @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "The receipt is invalid.")
    private String purchaseTime;

    @NotEmpty(message = "The receipt is invalid.")
    private List<@Valid Item> items;

    @NotNull(message = "total must not be blank")
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message="The receipt is invalid.")
    private String total;

}
