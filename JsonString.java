package ex3_json;

public class JsonString implements JsonValue{

    private String str;

    public JsonString(String s) {
        this.str = s;
    }

    @Override
    public JsonValue get(int i) throws QueryException{
        return null;
    }

    @Override
    public JsonValue get(String s) throws QueryException{
        return null;
    }

    public String get(){
        return str;
    }

    public String toString() {
        return str;
    }

}