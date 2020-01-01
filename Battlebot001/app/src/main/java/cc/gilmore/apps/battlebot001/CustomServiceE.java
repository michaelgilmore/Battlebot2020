package cc.gilmore.apps.battlebot001;

/**
 * Created by Mike on 10/19/2019.
 */
public class CustomServiceE extends BLEService {
    public CustomServiceE() {
        uuid = Util.uuidFromString("FFE0");

        characteristics.put("A", new BLECharacteristic("FFE1", "READ, NOTIFY, WRITE_NO_RESPONSE", "WRITE REQUEST", "User Desc 2901, Client Char Config 2902"));
    }
}
