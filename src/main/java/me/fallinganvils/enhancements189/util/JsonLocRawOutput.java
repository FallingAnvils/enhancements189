package me.fallinganvils.enhancements189.util;

import com.google.gson.annotations.SerializedName;

public class JsonLocRawOutput {
    @SerializedName("server")
    public String server;

    @SerializedName("gametype")
    public String gameType;

    @SerializedName("lobbyname")
    public String lobbyName;

    @SerializedName("mode")
    public String mode;

    @SerializedName("map")
    public String map;

    public boolean isLobby() {
        return lobbyName != null;
    }
}
