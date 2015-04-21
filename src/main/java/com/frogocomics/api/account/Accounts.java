package com.frogocomics.api.account;

import org.spongepowered.api.entity.player.Player;

public class Accounts {

    public static AccountBuilder builder(Player player) {
        return new AccountBuilder(player);
    }
}
