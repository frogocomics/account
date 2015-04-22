package com.frogocomics.api.account;

import org.spongepowered.api.entity.player.Player;

import java.util.Map;

public class AccountExporter {

    private Map<String, Object> settings;
    private final String accountJson;
    private final String playerUniqueId;
    private final Player player;

    public AccountExporter(Map<String, Object> settings, String accountJson, String playerUniqueId, Player player) {
        this.settings = settings;
        this.accountJson = accountJson;
        this.playerUniqueId = playerUniqueId;
        this.player = player;
    }

    public Map<String, Object> getSettings() {
        return settings;
    }

    public String getUniqueId() {
        return playerUniqueId.trim();
    }

    public String getAccountJson() {
        return accountJson;
    }

    public Player getPlayer() {
        return player;
    }
}
