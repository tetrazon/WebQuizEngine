package engine.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestHeader;

public class Utils {
    public static String getNameFromAuthHeader(@RequestHeader("Authorization") String authorization) {
        Base64 base64 = new Base64();
        return new String(base64.decode(authorization.substring(5).getBytes())).split(":")[0];
    }
}
