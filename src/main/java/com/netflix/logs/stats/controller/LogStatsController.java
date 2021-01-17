package com.netflix.logs.stats.controller;

import com.netflix.logs.stats.model.LogRecordRequest;
import com.netflix.logs.stats.model.LogStatsResponse;
import com.netflix.logs.stats.repository.LogRecordsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LogStatsController {

    @Autowired
    LogRecordsRepository logRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(LogStatsController.class);


    @PostMapping("/logs")
    String newLogRecord(@RequestBody LogRecordRequest newLog) {

        LOGGER.info("Adding New Log Record");

        int insertValue = logRepo.addLogRecord(newLog);
        if (insertValue == 1) {
            return "SUCCESS";
        } else {
            return "FAILURE";
        }

    }


    @GetMapping("/api/statistics/topRequestUrls")
    public List<LogStatsResponse> topRequestURLs(@RequestParam(name = "from") String from,
                                                 @RequestParam(name = "to") String to,
                                                 @RequestParam(name = "groupBy", required = false) String groupBy) {

        LOGGER.info("topRequestURLs from= " + from + " to= " + "groupBy= " + groupBy);

        List<LogStatsResponse> topURLS = new ArrayList<>();

        topURLS = logRepo.getTopURLs(from, to, groupBy);

        return topURLS;
    }

    @GetMapping("/api/statistics/avgRequestDuration")
    public List<LogStatsResponse> avgRequestDuration(@RequestParam(name = "from") String from,
                                                     @RequestParam(name = "to") String to,
                                                     @RequestParam(name = "groupBy", required = false) String groupBy) {

        LOGGER.info("avgRequestDuration from= " + from + " to= " + "groupBy= " + groupBy);

        List<LogStatsResponse> avgRequestDuration = new ArrayList<>();

        avgRequestDuration = logRepo.getAvgRequestDuration(from, to, groupBy);

        return avgRequestDuration;
    }

    @GetMapping("/api/statistics/successRatio")
    public List<LogStatsResponse> successRatio(@RequestParam(name = "from") String from,
                                               @RequestParam(name = "to") String to,
                                               @RequestParam(name = "groupBy", required = false) String groupBy) {

        LOGGER.info("successRatio from= " + from + " to= " + "groupBy= " + groupBy);

        List<LogStatsResponse> successRatio = new ArrayList<>();

        if (groupBy != null && !groupBy.isEmpty()) {
            successRatio = logRepo.getSuccessRatioByUser(from, to);
        } else {
            successRatio = logRepo.getTotalSuccessRatio(from, to);
        }

        return successRatio;
    }


}
