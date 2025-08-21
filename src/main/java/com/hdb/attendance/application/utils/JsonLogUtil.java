package com.hdb.attendance.application.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;

@UtilityClass
public class JsonLogUtil {

    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static void logJson(Logger logger, String rawJson, String prefix) {
        try {
            JsonNode jsonNode = mapper.readTree(rawJson);
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
            logger.info("{}:\n{}", prefix, prettyJson);
        } catch (Exception e) {
            logger.warn("⚠️ Không thể parse JSON, log thô:\n{}", rawJson);
        }
    }

    public static void logJson(Logger logger, Object object, String prefix) {
        try {
            JsonNode jsonNode = mapper.valueToTree(object);  // convert object to JsonNode
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
            logger.info("{}:\n{}", prefix, prettyJson);
        } catch (Exception e) {
            logger.warn("⚠️ Không thể convert object sang JSON: {}", e.getMessage());
        }
    }

}


