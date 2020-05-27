package com.paytel.task.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class ExtractLogData {

    public static final String DATE_KEY = "date";
    public static final String LOG_LEVEL_KEY = "lvl";
    public static final String LOG_MESSAGE_KEY = "msg";
    public static final String CLASS_NAME_KEY = "className";

    private final String[] timeRgx = {
            //date and time
            "(?<timestamp>\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{3})", //time with .
            "(?<timestamp>\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3})", //time with ,
            //date only
            "(?<timestamp>\\d{4}-\\d{2}-\\d{2})"
            //assume that logs with time only are invalid
    };
    private final String levelRgx = "(?<level>INFO|ERROR|WARN|TRACE|DEBUG|FATAL)";

    private final String[] msgRgx = {
            "(?<= : ).*",
            "(?<= - ).*"
    };

    private final String[] classRgx = {".*(?=\\s:)", ".*(?=\\s-)"};

    public Map<String, String> extractLogDataFromString(String logMessage) {
        Map<String, String> result = new HashMap<>();

        Pattern pattern;
        Matcher matcher;
        for (String dateRgx : timeRgx
                ) {
            pattern = Pattern.compile(dateRgx);
            matcher = pattern.matcher(logMessage.substring(0, 24)); //assume that date will be first
            if (matcher.find()) {
                result.put(DATE_KEY, matcher.group("timestamp"));
                break;
            }
        }

        pattern = Pattern.compile(levelRgx);
        matcher = pattern.matcher(logMessage);
        if (matcher.find()) {
            result.put(LOG_LEVEL_KEY, matcher.group("level"));
        }

        for (String mRgx : msgRgx
                ) {
            pattern = Pattern.compile(mRgx);
            matcher = pattern.matcher(logMessage);
            if (matcher.find()) {
                result.put(LOG_MESSAGE_KEY, matcher.group(0));
                break;
            }
        }

        String[] temp;
        for (String cRgx : classRgx
                ) {
            pattern = Pattern.compile(cRgx);
            matcher = pattern.matcher(logMessage);
            if (matcher.find()) {
                temp = matcher.group(0).split(" ");
                result.put(CLASS_NAME_KEY, temp[temp.length - 1]);
                break;
            }
        }
        return result;
    }

    public Date convertDate(Map<String, String> logsInfoMap) {
        Date result = null;
        if (logsInfoMap.containsKey(ExtractLogData.DATE_KEY)) {
            String dateString = logsInfoMap.get(ExtractLogData.DATE_KEY);
            dateString = dateString.replace(",", ".");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            try {
                result = format.parse(dateString);
            } catch (ParseException e) {
                format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    result = format.parse(dateString);
                } catch (ParseException e1) {
                    log.error("", e1);
                }
            }
        }
        return result;
    }
}
