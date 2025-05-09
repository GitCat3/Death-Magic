package me.give_me_moneyz.deathmagic;

import com.mojang.logging.LogUtils;
import me.give_me_moneyz.deathmagic.common.network.MyNetwork;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DeathMagic.MODID)
public class DeathMagic
{
    public static final String MODID = "deathmagic";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DeathMagic(IEventBus modEventBus)
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        MyNetwork.register();
    }
}