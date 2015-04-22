package com.frogocomics.api.account;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.spongepowered.api.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccountBuilder {

    private Player player;
    private GsonBuilder gsonBuilder;
    private Map<String, Object> settings = new HashMap<>();

    long defaultAmount;
    boolean setMoney = false;
    boolean setDefaultAmount = false;

    private Gson gson = gsonBuilder.disableHtmlEscaping().enableComplexMapKeySerialization().serializeNulls().setPrettyPrinting().create();

    protected AccountBuilder(Player player) {
        this.player = player;
    }

    public AccountBuilder setUniqueId(UUID uuid) {
        settings.put("player.uuid", uuid);
        return null;
    }

    public AccountBuilder setMoney(long amount) {
        if(amount < 0) {
            settings.put("player.money", getDefaultAmount());
            return null;
        }

        settings.put("player.money", amount);
        setMoney = true;
        return null;
    }

    public AccountBuilder setDefaultAmount(long amount) {

        setDefaultAmount = true;

        if(amount < 0) {
            this.defaultAmount = 50000;
        }

        this.defaultAmount = amount;

        return null;
    }

    public AccountBuilder setKey(String path, Object value) throws AccountException {

        if(path.equalsIgnoreCase("player.money") || path.equalsIgnoreCase("player.uuid")) {
            throw new AccountException("Please do not use the path \"player.money\" or \"player.uuid\"!");
        }

        settings.put(path.toLowerCase().trim(), value);

        return null;
    }

    private long getDefaultAmount() {
        if(!setDefaultAmount) {
            return 50000;
        }

        return defaultAmount;
    }

    public Account build() throws AccountException {
        if(!setMoney) {
            if(!setDefaultAmount) {
                throw new AccountException("Please specify the amount of money or the default amount!");
            } else {
                settings.put("player.money", getDefaultAmount());
            }
        }

        if(settings.get("player.uuid") == null) {
            throw new AccountException("Please specify a player specific UUID!");
        }

        String accountJson = gson.toJson(settings);

        return new Account(player, accountJson, player.getUniqueId().toString(), settings);
    }
}
