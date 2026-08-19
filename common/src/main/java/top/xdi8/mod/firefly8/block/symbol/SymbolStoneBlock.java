package top.xdi8.mod.firefly8.block.symbol;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.Firefly8;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;
import top.xdi8.mod.firefly8.core.letters.LettersUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class SymbolStoneBlock extends Block implements KeyedLetter.Provider {
    private final KeyedLetter letter;

    public SymbolStoneBlock(BlockBehaviour.Properties properties, KeyedLetter letter) {
        super(properties);
        this.letter = letter;
    }

    @NotNull
    @Override
    public KeyedLetter letter() {
        return letter;
    }

    @ApiStatus.Internal
    public static void registerAll(Consumer3<String, Function<BlockBehaviour.Properties, Block>, BlockBehaviour.Properties> registry) {
        BlockBehaviour.Properties properties1 = Properties.of()
                .overrideDescription("block.firefly8.symbol_stone")
                .mapColor(MapColor.COLOR_LIGHT_GRAY)
                .requiresCorrectToolForDrops()
                .strength(1.5F, 8.0F);
        registry.accept("symbol_stone", (properties) -> {
            var block = new SymbolStoneBlock(properties, KeyedLetter.empty());
            LETTER_TO_BLOCK.put(KeyedLetter.empty(), block);
            return block;
        }, properties1);
        LettersUtil.forEach((key, letter) -> {
            if (letter.isNull()) return;
            Function<BlockBehaviour.Properties, Block> sup = (properties) -> {
                var block = new SymbolStoneBlock(properties, letter);
                LETTER_TO_BLOCK.put(letter, block);
                return block;
            };
            registry.accept(getBlockId(key), sup, properties1);
        });
    }

    static final Map<KeyedLetter, SymbolStoneBlock> LETTER_TO_BLOCK = new HashMap<>();
    public static SymbolStoneBlock fromLetter(@Nullable KeyedLetter letter) {
        final SymbolStoneBlock block = LETTER_TO_BLOCK.get(letter);
        if (block == null) return LETTER_TO_BLOCK.get(KeyedLetter.empty());
        return block;
    }

    public static String getBlockId(Identifier key){
        if (Firefly8.MODID.equals(key.getNamespace())) return "symbol_stone_" + key.getPath();
        else return "symbol_stone_" + key.getNamespace() + "__" + key.getPath();
    }

    @FunctionalInterface
    public interface Consumer3<T1, T2, T3>{
        void accept(T1 t1, T2 t2, T3 t3);
    }
}
