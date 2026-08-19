package top.xdi8.mod.firefly8.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.object.boat.BoatModel;
import top.xdi8.mod.firefly8.Firefly8Client;
import top.xdi8.mod.firefly8.client.FireflyParticle;
import top.xdi8.mod.firefly8.particle.FireflyParticles;

public class Firefly8ClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelLayerRegistry.registerModelLayer(Firefly8Client.CEDAR_BOAT_LAYER, BoatModel::createBoatModel);
        ModelLayerRegistry.registerModelLayer(Firefly8Client.CEDAR_CHEST_BOAT_LAYER, BoatModel::createChestBoatModel);
        new Firefly8Client().run();
        ParticleProviderRegistry.getInstance().register(FireflyParticles.FIREFLY.get(), FireflyParticle.Provider::new);
    }
}
