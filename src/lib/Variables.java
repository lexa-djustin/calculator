package lib;

import java.util.HashMap;
import java.util.Map;

public class Variables {
    private static Map<String, Double> variables;

    static {
        variables = new HashMap<>();
        variables.put("PI", Math.PI);
    }

    private static boolean isExists(String name) {
        return variables.containsKey(name);
    }

    public static double get(String key) {
        if (!isExists(key)) {
            return 0;
        }

        return variables.get(key);
    }

    public static void set(String name, double value) {
        variables.put(name, value);
    }
}
