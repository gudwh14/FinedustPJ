package com.hanium.chj.finedustpj;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FavoriteRequest extends StringRequest {
    final static private String URL = "http://14.63.171.79/favorite.php";
    private Map<String, String> parameters;

    public FavoriteRequest(String userID, Response.Listener <String> listener ) {
        super(Method.POST, URL, listener ,null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
    }

    public Map<String,String> getParams() {
        return parameters;
    }
}
