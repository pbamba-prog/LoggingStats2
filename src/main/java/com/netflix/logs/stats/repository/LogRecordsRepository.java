package com.netflix.logs.stats.repository;

import com.netflix.logs.stats.model.LogRecordRequest;
import com.netflix.logs.stats.model.LogStatsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class LogRecordsRepository {

    @Autowired
    JdbcTemplate template;

    private static final Logger LOGGER = LoggerFactory.getLogger(LogRecordsRepository.class);


    /* Adding into database table */
    public int addLogRecord(LogRecordRequest log) {

        LOGGER.info("addLogRecord " + log.toString());


        String query = "INSERT INTO LOG_RECORDS VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        LOGGER.info("query " + query);


        String timeStampStr = log.getTimestamp();
        OffsetDateTime odt = OffsetDateTime.parse(timeStampStr);
        String requestDurationStr = log.getRequestDuration();
        requestDurationStr = requestDurationStr.replaceAll("[^\\d]", "");

        int reqDur = Integer.parseInt(requestDurationStr);
        int result = template.update(query,
                odt,
                log.getVersion(),
                log.getMessage(),
                reqDur,
                log.getSourceIp(),
                log.getRequestMethod(),
                log.getRequestEndpoint(),
                log.getRequestProtocol(),
                log.getRequestUser(),
                log.getResponseStatus(),
                log.getResponseSize());
        return result;
    }

    public List<LogStatsResponse> getTopURLs(String from, String to, String groupBy) {
        LOGGER.info("getTopURLs ");

        String query = "SELECT DISTINCT requestendpoint, requestUser FROM log_records " +
                " WHERE timestamp between TIMESTAMP '" + from +
                "' and  TIMESTAMP '" + to + "'";
        if (groupBy != null && !groupBy.isEmpty()) {
            query = query + " GROUP BY requestUser,requestendpoint";
        }
        LOGGER.info("query " + query);
        List<LogStatsResponse> respList = new ArrayList<>();
        List<Map<String, Object>> retList = template.queryForList(query);
        for (Map<String, Object> rowMap : retList) {
            LogStatsResponse resp = new LogStatsResponse();
            resp.setRequestEndpoint((String) rowMap.get("requestEndpoint"));
            resp.setUser((String) rowMap.get("requestUser"));
            respList.add(resp);
        }
        return respList;
    }

    public List<LogStatsResponse> getAvgRequestDuration(String from, String to, String groupBy) {
        LOGGER.info("getAvgRequestDuration ");

        String query = "SELECT  AVG(REQUESTDURATION) AS duration ";

        if (groupBy != null && !groupBy.isEmpty()) {
            query = query + " ,requestUser";
        }
        query = query + " from LOG_RECORDS" +
                " WHERE timestamp between TIMESTAMP '" + from +
                "' and  TIMESTAMP '" + to + "'";
        if (groupBy != null && !groupBy.isEmpty()) {
            query = query + " GROUP BY requestUser";
        }
        LOGGER.info("query " + query);
        List<LogStatsResponse> respList = new ArrayList<>();
        List<Map<String, Object>> retList = template.queryForList(query);
        for (Map<String, Object> rowMap : retList) {
            LogStatsResponse resp = new LogStatsResponse();
            resp.setAvgRequestDuration((Integer) rowMap.get("duration"));
            resp.setUser((String) rowMap.get("requestUser"));
            respList.add(resp);
        }
        return respList;
    }

    public List<LogStatsResponse> getSuccessRatioByUser(String from, String to) {
        LOGGER.info("getSuccessRatioByUser ");

        String query = "select requestuser, " +
                "sum (case when responsestatus = 200 then 1" +
                " else 0 end) As PASS, " +
                "sum (case when responsestatus <> 200 then 1" +
                " else 0 end) As FAIL, " +
                "(sum (case when responsestatus = 200 then 1" +
                " else 0 end) + sum (case when responsestatus <> 200 then 1" +
                " else 0 end)) As TOTAL, " +
                "sum (case when responsestatus = 200 then 1 " +
                " else 0 end)*100/\n" +
                "(sum (case when responsestatus = 200 then 1 " +
                " else 0 end) + sum (case when responsestatus <> 200 then 1 " +
                " else 0 end)) as successRatio " +
                "from log_records " +
                "WHERE timestamp between TIMESTAMP '" + from + "' and  TIMESTAMP '" + to + "'" +
                "group by requestuser";

        LOGGER.info("query " + query);

        List<LogStatsResponse> respList = new ArrayList<>();
        List<Map<String, Object>> retList = template.queryForList(query);
        for (Map<String, Object> rowMap : retList) {
            LogStatsResponse resp = new LogStatsResponse();
            resp.setSuccessRatio((Long) rowMap.get("successRatio"));
            resp.setUser((String) rowMap.get("requestUser"));
            respList.add(resp);
        }
        return respList;
    }

    public List<LogStatsResponse> getTotalSuccessRatio(String from, String to) {

        LOGGER.info("getTotalSuccessRatio");
        String query = "select " +
                "(sum (case when responsestatus = 200 then 1 " +
                "else 0 end) *100/ (select count(*) from log_records)) AS successRatio" +
                " from log_records " +
                "WHERE timestamp between TIMESTAMP '" + from + "' and  TIMESTAMP '" + to + "'";
        LOGGER.info("query " + query);

        List<LogStatsResponse> respList = new ArrayList<>();
        List<Map<String, Object>> retList = template.queryForList(query);
        for (Map<String, Object> rowMap : retList) {
            LogStatsResponse resp = new LogStatsResponse();
            resp.setSuccessRatio((Long) rowMap.get("successRatio"));
            respList.add(resp);
        }
        return respList;
    }
}


