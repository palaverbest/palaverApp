package helper;

import org.json.JSONObject;

/**
 * Created by stefa on 22.02.2016.
 */
public class ApiResult {
    public JSONObject json;
    public NetworkHelper.ApiCommand command;

    public ApiResult(JSONObject json, NetworkHelper.ApiCommand command) {
        this.command = command;
        this.json = json;
    }
}
