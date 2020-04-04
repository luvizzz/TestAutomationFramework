package app.utils;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static String toJson(List<JsonProperty> jsonProperties) {
        return jsonProperties.stream()
                .map(JsonProperty::toString)
                .collect(Collectors.joining(",\n  ", "{\n  ", "\n}"));
    }
}
