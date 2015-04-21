package com.frogocomics.api.account;

import com.google.inject.Inject;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.player.Player;

import java.io.*;
import java.util.Map;

public class Account {

    @Inject
    private Game game;

    @Inject
    private Logger logger;

    private Player player;
    private String accountJson;
    private String playerUniqueId;

    private Map<String, Object> settings;

    public Account(Player player, String accountJson, String playerUniqueId, Map<String, Object> settings) {
        this.player = player;
        this.accountJson = accountJson;
        this.settings = settings;

        /*
        Expected Console Output:

        [INFO] Account created/modified:
        [INFO] ?????
        [INFO] Value "player.money": <value>
        [INFO] Value "player.uuid": ef62c0c6-67a6-47ea-b704-17d76bd2b4aa
        [INFO] ?????
         */

        getLogger().info(/*Heavy shit this is lol, but just to be safe.. :(*/"\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015"); //???????????????
        getLogger().info("Account created/modified:");
        sendValue("player.money", getLogger(), settings);
        sendValue("player.uuid", getLogger(), settings);
        getLogger().info("\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015\u2015"); //???????????????
    }

    private Logger getLogger() {
        return logger;
    }

    private RankBuilder builder;

    private String username;
    private String password;

    public Account setLogger(Logger logger) {
        this.logger = logger;
        return null;
    }

    public Account writeInformationToFtpServer(String ip, int port) throws IOException{
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(ip, port);
        ftpClient.login(getUsername(), getPassword());
        ftpClient.enterLocalPassiveMode();

        FileWriter out = new FileWriter("\\accounts\\" + playerUniqueId + ".json");

        char[] information = accountJson.toCharArray();
        out.write(accountJson);

        ftpClient.logout();
        ftpClient.disconnect();
        return null;
    }

    public Account setUsername(String username) {
        this.username = username;
        return null;
    }

    public Account setPassword(String password) {
        this.password = password;
        return null;
    }

    public String getUsername() {
        if(username != null) return username;
        else return null;
    }

    public String getPassword() {
        if(password != null) return password;
        else return null;
    }

    private void sendValue(String value, Logger logger, Map<String, Object> map) {
        logger.info("Value \"" + value + "\":" + map.get(value));
    }
}
