package cc.gilmore.apps.battlebot001;

/**
 * Created by Mike on 10/19/2019.
 */
public class CustomServiceF extends BLEService {
    public CustomServiceF() {
        uuid = Util.uuidFromString("FFF0");

        characteristics.put("1", new BLECharacteristic("FFF1", "READ, WRITE", "WRITE REQUEST", "2901"));
        characteristics.put("2", new BLECharacteristic("FFF2", "READ", "", "2901"));
        characteristics.put("3", new BLECharacteristic("FFF3", "WRITE", "WRITE REQUEST", "2901"));
        characteristics.put("4", new BLECharacteristic("FFF4", "NOTIFY", "", "User Desc 2901, Client Char Config 2902"));
        characteristics.put("5", new BLECharacteristic("FFF5", "READ, NOTIFY, WRITE_NO_RESPONSE", "WRITE REQUEST", "2901"));
    }

    public BLECharacteristic getCharacteristic(String key) {
        return characteristics.get(key);
    }
}
