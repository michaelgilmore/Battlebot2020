package cc.gilmore.apps.battlebot001;

/**
 * Created by Mike on 10/19/2019.
 */
public class GenericAttributeService extends BLEService {
    public GenericAttributeService() {
        uuid = Util.uuidFromString("1801");

        //property INDICATE
        uuids.put("service changed", Util.uuidFromString("2A05"));

        //Descriptors
        //Client Characteristic Configuration
        //2902
    }
}
