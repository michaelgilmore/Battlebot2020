package cc.gilmore.apps.battlebot001;

import java.util.UUID;

/**
 * Created by Mike on 10/19/2019.
 */
public class BLECharacteristic {
    public UUID uuid;
    private String descriptors;
    private String properties;
    private String writeTypes;

    public BLECharacteristic(String uid, String properties, String writeTypes, String descriptors) {
        uuid = Util.uuidFromString(uid);
        this.descriptors = descriptors;
        this.properties = properties;
        this.writeTypes = writeTypes;
    }
}
