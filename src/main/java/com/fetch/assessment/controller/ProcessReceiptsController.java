package com.fetch.assessment.controller;

import com.fetch.assessment.dto.PointsResponseJson;
import com.fetch.assessment.dto.ProcessReceiptsRequestJson;
import com.fetch.assessment.dto.ProcessReceiptsResponseJson;
import com.fetch.assessment.interfaces.ProcessReceiptsInterface;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class ProcessReceiptsController {

    @Autowired
    ProcessReceiptsInterface processReceiptsService;

    /**
     * Endpoint for processing receipts.
     * This method will accept a POST request with the receipt data, process it, and return a response with the processed information.
     *
     * @param processReceiptsRequestJson The request body containing receipt details.
     * @return ResponseEntity containing the response JSON with processed receipt information.
     */
    @PostMapping("/receipts/process")
    public ResponseEntity<ProcessReceiptsResponseJson> processReceipts(@Valid @RequestBody ProcessReceiptsRequestJson processReceiptsRequestJson){

        // Call the service to store the receipt and get the response
        ProcessReceiptsResponseJson processReceiptsResponseJson = processReceiptsService.storeReceipts(processReceiptsRequestJson);

        return ResponseEntity.ok(processReceiptsResponseJson);
    }

    /**
     * Endpoint for retrieving points based on a specific receipt ID.
     * This method will accept a GET request with the receipt ID, fetch the associated points, and return them in the response.
     *
     * @param id The ID of the receipt for which points need to be calculated.
     * @return ResponseEntity containing the points information or an error message.
     */
    @GetMapping("/receipts/{id}/points")
    public ResponseEntity<Object> getPoints(@NotNull @PathVariable @Pattern(regexp = "^\\S+$", message = "No receipt found for that ID.") String id){

        PointsResponseJson pointsResponseJson;

        try {
            UUID uuid = UUID.fromString(id);

            // Call the service to calculate the points for the given receipt ID
            pointsResponseJson = processReceiptsService.calculatePoints(uuid);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }

        return ResponseEntity.ok(pointsResponseJson);
    }
}
