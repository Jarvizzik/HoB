package com.example.user.hob;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://jarvizz.000webhostapp.com/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String surname, String username, int age, String city,String url, String password, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("surname", surname);
        params.put("username", username);
        params.put("age", age+"");
        params.put("city", city);
        params.put("url", url);
        params.put("password",password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
