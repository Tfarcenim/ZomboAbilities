package tfar.zomboabilities.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class Serializer2 extends ShapedRecipe.Serializer {

    public static final MapCodec<GeniusRecipe> CODEC = RecordCodecBuilder.mapCodec(
                            instance -> instance.group(ShapedRecipe.Serializer.CODEC.forGetter(geniusRecipe -> geniusRecipe))
                                    .apply(instance, GeniusRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, GeniusRecipe> STREAM_CODEC = StreamCodec.of(
            ShapedRecipe.Serializer.STREAM_CODEC::encode, pBuffer -> new GeniusRecipe(ShapedRecipe.Serializer.STREAM_CODEC.decode(pBuffer)));


    @Override
    public MapCodec<ShapedRecipe> codec() {
        return (MapCodec<ShapedRecipe>)(Object) CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, ShapedRecipe> streamCodec() {
        return  (StreamCodec<RegistryFriendlyByteBuf, ShapedRecipe>) (Object) STREAM_CODEC;
    }
}