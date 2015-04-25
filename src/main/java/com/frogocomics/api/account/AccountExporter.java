package com.frogocomics.api.account;

import org.spongepowered.api.entity.player.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountExporter {

    private Map<String, Object> settings = null;
    private final String accountJson;
    private final String playerUniqueId;
    private final Player player;

    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public AccountExporter(Map<String, Object> settings, String accountJson, String playerUniqueId, Player player) throws AccountException {
        this.settings = settings;
        this.accountJson = accountJson;
        this.playerUniqueId = playerUniqueId;
        this.player = player;

        if(settings == null) {
            throw new AccountException("Internal error.");
        }
    }

    public void exportToMySqlDatabase(String address) throws SQLException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(address);
            statement = connection.createStatement();

            List<String> entry = new ArrayList<>();
            List<Object> value = new ArrayList<>();
            String[] k; Object[] v;

            for(Map.Entry<String, Object> map : settings.entrySet()) {
                entry.add(map.getKey());
                value.add(map.getValue());
            }

            k = (String[]) entry.toArray();
            v = value.toArray();

            //TODO: Generate data in mySQL database using "INSERT INTO"
        } catch(SQLException | ClassNotFoundException e) {
            throw e;
        } finally {

        }
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

    public Object getKey(String path) throws NodeNotFoundException {
        if(settings.get(path) == null) {
            throw new NodeNotFoundException();
        }
        return settings.get(path);
    }

    public long getMoney() {
        if(settings.get("player.money") == null) {
            return 0;
        }

        return (long) settings.get("player.money");
    }
}

