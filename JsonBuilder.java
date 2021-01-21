package ex3_json;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

public class JsonBuilder implements JsonValue {
    private CharScanner cs;
    private JsonObject obj;
    private JsonArray jarr;
    private char first;
    private char temp;

    public JsonBuilder(CharScanner cs) throws SyntaxException, QueryException {
        this.cs = cs;
        this.obj = new JsonObject();
        this.jarr = new JsonArray();
        ParseJson();
    }

    public JsonBuilder(File file) throws FileNotFoundException, QueryException, SyntaxException {
        this.cs = new CharScanner(file);
        this.obj = new JsonObject();
        this.jarr = new JsonArray();
        ParseJson();
    }

    public void ParseJson() throws SyntaxException, QueryException {
        temp = cs.peek();
        if (temp != '{' && temp != '[' && !Character.isDigit(temp) && temp != '"' && temp != '-') {
            throw new SyntaxException();
        }
        first = temp;
        parseObject();

    }

    public JsonObject parseObject() throws SyntaxException, QueryException {
        char c = 0;
        int count = 0;
        JsonValue[] val = new JsonValue[2];

        cs.next();
        while (cs.hasNext()) {
            c = cs.peek();

            switch (c) {
                case '{':
                    val[count] = new JsonBuilder(cs);
                    break;
                case '[':
                    val[count] = parseArray();
                    break;
                case '-':
                    val[count] = parseNumber();
                    break;
                case '"':
                    val[count] = parseString();
                    break;
                default:
                    if (Character.isDigit(c)) {
                        val[count] = parseNumber();
                    } else {
                        if (c == ',') {
                            count = 0;
                        } else if (c == ':') {
                            count = 1;
                        }
                        cs.next();
                    }
                    break;
            }
            if (val[0] != null && val[1] != null) {
                obj.getO().put(val[0].toString(), val[1]);
                val[0] = null;
                val[1] = null;
            }

        }

        return obj;
    }

    public JsonArray parseArray() throws SyntaxException {
        JsonArray arr = new JsonArray();
        while (cs.hasNext() && cs.peek() != ']') {
            if (cs.peek() == '"') {
                JsonValue st = parseString();
                arr.getA().add(st);
            }
            if (cs.peek() != ']') {
                cs.next();
            }
        }
        return arr;
    }

    public JsonString parseString() throws SyntaxException {
        String key = "";
        if (cs.peek() == '"') {
            cs.next();
            while (cs.hasNext()) {
                if (cs.peek() != '"') {
                    key += cs.next();
                } else {
                    break;
                }
            }
            if (cs.peek() == '"') {
                cs.next();
            }
        }
        return new JsonString(key);
    }

    /**
     * checking zero and do somthing about it
     * livdok at haefes vankoda po batoch hafunctzia
     */
    public JsonNumber parseNumber() throws SyntaxException {
        String num = "";
        if (cs.peek() == '-') {
            num += cs.next();
        }
        if(cs.peek() == '0')
        {
            num += cs.next();
            if(cs.peek() != '.')
            {
                throw new SyntaxException();
            }
            num += cs.next();

            if (Character.isDigit(cs.peek()) || cs.peek() == 'e' || cs.peek() == 'E' || cs.peek() == '-') {
                while (cs.hasNext()) {
                    if (Character.isDigit(cs.peek()) || cs.peek() == 'e' || cs.peek() == 'E' || cs.peek() == '-') {
                        num += cs.next();
                    } else {
                        if (cs.peek() != ',' && cs.peek() != ':') {
                            throw new SyntaxException();
                        }
                        break;
                    }
                }
            } else {
                if (cs.peek() != ',' && cs.peek() != ':') {
                    throw new SyntaxException();
                }
            }
        }
        if (Character.isDigit(cs.peek())) {
            while (cs.hasNext()) {
                if (Character.isDigit(cs.peek())) {
                    num += cs.next();
                } else {
                    if (cs.peek() != ',' && cs.peek() != ':') {
                        throw new SyntaxException();
                    }
                    break;
                }
            }
        } else {
            if (cs.peek() != ',' && cs.peek() != ':') {
                throw new SyntaxException();
            }
        }
        if (!num.contains("e") && !num.contains("E") && num.contains("-")) {
            throw new SyntaxException();
        }

        Double n = Double.parseDouble(num);
        if(n >= 1 || n <= -1)
        {
            return new JsonNumber(n.intValue());
        }
        return new JsonNumber(n);
    }


    public JsonValue get(int i) throws QueryException {
        return jarr.getA().get(i);
    }

    public JsonValue get(String s) throws QueryException {
        return obj.get(s);
    }

    public String toString() {
        return toString(1);
    }

    public String toString(int i) {

        StringBuilder res = new StringBuilder("{\n");

        for (Map.Entry<String, JsonValue> pair : obj.getO().entrySet()) {
            for(int j = 0; j < i; j++) {
                res.append("  ");
            }
            res.append(pair.getKey()).append(": ");
            if(pair.getValue() instanceof JsonBuilder) {
                res.append(((JsonBuilder) pair.getValue()).toString(i+1));
            }
            else {
                res.append(pair.getValue()).append('\n');
            }
        }
        for(int j = 0; j < i - 1; j++) {
            res.append("  ");
        }
        return res + "}\n";
    }
}