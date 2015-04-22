package com.frogocomics.api.account;

import com.google.inject.Inject;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.spongepowered.api.entity.player.Player;

import java.io.*;
import java.util.Map;

public class Account {

    @Inject private Logger logger;

    private Player player;
    private String accountJson;
    private String playerUniqueId;

    private Map<String, Object> settings;

    /**
     * Constructor for the <code>Account</code> class.
     *
     * @param player The player whose account is being made
     * @param accountJson The json String created for the account
     * @param playerUniqueId The UniqueId (UUID) of the player
     * @param settings A {@link java.util.Map} of the player settings
     *
     * @see java.util.UUID
     */
    public Account(Player player, String accountJson, String playerUniqueId, Map<String, Object> settings) {
        this.player = player;
        this.accountJson = accountJson;
        this.settings = settings;

        getLogger().info("\u2015\u2015\u2015\u2015");
        getLogger().info("Account created/modified:");

        sendValue("player.money", getLogger(), settings);
        sendValue("player.uuid", getLogger(), settings);

        getLogger().info("\u2015\u2015\u2015\u2015");
    }

    /**
     * Gets an instance of {@link org.slf4j.Logger}.
     *
     * @return {@link org.slf4j.Logger}
     */
    private Logger getLogger() {
        return logger;
    }

    private RankBuilder builder;

    private String username;
    private String password;

    /**
     * Sets the specific Logger for the plugin.
     *
     * @param logger An instance of {@link org.slf4j.Logger}
     * @return null
     */
    public Account setLogger(Logger logger) {
        this.logger = logger;

        return null;
    }

    /**
     * Get an instance of {@link com.frogocomics.api.account.AccountExporter}.
     *
     * @return An instance of {@link com.frogocomics.api.account.AccountExporter}
     */
    public AccountExporter getAccountExporter() {
        return new AccountExporter(settings, accountJson, playerUniqueId, player);
    }

    /**
     * Write player information to a Ftp Server using JSON.
     *
     * @param ip The ip of the server
     * @param port The port number of the server
     * @return null
     * @throws IOException If the program is unable to connect to a server
     */
    @Deprecated
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

    /**
     * Sets the username of a Ftp Server.
     *
     * @param username Username of a Ftp Server
     * @return null
     */
    @Deprecated
    public Account setUsername(String username) {
        this.username = username;

        return null;
    }

    /**
     * Sets the password of a Ftp Server.
     *
     * @param password Password of a Ftp Server
     * @return null
     */
    @Deprecated
    public Account setPassword(String password) {
        this.password = password;

        return null;
    }

    /**
     * Gets the username of a Ftp Server.
     *
     * @return The username of a Ftp Server
     */
    @Deprecated
    public String getUsername() {
        if(username != null) {
            return username;
        } else {
            return null;
        }
    }

    /**
     * Gets the password of a Ftp Server.
     *
     * @return The password of a Ftp Server
     */
    @Deprecated
    public String getPassword() {
        if(password != null) {
            return password;
        }
        else {
            return null;
        }
    }

    /**
     * Sends a value to be logged to the server console.
     *
     * @param value The value
     * @param logger An instance of {@link org.slf4j.Logger}
     * @param map A subclass of {@link java.util.Map} that uses {@link java.lang.String} and {@link java.lang.Object} for the value
     */
    private void sendValue(String value, Logger logger, Map<String, Object> map) {
        logger.info("Value \"" + value + "\":" + map.get(value));
    }
}
