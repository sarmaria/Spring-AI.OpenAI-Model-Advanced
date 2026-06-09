package com.ai.openai.advanced.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class TimeTools {

    Logger logger = LoggerFactory.getLogger(TimeTools.class);

    @Tool(name="getCurrentLocalTime", description="Gives the current time in the user zone")
    public String getCurrentLocalTime(){
        logger.info("getCurrentLocalTime()");
        return LocalDateTime.now().toString();
    }

    @Tool(name="getCurrentTimeInZone", description = "Gives the current time in given timezone")
    public String getCurrentTimeInZone(@ToolParam(description = "timezone provided by user") String zoneId){
        logger.info("getCurrentTimeInZone {}", zoneId);
        return LocalDateTime.now(ZoneId.of(zoneId)).toString();
    }
}
