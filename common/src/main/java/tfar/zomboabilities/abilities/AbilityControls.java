package tfar.zomboabilities.abilities;

import java.util.Objects;

public class AbilityControls {

    public boolean holding_primary;
    public boolean holding_secondary;
    public boolean holding_tertiary;
    public boolean holding_quaternary;

    public void updateControls(boolean primary,boolean secondary,boolean tertiary,boolean quaternary) {
        holding_primary = primary;
        holding_secondary = secondary;
        holding_tertiary = tertiary;
        holding_quaternary = quaternary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbilityControls that = (AbilityControls) o;
        return holding_primary == that.holding_primary && holding_secondary == that.holding_secondary && holding_tertiary == that.holding_tertiary && holding_quaternary == that.holding_quaternary;
    }

    @Override
    public String toString() {
        return "AbilityControls{" +
                "holding_primary=" + holding_primary +
                ", holding_secondary=" + holding_secondary +
                ", holding_tertiary=" + holding_tertiary +
                ", holding_quaternary=" + holding_quaternary +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(holding_primary, holding_secondary, holding_tertiary, holding_quaternary);
    }
}
