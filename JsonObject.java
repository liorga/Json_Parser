package ex3_json;

import java.util.HashMap;
import java.util.Map;

public class JsonObject implements JsonValue{
    private Map<String,JsonValue> o;

    public JsonObject() {
        this.o = new HashMap<String, JsonValue>();
    }

    @Override
    public JsonValue get(int i) throws QueryException{
        return null;
    }

    @Override
    public JsonValue get(String s) throws QueryException{
        return o.get(s);
    }

    public Map<String, JsonValue> getO() {
        return o;
    }

    public void setO(Map<String, JsonValue> o) {
        this.o = o;
    }

    public String toString() {
        return "JsonObject";
    }

}