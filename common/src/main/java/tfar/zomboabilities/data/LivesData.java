package tfar.zomboabilities.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record LivesData(int lives) {

    public static final Codec<LivesData> CODEC = RecordCodecBuilder.create(
            livesDataInstance -> livesDataInstance.group(Codec.INT.fieldOf("lives").forGetter(LivesData::lives))
            .apply(livesDataInstance,LivesData::new));

    public LivesData withLives(int lives) {
        return lives != this.lives ? new LivesData(lives) : this;
    }

}
