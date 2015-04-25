package com.frogocomics.api.account;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.spongepowered.api.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The AccountBuilder class hides the code used to create an
 * player profile. It should not be accessed directly but rather
 * through {@link Accounts#builder(Player)}.
 *
 * @see Accounts
 */
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

    /**
     * Set the Unique id (UUID) of the player
     * in a {@link String}.
     *
     * @param uuid The Unique id (UUID) of a player
     * @return null
     */
    public AccountBuilder setUniqueId(UUID uuid) {
        settings.put("player.uuid", uuid);
        return null;
    }

    /**
     * Set the amount of money the player has.
     *
     * @param amount The amount of money that is to be given to the player
     * @return null
     */
    public AccountBuilder setMoney(long amount) {
        if(amount < 0) {
            settings.put("player.money", getDefaultAmount());
            return null;
        }

        settings.put("player.money", amount);
        setMoney = true;
        return null;
    }

    /**
     * If the amount of money the player should have is not set,
     * Use this instead. This will set a default amount, which is
     * to be used as a backup.
     *
     * @param amount The default amount that servers as a backup
     * @return null
     */
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

    /**
     * Create the account, and cast it into a {@link Account},
     * which could then be cast as a {@link AccountExporter}
     * through the {@link Account#getAccountExporter()} method.
     *
     * @return An instance of {@link Account}
     * @throws AccountException
     */
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
