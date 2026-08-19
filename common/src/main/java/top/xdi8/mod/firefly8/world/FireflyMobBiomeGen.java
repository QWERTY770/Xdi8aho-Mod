package top.xdi8.mod.firefly8.world;

import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.hooks.level.biome.SpawnProperties;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;

public class FireflyMobBiomeGen {
    public static void registerBiomeModifications() {
        BiomeModifications.addProperties(((biomeContext, mutable) -> {
            if (biomeContext.hasTag(BiomeTags.HAS_VILLAGE_PLAINS)){
                addFireflySpawn(mutable.getSpawnProperties(), 5, 1, 3);
            }
            else if (biomeContext.hasTag(BiomeTags.IS_FOREST)){
                addFireflySpawn(mutable.getSpawnProperties(), 10, 2, 4);
            }
            else if (biomeContext.hasTag(BiomeTags.IS_JUNGLE)){
                addFireflySpawn(mutable.getSpawnProperties(), 20, 2, 4);
            }
            else if (biomeContext.hasTag(BiomeTags.HAS_SWAMP_HUT)){
                addFireflySpawn(mutable.getSpawnProperties(), 7, 2, 4);
            }
        }));
    }

    private static void addFireflySpawn(SpawnProperties.Mutable spawnProperties, int weight, int minCount, int maxCount) {
        spawnProperties.addSpawn(MobCategory.CREATURE,
                new MobSpawnSettings.SpawnerData(FireflyEntityTypes.FIREFLY.get(), minCount, maxCount), weight);
    }
}
