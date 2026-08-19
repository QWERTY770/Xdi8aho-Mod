package top.xdi8.mod.firefly8.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

public class FireflyParticle extends SingleQuadParticle {
    public FireflyParticle(ClientLevel level, double x, double y, double z, double xs, double ys, double zs, SpriteSet sprites) {
        super(level, x, y, z, xs, ys, zs, sprites.get(level.getRandom()));
        this.setLifetime(80 + random.nextInt(16));
    }

    @Override
    public @NotNull SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    @Override
    public void tick() {
        if (this.age++ <= 20) {
            this.setAlpha(this.age * 0.05F);
        } else if (this.age >= this.getLifetime() - 20) {
            this.alpha -= 0.05F;
        }
        if (this.age >= this.getLifetime() && this.alpha <= 0) {
            this.remove();
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet sprites) {
            this.sprite = sprites;
        }

        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, @NotNull RandomSource random) {
            FireflyParticle particle = new FireflyParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, sprite);
            particle.setAlpha(1.0F);
            return particle;
        }
    }
}
