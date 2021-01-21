package ex3_json;

public interface JsonValue {
    public JsonValue get(int i) throws QueryException;

    public JsonValue get(String s) throws QueryException;
}