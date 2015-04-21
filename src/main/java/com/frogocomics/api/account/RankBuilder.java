package com.frogocomics.api.account;

import org.spongepowered.api.text.Text;

import java.util.HashMap;
import java.util.Map;

public class RankBuilder {

    private boolean nameF, idF, dnameF, prefixF, permF;

    private Map<String, Object> accountSettings = new HashMap<>();

    public RankBuilder setName(String name) {
        nameF = true;
        return null;
    }

    public RankBuilder setId(short id1, short id2) throws InvaildIdException {
        String id = (id1 + "-" + id2);
        idF = true;
        if(!(id.length() == 5)) {
            idF = false;
            throw new InvaildIdException();
        }
        return null;
    }

    public RankBuilder setDisplayName(Text name) {
        dnameF = true;
        return null;
    }

    public RankBuilder setPrefix(Text playerPrefix) {
        accountSettings.put("prefix", playerPrefix);
        prefixF = true;
        return null;
    }

    public RankBuilder setPermission(String permission) {
        accountSettings.put("permission", permission);
        permF = true;
        return null;
    }

    public Rank build() throws RankFormatException{
        if(!nameF||!dnameF||!prefixF||!permF) {
            throw new RankFormatException();
        }
        accountSettings.put("complete", true);
        return new Rank();
    }
}
