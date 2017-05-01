package cc.gilmore.apps.dressmike;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mike on 3/30/2016.
 */
public class OutfitDB {
    List<Outfit> outfits = new ArrayList<>();
    public static final String OUTFIT_DB_DATE_FORMAT = "yyyyMMdd";
    private String dbFilePath;

    public OutfitDB(String dbFilePath) {
        this.dbFilePath = dbFilePath;

        File dbFile = new File(dbFilePath);
        if(!dbFile.exists()) {
            try {
                dbFile.createNewFile();
            }
            catch(IOException io) {
                Logger.log("Unable to create new DB file (" + dbFilePath + ")", io, Logger.LogLevel.FATAL);
                return;
            }
        }

        BufferedReader br = null;
        try {
            FileInputStream fstream = new FileInputStream(dbFile);
            br = new BufferedReader(new InputStreamReader(fstream));
        }
        catch(FileNotFoundException fnf) {
            Logger.log("Unable to open DB file (" + dbFilePath + ")", fnf, Logger.LogLevel.FATAL);
            return;
        }

        if(br == null) {
            Logger.log("Unable to open DB file (" + dbFilePath + ")", Logger.LogLevel.FATAL);
            return;
        }

        try {
            String strLine;
            while ((strLine = br.readLine()) != null) {
                outfits.add(new Outfit(strLine));
            }

            br.close();
        }
        catch(IOException io) {
            Logger.log("Unable to read DB file (" + dbFilePath + ")", io, Logger.LogLevel.FATAL);
        }
    }

    private OutfitDB() {}

    public void saveTodaysOutfit(String currentShirt, String currentPants) {
        this.outfits.add(new Outfit(currentShirt, currentPants, new Date(), 0));
        persist();
    }

    private void persist() {
        BufferedWriter bw = null;
        try {
            File dbFile = new File(dbFilePath);
            FileOutputStream fstream = new FileOutputStream(dbFile);
            bw = new BufferedWriter(new OutputStreamWriter(fstream));
        }
        catch(FileNotFoundException fnf) {
            Logger.log("Unable to persist file (" + dbFilePath + ")", fnf, Logger.LogLevel.FATAL);
            return;
        }

        if(bw == null) {
            Logger.log("Unable to persist file (" + dbFilePath + ")", Logger.LogLevel.FATAL);
            return;
        }

        try {
            for(Outfit outfit : outfits) {
                bw.write(outfit.toString() + "\n");
            }

            bw.close();
        }
        catch(IOException io) {
            Logger.log("Unable to write DB file (" + dbFilePath + ")", io, Logger.LogLevel.FATAL);
        }
    }
}
