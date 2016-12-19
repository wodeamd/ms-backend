package ms.demo.back;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import static spark.Spark.get;
import static spark.Spark.port;

public class BackendController {
    public static void main(String[] args) {
        port(8071);
        get("/", (req, res) -> "This is backend worker:" + InetAddress.getLocalHost().getHostAddress().toString());
        get("/backend/hex", (req, res) -> {
            String str = req.queryParams("str");
            try {
                return "Hex code:" + bytesToHex(str.getBytes(Charset.forName("UTF-8"))) + "\n\nWorker--" + workerIP();
            } catch (Exception e) {
                e.printStackTrace();
                return "Error when calculating:" + e.getMessage();
            }
        });
    }

    private static String workerIP()throws UnknownHostException{
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
