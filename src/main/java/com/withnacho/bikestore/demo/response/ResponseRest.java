package com.withnacho.bikestore.demo.response;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class sets the structure for the information metadata obtained after executing certain queries on the console.
 */
public class ResponseRest {

    private ArrayList<HashMap<String, String>> metadata = new ArrayList<>();

    public ArrayList<HashMap<String, String>> getMetadata() {
        return metadata;
    }

    public void setMetadata(String type, String code, String data) {

        HashMap<String, String> map = new HashMap<String, String>();

        map.put("type", type);
        map.put("code", code);
        map.put("data", data);

        metadata.add(map);
    }



}
