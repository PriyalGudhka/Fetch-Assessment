package com.fetch.assessment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @NotNull(message = "shortDescription must not be blank")
    @Pattern(regexp = "^[\\w\\s\\-]+$", message = "The receipt is invalid.")
    private String shortDescription;

    @NotNull(message = "price must not be blank")
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message="The receipt is invalid.")
    private String price;
}
