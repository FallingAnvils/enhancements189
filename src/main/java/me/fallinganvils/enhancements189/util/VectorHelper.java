package me.fallinganvils.enhancements189.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.util.vector.Vector2f;

public class VectorHelper {

    public static Vector2f getForwardsVector(PlayerEntity player) {
        float yawComponentX = (float)-Math.sin(Math.toRadians(player.yaw));
        float yawComponentZ = (float)Math.cos(Math.toRadians(player.yaw));

        return new Vector2f(yawComponentX, yawComponentZ);
    }
    
    public static Vector2f getBackwardsVector(PlayerEntity player) {

        float yawComponentX = (float)Math.sin(Math.toRadians(player.yaw));
        float yawComponentZ = (float)-Math.cos(Math.toRadians(player.yaw));

        return new Vector2f(yawComponentX, yawComponentZ);
    }

    public static Vector2f getLeftVector(PlayerEntity player) {

        float yawComponentX = (float)Math.cos(Math.toRadians(player.yaw));
        float yawComponentZ = (float)Math.sin(Math.toRadians(player.yaw));

        return new Vector2f(yawComponentX, yawComponentZ);
    }

    public static Vector2f getRightVector(PlayerEntity player) {
        float yawComponentX = (float)-Math.cos(Math.toRadians(player.yaw));
        float yawComponentZ = (float)-Math.sin(Math.toRadians(player.yaw));

        return new Vector2f(yawComponentX, yawComponentZ);
    }

    public static Vector2f getHorizontalComponentsBetween(PlayerEntity player, Vec3d targetPos) {
        Vec3d playerPos = player.getPos();

        float distanceX = (float)(playerPos.x - targetPos.x);
        float distanceZ = (float)(playerPos.z - targetPos.z);

        return new Vector2f(distanceX, distanceZ);
    }
}
