package cc.gilmore.apps.dressmike;

import java.util.Date;

/**
 * Created by Mike on 3/31/2016.
 */
public class Outfit {

    String shirt;
    String pants;
    Date wornDate;
    int rating;//5=Ideal, 4=Good, 3=OK, 2=Debatable, 1=Never Wear Again

    private static final String DELIM = ";";

    public Outfit(String shirt, String pants, Date wornDate, int rating) {
        this.shirt = shirt;
        this.pants = pants;
        this.wornDate = wornDate;
        this.rating = rating;
    }

    public Outfit(String dbFileLine) {
        fromString(dbFileLine);
    }

    private Outfit() {}

    @Override
    public String toString() {
        return shirt + DELIM + pants + DELIM + Util.formatDate(wornDate, OutfitDB.OUTFIT_DB_DATE_FORMAT) + DELIM + rating;
    }

    public void fromString(String s) {
        String[] tokens = s.split(DELIM);
        if(tokens == null || tokens.length < 4) {
            Logger.log("Unexpected Outfit line format (" + s + ")", Logger.LogLevel.ERROR);
            return;
        }
        shirt = tokens[0];
        pants = tokens[1];
        wornDate = Util.parseDate(tokens[2], OutfitDB.OUTFIT_DB_DATE_FORMAT);
        rating = Integer.parseInt(tokens[3]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Outfit outfit = (Outfit) o;

        if (!shirt.equals(outfit.shirt)) return false;
        if (!pants.equals(outfit.pants)) return false;
        return wornDate.equals(outfit.wornDate);

    }

    @Override
    public int hashCode() {
        int result = shirt.hashCode();
        result = 31 * result + pants.hashCode();
        result = 31 * result + wornDate.hashCode();
        return result;
    }

    public String getShirt() {
        return shirt;
    }
    public String getPants() {
        return pants;
    }
    public Date getWornDate() {
        return wornDate;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
}
