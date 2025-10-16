package com.master.mastermod.common.item.healing.config;

public final class HealingSwordConfig {

    public static final StageSettings STAGE_I = StageSettings.builder()
            .stage(1)
            .vampirism(0.10f)
            .maxEnergy(20)
            .hitEnergyGain(1)
            .hitEnergyInterval(4)
            .killEnergyGain(1)
            .build();

    public static final StageSettings STAGE_II = StageSettings.builder()
            .stage(2)
            .vampirism(0.20f)
            .maxEnergy(30)
            .hitEnergyGain(1)
            .hitEnergyInterval(3)
            .auraRadius(2.0d)
            .auraDurationTicks(5 * 20)
            .build();

    public static final StageSettings STAGE_III = StageSettings.builder()
            .stage(3)
            .vampirism(0.30f)
            .maxEnergy(50)
            .hitEnergyGain(2)
            .killEnergyGain(6)
            .activeAuraRadius(5.0d)
            .activeAuraDurationTicks(8 * 20)
            .activeAuraEnergyCost(6)
            .build();

    public static final StageSettings STAGE_IV = StageSettings.builder()
            .stage(4)
            .vampirism(0.40f)
            .maxEnergy(70)
            .hitEnergyGain(3)
            .killEnergyGain(8)
            .cleanseEnergyCost(10)
            .cleanseHealthCost(2.0f)
            .cleanseCooldownTicks(20 * 12)
            .immunityDurationTicks(20 * 10)
            .build();

    public static final StageSettings STAGE_V = StageSettings.builder()
            .stage(5)
            .vampirism(0.0f)
            .maxEnergy(100)
            .hitEnergyGain(4)
            .killEnergyGain(10)
            .soulBurstEnergyCost(18)
            .soulBurstRadius(8.0d)
            .soulBurstBaseDamage(6.0f)
            .soulBurstRegenDuration(20 * 8)
            .overloadChance(0.10f)
            .overloadDamage(8.0f)
            .overloadHeal(10.0f)
            .build();

    private HealingSwordConfig() {
    }

    public static final class StageSettings {
        private final int stage;
        private final float vampirism;
        private final int maxEnergy;
        private final int hitEnergyGain;
        private final int hitEnergyInterval;
        private final int killEnergyGain;
        private final double auraRadius;
        private final int auraDurationTicks;
        private final double activeAuraRadius;
        private final int activeAuraDurationTicks;
        private final int activeAuraEnergyCost;
        private final int cleanseEnergyCost;
        private final float cleanseHealthCost;
        private final int cleanseCooldownTicks;
        private final int immunityDurationTicks;
        private final int soulBurstEnergyCost;
        private final double soulBurstRadius;
        private final float soulBurstBaseDamage;
        private final int soulBurstRegenDuration;
        private final float overloadChance;
        private final float overloadDamage;
        private final float overloadHeal;

        private StageSettings(Builder builder) {
            this.stage = builder.stage;
            this.vampirism = builder.vampirism;
            this.maxEnergy = builder.maxEnergy;
            this.hitEnergyGain = builder.hitEnergyGain;
            this.hitEnergyInterval = builder.hitEnergyInterval;
            this.killEnergyGain = builder.killEnergyGain;
            this.auraRadius = builder.auraRadius;
            this.auraDurationTicks = builder.auraDurationTicks;
            this.activeAuraRadius = builder.activeAuraRadius;
            this.activeAuraDurationTicks = builder.activeAuraDurationTicks;
            this.activeAuraEnergyCost = builder.activeAuraEnergyCost;
            this.cleanseEnergyCost = builder.cleanseEnergyCost;
            this.cleanseHealthCost = builder.cleanseHealthCost;
            this.cleanseCooldownTicks = builder.cleanseCooldownTicks;
            this.immunityDurationTicks = builder.immunityDurationTicks;
            this.soulBurstEnergyCost = builder.soulBurstEnergyCost;
            this.soulBurstRadius = builder.soulBurstRadius;
            this.soulBurstBaseDamage = builder.soulBurstBaseDamage;
            this.soulBurstRegenDuration = builder.soulBurstRegenDuration;
            this.overloadChance = builder.overloadChance;
            this.overloadDamage = builder.overloadDamage;
            this.overloadHeal = builder.overloadHeal;
        }

        public int getStage() {
            return this.stage;
        }

        public float getVampirism() {
            return this.vampirism;
        }

        public int getMaxEnergy() {
            return this.maxEnergy;
        }

        public int getHitEnergyGain() {
            return this.hitEnergyGain;
        }

        public int getHitEnergyInterval() {
            return this.hitEnergyInterval;
        }

        public int getKillEnergyGain() {
            return this.killEnergyGain;
        }

        public double getAuraRadius() {
            return this.auraRadius;
        }

