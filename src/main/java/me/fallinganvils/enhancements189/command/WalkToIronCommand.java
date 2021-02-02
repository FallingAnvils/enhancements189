package me.fallinganvils.enhancements189.command;

import me.fallinganvils.enhancements189.handler.WalkToIronHandler;
import me.fallinganvils.enhancements189.util.VectorHelper;
import me.fallinganvils.enhancements189.EnhancementsMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Items;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.util.vector.Vector2f;

import java.util.List;


public class WalkToIronCommand {

    public String getName() {
        return "/findgen";
    }

    public boolean execute(String cmd) {
        WalkToIronHandler handler = EnhancementsMod.FINDGEN_HANDLER;
        MinecraftClient client = MinecraftClient.getInstance();

        if(cmd.contains("stop")) {
            handler.pressersManager.stop();
            return false;
        } else if(cmd.contains("disable")) {
            client.player.addMessage(new TranslatableText("commands.findgen.temporarily_disabled"));
            handler.setTempDisabled(true);
            return true;
        }
        
        Box box = client.player.getBoundingBox().expand(handler.SEARCH_RANGE, handler.SEARCH_RANGE, handler.SEARCH_RANGE);
        List<ItemEntity> ironEntities = client.world.getEntitiesInBox(ItemEntity.class, box, item -> item.getItemStack().getItem() == Items.IRON_INGOT);

        if(ironEntities.size() < 1) {
            client.player.addMessage(new TranslatableText("commands.findgen.nothing_in_range"));
            return false;
        } else {
            ItemEntity ironEntity = ironEntities.get(0);
            
            Vec3d ironRelativeToPlayer = ironEntity.getPos().subtract(client.player.getPos());
            Vector2f rIron2pos = new Vector2f((float)ironRelativeToPlayer.x, (float)ironRelativeToPlayer.z);

            Vector2f forwardsVector = VectorHelper.getForwardsVector(client.player);
            Vector2f backwardsVector = VectorHelper.getBackwardsVector(client.player);
            Vector2f leftVector = VectorHelper.getLeftVector(client.player);
            Vector2f rightVector = VectorHelper.getRightVector(client.player);

            float forwardAngle = 90 - (float) Math.toDegrees(Vector2f.angle(forwardsVector, rIron2pos));
            float backAngle = 90 - (float)Math.toDegrees(Vector2f.angle(backwardsVector, rIron2pos));
            float leftAngle = 90 - (float)Math.toDegrees(Vector2f.angle(leftVector, rIron2pos));
            float rightAngle = 90 - (float)Math.toDegrees(Vector2f.angle(rightVector, rIron2pos));
            // negative on these ^ means you don't go in that direction
            
            handler.resetPressers();
            
            if(forwardAngle >= 0) {
                float percentForward = forwardAngle / 90.0F;
                System.out.println("Percent forward: " + percentForward);
                handler.pressersManager.setDelays(WalkToIronHandler.PresserIndex.FORWARDS, Math.round((1 - percentForward) * 10), Math.round(percentForward * 10));
                handler.pressersManager.setShouldRun(WalkToIronHandler.PresserIndex.FORWARDS, true);
            }
            if(backAngle >= 0) {
                float percentBack = backAngle / 90.0F;
                System.out.println("Percent back: " + percentBack);
                handler.pressersManager.setDelays(WalkToIronHandler.PresserIndex.BACKWARDS, Math.round((1 - percentBack) * 10), Math.round(percentBack * 10));
                handler.pressersManager.setShouldRun(WalkToIronHandler.PresserIndex.BACKWARDS, true);
            }
            if(leftAngle >= 0) {
                float percentLeft = leftAngle / 90.0F;
                System.out.println("Percent left: " + percentLeft);
                handler.pressersManager.setDelays(WalkToIronHandler.PresserIndex.LEFT, Math.round((1 - percentLeft) * 10), Math.round(percentLeft * 10));
                handler.pressersManager.setShouldRun(WalkToIronHandler.PresserIndex.LEFT, true);
            }
            if(rightAngle >= 0) {
                float percentRight = rightAngle / 90.0F;
                System.out.println("Percent right: " + percentRight);
                handler.pressersManager.setDelays(WalkToIronHandler.PresserIndex.RIGHT, Math.round((1 - percentRight) * 10), Math.round(percentRight * 10));
                handler.pressersManager.setShouldRun(WalkToIronHandler.PresserIndex.RIGHT, true);
            }
            
            if(!handler.pressersManager.start()) {
                // if the user is already holding a movement key
                // don't return false so the auto thing doesn't try again
                EnhancementsMod.FINDGEN_HANDLER.pressersManager.stop();
                client.player.addMessage(new TranslatableText("commands.findgen.stop.reason.player_interaction"));
            } else {
                client.player.addMessage(new TranslatableText("commands.findgen.found", ironEntity.getPos().x, ironEntity.getPos().y, ironEntity.getPos().z));
            }
        }
        return true;
    }

}
