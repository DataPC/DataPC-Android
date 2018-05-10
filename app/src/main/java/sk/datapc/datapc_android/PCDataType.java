package sk.datapc.datapc_android;

import org.json.*;
import java.util.ArrayList;
import java.util.List;

public class PCDataType {
    private int PCid;
    private String location;
    private List<ComponentDataType> components;

    public PCDataType(String json){
        try {
            JSONObject obj = new JSONObject(json);
            PCid = obj.getInt("id");
            location = obj.getString("location");
            components = new ArrayList<ComponentDataType>();

            JSONArray arr = obj.getJSONArray("komponenty");
            for (int i = 0; i < arr.length(); i++)
            {
                JSONObject component = arr.getJSONObject(i);

                components.add(new ComponentDataType(component.getInt("id"),
                        component.getString("ct_name"),
                        component.getString("ma_name"),
                        component.getString("m_name")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            // TODO pridaj log
        }
    }

    public int getPCid() {
        return PCid;
    }

    public String getLocation() {
        return location;
    }

    public List<ComponentDataType> getComponents() {
        return components;
    }
}
