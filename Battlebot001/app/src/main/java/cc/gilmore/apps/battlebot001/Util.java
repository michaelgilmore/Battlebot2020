package cc.gilmore.apps.battlebot001;

import java.util.UUID;

/**
 * Created by Mike on 10/19/2019.
 */
public class Util {
    public static UUID uuidFromString(String uid) {
        return UUID.fromString("0000" + uid + "-0000-1000-8000-00805f9b34fb");
    }
}
