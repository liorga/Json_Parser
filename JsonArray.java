package ex3_json;

import java.util.ArrayList;
import java.util.List;

public class JsonArray implements JsonValue{
    private List<JsonValue> a;


    public JsonArray() {
        this.a = new ArrayList<JsonValue>();
    }


    @Override
    public JsonValue get(int i) throws QueryException{
        return a.get(i);
    }

    @Override
    public JsonValue get(String s) throws QueryException{
        return null;
    }

    public String toString() {
        return String.valueOf(a);
    }

    public List<JsonValue> getA() {
        return a;
    }

    public void setA(List<JsonValue> a) {
        this.a = a;
    }
}