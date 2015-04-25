package com.frogocomics.api.account;

import com.google.common.annotations.Beta;
import com.google.inject.Inject;

import org.slf4j.Logger;
import org.spongepowered.api.entity.player.Player;

import java.util.HashMap;
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
     * @param settings A {@link Map} of the player settings
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

    public Account() {}

    /**
     * Gets an instance of {@link Logger}.
     *
     * @return {@link Logger}
     */
    private Logger getLogger() {
        return logger;
    }

    private String username;
    private String password;

    /**
     * Sets the specific Logger for the plugin.
     *
     * @param logger An instance of {@link Logger}
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
        try {
            return new AccountExporter(settings, accountJson, playerUniqueId, player);
        } catch(AccountException e) {
            return null;
        }
    }

    /**
     * Get the node manager.
     *
     * @param node The node that will be worked on
     * @return {@link Node}
     */
    public Node getNodeManager(String node) {
        return new Node(node, settings);
    }

    /**
     * Sets the username of a Ftp Server.
     *
     * @param username Username of a Ftp Server
     * @return null
     * @Deprecated FTP is not likely used
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
     * @param logger An instance of {@link Logger}
     * @param map A subclass of {@link Map} that uses {@link String} and {@link Object} for the value
     */
    @Beta
    private void sendValue(String value, Logger logger, Map<String, Object> map) {
        logger.info("Value \"" + value + "\":" + map.get(value));
    }
}

class Node {

    private String node;
    private HashMap<String, Object> settings;

    public Node(String node, Map<String, Object> settings){
        this.node = node;
        this.settings = (HashMap<String, Object>) settings;
    }

    public Object getValue() throws NodeNotFoundException {
        if(settings.get(node) == null) {
            throw new NodeNotFoundException();
        } else {
            return settings.get(node);
        }
    }
}
