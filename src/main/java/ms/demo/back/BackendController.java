package ms.demo.back;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "/backend", produces = {APPLICATION_JSON_VALUE})
public class BackendController {

    @RequestMapping(method = RequestMethod.GET)
    public String defaultPage() {
        try {
            return "This is backend worker:" + workerIP();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error happen:" + e.getMessage();
        }

    }

    @RequestMapping(value = "/hex", method = RequestMethod.GET)
    public String convert(@RequestParam(name = "str", required = true) String str) {
        try {
            return "Hex code:" + bytesToHex(str.getBytes(Charset.forName("UTF-8"))) + "\n\nWorker--" + workerIP();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error when calculating:" + e.getMessage();
        }
    }

    private String workerIP()throws UnknownHostException{
        return InetAddress.getLocalHost().getHostAddress().toString();
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

}
