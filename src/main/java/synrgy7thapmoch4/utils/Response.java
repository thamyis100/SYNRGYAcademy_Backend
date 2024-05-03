package synrgy7thapmoch4.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Response {

    private static final Logger logger = LogManager.getLogger(Response.class);

    public Map sukses(Object obj){
        logger.info("Success response: {}", obj);
        Map map = new HashMap();
        map.put("data", obj);
        map.put("status", 200);
        map.put("message", "Success");
        return map;
    }

    public Map error(Object obj, Object code){
        logger.error("Error response: code={}, message={}", code, obj);
        Map map = new HashMap();
        map.put("status", code);
        map.put("message", obj);
        return map;
    }

    public boolean chekNull(Object obj){
        if(obj == null){
            logger.warn("Null object detected.");
            return true;
        }
        return  false;
    }
}