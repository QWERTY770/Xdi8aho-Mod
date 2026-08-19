package top.xdi8.mod.firefly8.item.symbol;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.block.symbol.SymbolStoneBlock;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;
import top.xdi8.mod.firefly8.core.letters.LettersUtil;
import top.xdi8.mod.firefly8.item.FireflyItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class SymbolStoneBlockItem extends BlockItem implements KeyedLetter.Provider {
    private final KeyedLetter letter;

    public SymbolStoneBlockItem(KeyedLetter letter, Properties properties) {
        super(SymbolStoneBlock.fromLetter(letter), properties);
        this.letter = letter;
    }

    @Override
    public void onDestroyed(@NotNull ItemEntity pItemEntity) {
        ItemUtils.onContainerDestroyed(pItemEntity, Stream.of(
                new ItemStack(FireflyItems.DARK_SYMBOL_STONE.get(), pItemEntity.getItem().getCount())).toList().stream());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context,
                                @NotNull TooltipDisplay display, @NotNull Consumer<Component> tooltip,
                                @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);
        if (letter.isNull()) return;
        List<String> names = new ArrayList<>();
        if (letter.hasUppercase()) names.add(Character.toString(letter.uppercase()));
        if (letter.hasMiddleCase()) names.add(Character.toString(letter.middleCase()));
        if (letter.hasLowercase()) names.add(Character.toString(letter.lowercase()));
        String text = String.join(" ", names);
        if (!text.isBlank()) {
            tooltip.accept(Component.translatable("block.firefly8.symbol_stone.letter", text));
        }
    }

    @NotNull
    @Override
    public KeyedLetter letter() {
        return letter;
    }

    @ApiStatus.Internal
    public static void registerAll(Function3<String, Function<Item.Properties, Item>, Item.Properties, RegistrySupplier<Item>> registry) {
        Item.Properties properties1 = new Properties().rarity(Rarity.UNCOMMON)
                .overrideDescription("block.firefly8.symbol_stone")
                .arch$tab(FireflyItems.FIREFLY8_TAB_SUPPLIER);
        RegistrySupplier<Item> emptyStone = registry.apply("symbol_stone", (properties) -> {
            var item = new SymbolStoneBlockItem(KeyedLetter.empty(), properties);
            LETTER_TO_ITEM.put(KeyedLetter.empty(), item);
            return item;
        }, properties1);
        emptyStone.listen(item1 -> Item.BY_BLOCK.put(SymbolStoneBlock.fromLetter(KeyedLetter.empty()), item1));
        LettersUtil.forEach((key, letter) -> {
            if (letter.isNull()) return;
            Function<Item.Properties, Item> sup = (properties) -> {
                var item = new SymbolStoneBlockItem(letter, properties);
                LETTER_TO_ITEM.put(letter, item);
                return item;
            };
            RegistrySupplier<Item> symbolStone = registry.apply(SymbolStoneBlock.getBlockId(key), sup, properties1);
            symbolStone.listen(item1 -> Item.BY_BLOCK.put(SymbolStoneBlock.fromLetter(letter), item1));
        });
    }

    static final Map<KeyedLetter, SymbolStoneBlockItem> LETTER_TO_ITEM = new HashMap<>();
    public static SymbolStoneBlockItem fromLetter(@Nullable KeyedLetter letter) {
        final SymbolStoneBlockItem item = LETTER_TO_ITEM.get(letter);
        if (item == null) return LETTER_TO_ITEM.get(KeyedLetter.empty());
        return item;
    }

    @FunctionalInterface
    public interface Function3<T1, T2, T3, R>{
        R apply(T1 t1, T2 t2, T3 t3);
    }
}
