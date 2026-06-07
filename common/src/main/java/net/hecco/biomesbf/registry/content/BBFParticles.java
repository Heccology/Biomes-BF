package net.hecco.biomesbf.registry.content;

import net.hecco.bountifulfares.BountifulFares;
import net.hecco.nexuslib.platform.NLServices;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Supplier;

public class BBFParticles {
    public static final Supplier<SimpleParticleType> SPRUCE_PETAL = registerParticle("spruce_petal");

    private static Supplier<SimpleParticleType> registerParticle(String name) {
        return NLServices.REGISTRY.registerParticleType(BountifulFares.MOD_ID, name);
    }

    public static void register() {
    }
}