        public int getAuraDurationTicks() {
            return this.auraDurationTicks;
        }

        public double getActiveAuraRadius() {
            return this.activeAuraRadius;
        }

        public int getActiveAuraDurationTicks() {
            return this.activeAuraDurationTicks;
        }

        public int getActiveAuraEnergyCost() {
            return this.activeAuraEnergyCost;
        }

        public int getCleanseEnergyCost() {
            return this.cleanseEnergyCost;
        }

        public float getCleanseHealthCost() {
            return this.cleanseHealthCost;
        }

        public int getCleanseCooldownTicks() {
            return this.cleanseCooldownTicks;
        }

        public int getImmunityDurationTicks() {
            return this.immunityDurationTicks;
        }

        public int getSoulBurstEnergyCost() {
            return this.soulBurstEnergyCost;
        }

        public double getSoulBurstRadius() {
            return this.soulBurstRadius;
        }

        public float getSoulBurstBaseDamage() {
            return this.soulBurstBaseDamage;
        }

        public int getSoulBurstRegenDuration() {
            return this.soulBurstRegenDuration;
        }

        public float getOverloadChance() {
            return this.overloadChance;
        }

        public float getOverloadDamage() {
            return this.overloadDamage;
        }

        public float getOverloadHeal() {
            return this.overloadHeal;
        }

        public static Builder builder() {
            return new Builder();
        }

        public static final class Builder {
            private int stage = 1;
            private float vampirism = 0.0f;
            private int maxEnergy = 0;
            private int hitEnergyGain = 0;
            private int hitEnergyInterval = 1;
            private int killEnergyGain = 0;
            private double auraRadius = 0.0d;
            private int auraDurationTicks = 0;
            private double activeAuraRadius = 0.0d;
            private int activeAuraDurationTicks = 0;
            private int activeAuraEnergyCost = 0;
            private int cleanseEnergyCost = 0;
            private float cleanseHealthCost = 0.0f;
            private int cleanseCooldownTicks = 0;
            private int immunityDurationTicks = 0;
            private int soulBurstEnergyCost = 0;
            private double soulBurstRadius = 0.0d;
            private float soulBurstBaseDamage = 0.0f;
            private int soulBurstRegenDuration = 0;
            private float overloadChance = 0.0f;
            private float overloadDamage = 0.0f;
            private float overloadHeal = 0.0f;

            private Builder() {
            }

            public Builder stage(int value) {
                this.stage = value;
                return this;
            }

            public Builder vampirism(float value) {
                this.vampirism = value;
                return this;
            }

            public Builder maxEnergy(int value) {
                this.maxEnergy = value;
                return this;
            }

            public Builder hitEnergyGain(int value) {
                this.hitEnergyGain = value;
                return this;
            }

            public Builder hitEnergyInterval(int value) {
                this.hitEnergyInterval = value;
                return this;
            }

            public Builder killEnergyGain(int value) {
                this.killEnergyGain = value;
                return this;
            }

            public Builder auraRadius(double value) {
                this.auraRadius = value;
                return this;
            }

            public Builder auraDurationTicks(int value) {
                this.auraDurationTicks = value;
                return this;
            }

            public Builder activeAuraRadius(double value) {
                this.activeAuraRadius = value;
                return this;
            }

            public Builder activeAuraDurationTicks(int value) {
                this.activeAuraDurationTicks = value;
                return this;
            }

            public Builder activeAuraEnergyCost(int value) {
                this.activeAuraEnergyCost = value;
                return this;
            }

            public Builder cleanseEnergyCost(int value) {
                this.cleanseEnergyCost = value;
                return this;
            }

            public Builder cleanseHealthCost(float value) {
                this.cleanseHealthCost = value;
                return this;
            }

            public Builder cleanseCooldownTicks(int value) {
                this.cleanseCooldownTicks = value;
                return this;
            }

            public Builder immunityDurationTicks(int value) {
                this.immunityDurationTicks = value;
                return this;
            }

            public Builder soulBurstEnergyCost(int value) {
                this.soulBurstEnergyCost = value;
                return this;
            }

            public Builder soulBurstRadius(double value) {
                this.soulBurstRadius = value;
                return this;
            }

            public Builder soulBurstBaseDamage(float value) {
                this.soulBurstBaseDamage = value;
                return this;
            }

            public Builder soulBurstRegenDuration(int value) {
                this.soulBurstRegenDuration = value;
                return this;
            }

            public Builder overloadChance(float value) {
                this.overloadChance = value;
                return this;
            }

            public Builder overloadDamage(float value) {
                this.overloadDamage = value;
                return this;
            }

            public Builder overloadHeal(float value) {
                this.overloadHeal = value;
                return this;
            }

            public StageSettings build() {
                return new StageSettings(this);
            }
        }
    }
}
