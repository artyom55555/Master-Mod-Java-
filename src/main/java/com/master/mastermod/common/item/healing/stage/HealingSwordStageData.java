package com.master.mastermod.common.item.healing.stage;

import com.master.mastermod.common.item.healing.config.HealingSwordConfig;
import com.master.mastermod.common.item.healing.config.HealingSwordConfig.StageSettings;

public final class HealingSwordStageData {

    private HealingSwordStageData() {
    }

    public static StageSettings settings(HealingSwordStages.Stage stage) {
        switch (stage) {
        case STAGE_I:
            return HealingSwordConfig.STAGE_I;
        case STAGE_II:
            return HealingSwordConfig.STAGE_II;
        case STAGE_III:
            return HealingSwordConfig.STAGE_III;
        case STAGE_IV:
            return HealingSwordConfig.STAGE_IV;
        case STAGE_V:
        default:
            return HealingSwordConfig.STAGE_V;
        }
    }
}
