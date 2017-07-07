package model;

import java.sql.Date;
import java.util.Map;

/**
 * Created by andreaperazzoli on 07/07/17.
 */
public class Soloist {
    private String stageName;
    private String mainGenre;
    private String birthday;

    public Soloist(Map<String,Object> productInfo) {
        this.stageName = (String)productInfo.get("soloist");
        this.mainGenre = (String)productInfo.get("maingenre");
        this.birthday = (String)productInfo.get("birthday");

    }

    public String getStageName() {
        return stageName;
    }

    public String getMainGenre() {
        return mainGenre;
    }

    public String getBirthday() {
        return birthday;
    }

    @Override
    public String toString() {
        return stageName;
    }
}
