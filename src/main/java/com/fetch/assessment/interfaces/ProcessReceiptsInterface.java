package com.fetch.assessment.interfaces;

import com.fetch.assessment.dto.PointsResponseJson;
import com.fetch.assessment.dto.ProcessReceiptsRequestJson;
import com.fetch.assessment.dto.ProcessReceiptsResponseJson;

import java.util.UUID;

public interface ProcessReceiptsInterface {

    ProcessReceiptsResponseJson storeReceipts(ProcessReceiptsRequestJson processReceiptsRequestJson);
    PointsResponseJson calculatePoints(UUID id) throws Exception;
}
