package cc.gilmore.apps.battlebot001;

/**
 * Created by Mike on 10/19/2019.
 */
public class DeviceInformationService extends BLEService {

    public DeviceInformationService() {
        uuid = Util.uuidFromString("180A");

        //All have property READ
        uuids.put("system id", Util.uuidFromString("2A23"));
        uuids.put("model number", Util.uuidFromString("2A24"));
        uuids.put("serial number", Util.uuidFromString("2A25"));
        uuids.put("firmware revision", Util.uuidFromString("2A26"));
        uuids.put("hardware revision", Util.uuidFromString("2A27"));
        uuids.put("software revision", Util.uuidFromString("2A28"));
        uuids.put("manufacturer name", Util.uuidFromString("2A29"));
        uuids.put("regulatory cert", Util.uuidFromString("2A2A"));
        uuids.put("pnp id", Util.uuidFromString("2A50"));
    }
}
