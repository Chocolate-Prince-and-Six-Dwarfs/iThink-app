package com.example.myapplication.model.http;

import com.example.myapplication.model.pojo.App;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ApiHelper {

    public int parseJSONWithGSON(String jsonData){

        Gson gson = new Gson();
        List<App> appList = gson.fromJson(jsonData,new TypeToken<App>(){}.getType());

        App app = appList.get(0);

        return app.getFlag();
    }
}
