package app.utils;

public class JsonProperty {
    private String name;
    private Object value;

    public JsonProperty(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (value.getClass().equals(String.class)) {
            return String.format("\"%s\" : \"%s\"", name, value);
        } else {
            return String.format("\"%s\" : %s", name, value.toString());
        }
    }
}
