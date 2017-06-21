package helper;

import org.json.JSONObject;

/**
 * Created by stefa on 19.02.2016.
 */
public class GeneralHelper {

    private static GeneralHelper instance;

    public static GeneralHelper getInstance() {
        if (GeneralHelper.instance == null) {
            GeneralHelper.instance = new GeneralHelper();
        }
        return GeneralHelper.instance;
    }

    public boolean isSuccessful(JSONObject json) {
        try {
            if (json.getInt("MsgType") == 1) {
                return true;
            }
        } catch(Exception e) { e.printStackTrace();};
        return false;
    }
}