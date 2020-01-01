package cc.gilmore.apps.battlebot001;

/**
 * Created by Mike on 10/19/2019.
 */
public class GenericAccessService extends BLEService {
    public GenericAccessService() {
        uuid = Util.uuidFromString("1800");

        //property READ
        uuids.put("device name", Util.uuidFromString("2A00"));
        //property READ
        uuids.put("appearance", Util.uuidFromString("2A01"));
        //property READ, WRITE
        //write type WRITE REQUEST
        uuids.put("peripheral privacy flag", Util.uuidFromString("2A02"));
        //property READ, WRITE
        //write type WRITE REQUEST
        uuids.put("reconnection address", Util.uuidFromString("2A03"));
        //property READ
        uuids.put("peripheral parameters", Util.uuidFromString("2A04"));
    }
}
