package tfar.zomboabilities.datagen;

import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.codehaus.plexus.util.StringUtils;
import tfar.zomboabilities.ZomboAbilities;

import java.util.function.Supplier;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, ZomboAbilities.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        ZomboAbilities.getKnownMobEffects().forEach(this::addDefaultMobEffect);
        ZomboAbilities.getKnownBlocks().forEach(block -> addDefaultBlock(() -> block));
        ZomboAbilities.getKnownItems().forEach(item -> addDefaultItem(() -> item));

    }

    protected void addPotion(Holder<Potion> potion, String name) {
        ItemStack stack = PotionContents.createItemStack(Items.POTION,potion);
        add(stack.getDescriptionId(), name);
        
        ItemStack splashStack = PotionContents.createItemStack(Items.SPLASH_POTION,potion);
        add(splashStack.getDescriptionId(), "Splash "+name);

        ItemStack lingeringStack = PotionContents.createItemStack(Items.LINGERING_POTION,potion);
        add(lingeringStack.getDescriptionId(), "Lingering "+name);
    }

    protected void addDefaultMobEffect(Holder<MobEffect> holder) {
        addEffect(holder::value,getNameFromEffect(holder.value()));
    }

    protected void addDefaultMobEffect(MobEffect effect) {
        addEffect(() -> effect,getNameFromEffect(effect));
    }

    protected void addDefaultItem(Supplier<? extends Item> supplier) {
        addItem(supplier,getNameFromItem(supplier.get()));
    }

    protected void addDefaultBlock(Supplier<? extends Block> supplier) {
        addBlock(supplier,getNameFromBlock(supplier.get()));
    }

    protected void addDefaultEntityType(Supplier<EntityType<?>> supplier) {
        addEntityType(supplier,getNameFromEntity(supplier.get()));
    }

    public static String getNameFromItem(Item item) {
        return StringUtils.capitaliseAllWords(item.getDescriptionId().split("\\.")[2].replace("_", " "));
    }

    public static String getNameFromBlock(Block block) {
        return StringUtils.capitaliseAllWords(block.getDescriptionId().split("\\.")[2].replace("_", " "));
    }

    public static String getNameFromEffect(MobEffect effect) {
        return StringUtils.capitaliseAllWords(effect.getDescriptionId().split("\\.")[2].replace("_", " "));
    }

    public static String getNameFromEntity(EntityType<?> entity) {
        return StringUtils.capitaliseAllWords(entity.getDescriptionId().split("\\.")[2].replace("_", " "));
    }

    protected void addTextComponent(MutableComponent component, String text) {
        ComponentContents contents = component.getContents();
        if (contents instanceof TranslatableContents translatableContents) {
            add(translatableContents.getKey(),text);
        } else {
            throw new UnsupportedOperationException(component +" is not translatable");
        }
    }

}
