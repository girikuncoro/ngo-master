package core.meta;

import java.util.HashMap;
import java.util.Map;

public enum ActionsOnItem {
    Harvested, Planted, Sold;
    protected static final Map<String, ActionsOnItem> sv;

    static {
        sv = new HashMap<>();
        for (ActionsOnItem v : values()) {
            sv.put(v.toString().toLowerCase(), v);
        }


    }

    public static ActionsOnItem fromString(String act) {
        if (null == act || act.trim().isEmpty())
            throw new IllegalArgumentException("Action can not be null nor empty");
        return sv.get(act.toLowerCase());
    }
}
