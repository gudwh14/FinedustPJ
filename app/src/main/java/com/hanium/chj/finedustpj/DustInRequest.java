package com.hanium.chj.finedustpj;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DustInRequest extends StringRequest{
    final static private String URL = "http://14.63.171.79/DustInput.php";
    private Map<String,String> parameters;

    public  DustInRequest(String userID, String dust, Response.Listener<String> listener) {
        super(Method.POST, URL, listener,null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("dust",dust);
    }

    public Map<String,String> getParams () {
        return parameters;
    }
}
