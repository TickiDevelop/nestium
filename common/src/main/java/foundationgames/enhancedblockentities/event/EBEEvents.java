package foundationgames.enhancedblockentities.event;

import java.util.ArrayList;
import java.util.List;

public class EBEEvents {
    private static final List<Runnable> RELOAD_LISTENERS = new ArrayList<>();

    public static void registerResourceReload(Runnable listener) {
        RELOAD_LISTENERS.add(listener);
    }

    public static void fireResourceReload() {
        for (Runnable listener : RELOAD_LISTENERS) {
            listener.run();
        }
    }
}
