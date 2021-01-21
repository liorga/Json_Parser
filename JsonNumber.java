package ex3_json;

public class JsonNumber implements JsonValue{
    private Number k;

    public JsonNumber(Number i) {
        this.k = i;
    }

    @Override
    public JsonValue get(int i) throws QueryException{
        return (JsonValue)k;
    }

    @Override
    public JsonValue get(String s) throws QueryException {
        return (JsonValue)k;
    }

    public String toString() {
        String str = "";
        str += k;
        return str;
    }
}