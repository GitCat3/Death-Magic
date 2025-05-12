package me.give_me_moneyz.deathmagic.client.events;

import me.give_me_moneyz.deathmagic.client.network.ClientDeadEntityTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TickEvent;
import net.minecraft.core.BlockPos;
import java.util.List;
import java.util.Random;

import static me.give_me_moneyz.deathmagic.DeathMagic.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientTick {
    private static int tickCounter = 0;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;

        tickCounter++;

        if (tickCounter >= 10) {
            List<BlockPos> deathPositions = ClientDeadEntityTracker.getDeathPositions();
            tickCounter = 0;

            double maxDistSqr = 64 * 64;
            BlockPos playerPos = mc.player.blockPosition();
            Random random = new Random();

            for (BlockPos blockPos : deathPositions) {

                Vec3 blockPosVec = blockPos.getCenter();
                double distSqr = playerPos.distToCenterSqr(blockPosVec);

                if (distSqr < maxDistSqr) {
                    int numParticles = 20;
                    for (int i = 0; i < numParticles; i++) {
                        double offsetX = random.nextGaussian() * 0.3;
                        double offsetY = random.nextGaussian() * 0.3;
                        double offsetZ = random.nextGaussian() * 0.3;

                        mc.level.addParticle(
                                ParticleTypes.SOUL_FIRE_FLAME,
                                blockPosVec.x + offsetX, blockPosVec.y + 0.1 + offsetY, blockPosVec.z + offsetZ,
                                0.0, 0.01, 0.0
                        );
                    }
                }
            }
        }
    }
}
