package com.master.mastermod.common.item.healing.stage;

import java.util.EnumMap;
import java.util.Map;

public final class HealingSwordStages {

    private static final Map<Stage, HealingSwordStageBehavior> BEHAVIORS = new EnumMap<>(Stage.class);

    static {
        register(Stage.STAGE_I, new StageOneBehavior());
        register(Stage.STAGE_II, new StageTwoBehavior());
        register(Stage.STAGE_III, new StageThreeBehavior());
        register(Stage.STAGE_IV, new StageFourBehavior());
        register(Stage.STAGE_V, new StageFiveBehavior());
    }

    private HealingSwordStages() {
    }

    private static void register(Stage stage, HealingSwordStageBehavior behavior) {
        BEHAVIORS.put(stage, behavior);
    }

    public static HealingSwordStageBehavior behavior(Stage stage) {
        return BEHAVIORS.getOrDefault(stage, BEHAVIORS.get(Stage.STAGE_I));
    }

    public static Stage fromId(int id) {
        for (Stage stage : Stage.values()) {
            if (stage.id == id) {
                return stage;
            }
        }
        return Stage.STAGE_I;
    }

    public enum Stage {
        STAGE_I(1),
        STAGE_II(2),
        STAGE_III(3),
        STAGE_IV(4),
        STAGE_V(5);

        private final int id;

        Stage(int id) {
            this.id = id;
        }

        public int id() {
            return this.id;
        }
    }
}
