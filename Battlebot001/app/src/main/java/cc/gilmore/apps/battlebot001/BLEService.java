package cc.gilmore.apps.battlebot001;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Mike on 10/19/2019.
 */
public class BLEService {
    public UUID uuid;
    protected Map<String, UUID> uuids;
    protected Map<String, BLECharacteristic> characteristics;

    public BLEService() {
        uuids = new HashMap<>();
        characteristics = new HashMap<>();
    }
}
