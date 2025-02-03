package com.fetch.assessment.service;

import com.fetch.assessment.dao.ItemsRepo;
import com.fetch.assessment.dao.ReceiptsRepo;
import com.fetch.assessment.dto.PointsResponseJson;
import com.fetch.assessment.dto.ProcessReceiptsRequestJson;
import com.fetch.assessment.dto.ProcessReceiptsResponseJson;
import com.fetch.assessment.entity.Items;
import com.fetch.assessment.entity.Receipts;
import com.fetch.assessment.interfaces.ProcessReceiptsInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProcessReceiptsService implements ProcessReceiptsInterface {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    ReceiptsRepo receiptsRepo;

    @Autowired
    ItemsRepo itemsRepo;

    // Constants for various points calculation rules
    private final int alphaNumericPoints = 1;
    private final int calculateTotalWithNoCents = 50;
    private final int multipleOfTotal = 25;
    private final double multiple = 0.25;
    private final int everyTwoItems = 5;
    private final int priceEarnedForOddDay = 6;
    private final LocalTime purchaseStartTime = LocalTime.parse("14:00");
    private final LocalTime purchaseStopTime = LocalTime.parse("16:00");
    private final int priceEarnedForTime = 10;

    /**
     * Stores the receipt and its associated items in the database.
     *
     * @param processReceiptsRequestJson The request JSON containing the receipt details.
     * @return Response JSON containing the saved receipt's ID.
     */
    @Override
    public ProcessReceiptsResponseJson storeReceipts(ProcessReceiptsRequestJson processReceiptsRequestJson) {

        // Convert the incoming request JSON to a Receipts entity
        Receipts receipts = modelMapper.map(processReceiptsRequestJson, Receipts.class);

        // Save the receipt to the database
        Receipts receipt = receiptsRepo.save(receipts);

        for (Items item : receipt.getItems()) {
            item.setReceipt(receipt);
        }
        itemsRepo.saveAll(receipt.getItems());

        return new ProcessReceiptsResponseJson(receipts.getId());
    }

    /**
     * Calculates the points for a specific receipt based on various criteria.
     *
     * @param id The ID of the receipt.
     * @return PointsResponseJson containing the total points earned for the receipt.
     * @throws Exception If the receipt ID is not found.
     */
    public PointsResponseJson calculatePoints(UUID id) throws Exception {

        int pointsRewarded = 0;

        Optional<Receipts> receipt = receiptsRepo.findById(id);
        if(receipt.isPresent()) {

            List<Items> items = receipt.get().getItems();
            int size = items.size();

            Receipts receipts = receipt.get();

            // Calculate points based on different criteria and add to the total points
            pointsRewarded += calculatePointsForAlphaNumeric(receipts.getRetailer()) +
                    calculatePointsForNoCents(receipts.getTotal()) + everyTwoTimes(size) +
                    multipleOfItems(items) + purchaseDateIsOdd(receipts.getPurchaseDate()) +
                    multipleOfTotal(receipts.getTotal()) + purchaseTime(receipts.getPurchaseTime());
        } else {
            throw new Exception("Receipt Id not found");
        }

        return new PointsResponseJson(pointsRewarded);
    }

    /**
     * Calculates points based on the number of alphanumeric characters in the retailer's name.
     *
     * @param retailer The retailer name.
     * @return The calculated points.
     */
    private int calculatePointsForAlphaNumeric(String retailer) {
        retailer = retailer.trim();
        int count = 0;

        for (int i = 0; i < retailer.length(); i++) {
            char ch = retailer.charAt(i);
            if (Character.isLetter(ch) || Character.isDigit(ch)) {
                count++;
            }
        }

        return count * alphaNumericPoints;
    }

    /**
     * Calculates points if the total is a whole number (no cents).
     *
     * @param total The total amount of the receipt.
     * @return The calculated points.
     */
    private int calculatePointsForNoCents(String total) {
        if (Double.parseDouble(total) % 1 == 0)
            return calculateTotalWithNoCents;
        return 0;
    }

    /**
     * Calculates points if the total is a multiple of 0.25.
     *
     * @param total The total amount of the receipt.
     * @return The calculated points.
     */
    private int multipleOfTotal(String total) {
        if (Double.parseDouble(total) % multiple == 0)
            return multipleOfTotal;
        return 0;
    }

    /**
     * Calculates points for every two items purchased.
     *
     * @param size The number of items in the receipt.
     * @return The calculated points.
     */
    private int everyTwoTimes(int size) {
        return (size / 2) * everyTwoItems;
    }

    /**
     * Calculates points based on the price of items with a short description length divisible by 3.
     *
     * @param items The list of items in the receipt.
     * @return The calculated points.
     */
    private int multipleOfItems(List<Items> items) {
        int priceEarned = 0;

        for (Items item : items) {
            if (item.getShortDescription().trim().length() % 3 == 0)
                priceEarned += (int) Math.ceil(Double.parseDouble(item.getPrice()) * 0.2);
        }

        return priceEarned;
    }

    /**
     * Calculates points if the purchase date is an odd day of the month.
     *
     * @param purchaseDate The date of purchase in ISO format.
     * @return The calculated points.
     */
    private int purchaseDateIsOdd(String purchaseDate) {
        LocalDate convertedDate = LocalDate.parse(purchaseDate, DateTimeFormatter.ISO_LOCAL_DATE);

        if (convertedDate.getDayOfMonth() % 2 != 0)
            return priceEarnedForOddDay;

        return 0;
    }

    /**
     * Calculates points if the purchase time is within a specific time window (2:00 PM - 4:00 PM).
     *
     * @param purchaseTime The time of purchase in HH:mm format.
     * @return The calculated points.
     */
    private int purchaseTime(String purchaseTime) {
        LocalTime time = LocalTime.parse(purchaseTime);

        if (time.isAfter(purchaseStartTime) && time.isBefore(purchaseStopTime))
            return priceEarnedForTime;

        return 0;
    }
}
