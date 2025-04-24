package cn.infinitumstudios.infinitumEconomy.foundation.types;

import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class Account{
    private final UUID accountUUID;
    private final String nickname;
    private int credit;

    public Account(OfflinePlayer player){
        this(player.getUniqueId(), player.getName(), 100);
    }

    public Account(UUID accountUUID, String nickname, int credit) {
        this.accountUUID = accountUUID;
        this.nickname = nickname;
        this.credit = credit;
    }

    public String getNickname() {
        return nickname;
    }

    public UUID getAccountID () {
        return accountUUID;
    }

    public int getCredit () {
        return credit;
    }

    public void setCredit (int credit) {
        this.credit = credit;
    }
}
