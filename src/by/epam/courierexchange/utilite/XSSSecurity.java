package by.epam.courierexchange.utilite;

public class XSSSecurity {
    private final static String LEFT_TAG = "<";
    private final static String RIGHT_TAG = ">";
    private final static String REPLACING_LEFT_TAG = "&lt";
    private final static String REPLACING_RIGHT_TAG = "&gt";

    private XSSSecurity() {
    }

    public static String secure(String string) {
        return string == null ? null : string.replaceAll(LEFT_TAG, REPLACING_LEFT_TAG).replaceAll(RIGHT_TAG, REPLACING_RIGHT_TAG);
    }
}
