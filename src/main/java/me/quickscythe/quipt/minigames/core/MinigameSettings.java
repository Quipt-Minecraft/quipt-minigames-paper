package me.quickscythe.quipt.minigames.core;

import me.quickscythe.quipt.minigames.core.annotations.MinigameStructure;

import java.util.Optional;

public record MinigameSettings(String name, Optional<String> description, Optional<String[]> rules, String[] arenas, int minPlayers, int maxPlayer, boolean canJoinInProgress, boolean canSpectate, boolean canRespawn, boolean canBreakBlocks, boolean canPlaceBlocks, boolean canDropItems, boolean canPickupItems, boolean canDamagePlayers, boolean canDamageMobs, boolean canDamageAnimals, boolean canDamageVehicles, boolean canDamageBlocks, boolean canUseItems, boolean canUseBlocks, boolean canUseVehicles, boolean canUseMobs, boolean canUseAnimals, boolean friendlyFire, boolean canTakeFallDamage) {

    public static Builder builder(String name){
        return new Builder(name);
    }

    public static MinigameSettings convertStructure(MinigameStructure structure){
        return builder(structure.name())
                .description(structure.description())
                .rules(structure.rules())
                .arenas(structure.arenas())
                .minPlayers(structure.minPlayers())
                .maxPlayers(structure.maxPlayers())
                .canJoinInProgress(structure.canJoinInProgress())
                .canSpectate(structure.canSpectate())
                .canRespawn(structure.canRespawn())
                .canBreakBlocks(structure.canBreakBlocks())
                .canPlaceBlocks(structure.canPlaceBlocks())
                .canDropItems(structure.canDropItems())
                .canPickupItems(structure.canPickupItems())
                .canDamagePlayers(structure.canDamagePlayers())
                .canDamageMobs(structure.canDamageMobs())
                .canDamageAnimals(structure.canDamageAnimals())
                .canDamageVehicles(structure.canDamageVehicles())
                .canDamageBlocks(structure.canDamageBlocks())
                .canUseItems(structure.canUseItems())
                .canUseBlocks(structure.canUseBlocks())
                .canUseVehicles(structure.canUseVehicles())
                .canUseMobs(structure.canUseMobs())
                .canUseAnimals(structure.canUseAnimals())
                .build();
    }



    public static class Builder{

        private final String name;
        private Optional<String> description = Optional.empty();
        private Optional<String[]> rules = Optional.empty();
        private String[] arenas;
        private int minPlayers = MinigameStructure.DEFAULT_MIN_PLAYERS;
        private int maxPlayer = MinigameStructure.DEFAULT_MAX_PLAYERS;
        private boolean canJoinInProgress = MinigameStructure.DEFAULT_CAN_JOIN_IN_PROGRESS;
        private boolean canSpectate = MinigameStructure.DEFAULT_CAN_SPECTATE;

        private boolean canRespawn = MinigameStructure.DEFAULT_CAN_RESPAWN;
        private boolean canBreakBlocks = MinigameStructure.DEFAULT_CAN_BREAK_BLOCKS;
        private boolean canPlaceBlocks = MinigameStructure.DEFAULT_CAN_PLACE_BLOCKS;
        private boolean canDropItems = MinigameStructure.DEFAULT_CAN_DROP_ITEMS;
        private boolean canPickupItems = MinigameStructure.DEFAULT_CAN_PICKUP_ITEMS;
        private boolean canDamagePlayers = MinigameStructure.DEFAULT_CAN_DAMAGE_PLAYERS;
        private boolean canDamageMobs = MinigameStructure.DEFAULT_CAN_DAMAGE_MOBS;
        private boolean canDamageAnimals = MinigameStructure.DEFAULT_CAN_DAMAGE_ANIMALS;
        private boolean canDamageVehicles = MinigameStructure.DEFAULT_CAN_DAMAGE_VEHICLES;
        private boolean canDamageBlocks = MinigameStructure.DEFAULT_CAN_DAMAGE_BLOCKS;
        private boolean canUseItems = MinigameStructure.DEFAULT_CAN_USE_ITEMS;
        private boolean canUseBlocks = MinigameStructure.DEFAULT_CAN_USE_BLOCKS;
        private boolean canUseVehicles = MinigameStructure.DEFAULT_CAN_USE_VEHICLES;
        private boolean canUseMobs = MinigameStructure.DEFAULT_CAN_USE_MOBS;
        private boolean canUseAnimals = MinigameStructure.DEFAULT_CAN_USE_ANIMALS;
        private boolean friendlyFire = MinigameStructure.DEFAULT_FRIENDLY_FIRE;
        private boolean canTakeFallDamage = MinigameStructure.DEFAULT_CAN_TAKE_FALL_DAMAGE;

        public Builder(String name){
            this.name = name;
        }

        public Builder description(String description){
            this.description = Optional.of(description);
            return this;
        }

        public Builder rules(String[] rules){
            this.rules = Optional.of(rules);
            return this;
        }

        public Builder arenas(String[] arenas){
            this.arenas = arenas;
            return this;
        }

        public Builder minPlayers(int minPlayers){
            this.minPlayers = minPlayers;
            return this;
        }

        public Builder maxPlayers(int maxPlayers){
            this.maxPlayer = maxPlayers;
            return this;
        }


        public Builder canJoinInProgress(boolean canJoinInProgress){
            this.canJoinInProgress = canJoinInProgress;
            return this;
        }

        public Builder canSpectate(boolean canSpectate){
            this.canSpectate = canSpectate;
            return this;
        }

        public Builder canRespawn(boolean canRespawn){
            this.canRespawn = canRespawn;
            return this;
        }

        public Builder canBreakBlocks(boolean canBreakBlocks){
            this.canBreakBlocks = canBreakBlocks;
            return this;
        }

        public Builder canPlaceBlocks(boolean canPlaceBlocks){
            this.canPlaceBlocks = canPlaceBlocks;
            return this;
        }

        public Builder canDropItems(boolean canDropItems){
            this.canDropItems = canDropItems;
            return this;
        }

        public Builder canPickupItems(boolean canPickupItems){
            this.canPickupItems = canPickupItems;
            return this;
        }

        public Builder canDamagePlayers(boolean canDamagePlayers){
            this.canDamagePlayers = canDamagePlayers;
            return this;
        }

        public Builder canDamageMobs(boolean canDamageMobs){
            this.canDamageMobs = canDamageMobs;
            return this;
        }

        public Builder canDamageAnimals(boolean canDamageAnimals){
            this.canDamageAnimals = canDamageAnimals;
            return this;
        }

        public Builder canDamageVehicles(boolean canDamageVehicles){
            this.canDamageVehicles = canDamageVehicles;
            return this;
        }

        public Builder canDamageBlocks(boolean canDamageBlocks){
            this.canDamageBlocks = canDamageBlocks;
            return this;
        }

        public Builder canUseItems(boolean canUseItems){
            this.canUseItems = canUseItems;
            return this;
        }

        public Builder canUseBlocks(boolean canUseBlocks){
            this.canUseBlocks = canUseBlocks;
            return this;
        }

        public Builder canUseVehicles(boolean canUseVehicles){
            this.canUseVehicles = canUseVehicles;
            return this;
        }

        public Builder canUseMobs(boolean canUseMobs){
            this.canUseMobs = canUseMobs;
            return this;
        }

        public Builder canUseAnimals(boolean canUseAnimals){
            this.canUseAnimals = canUseAnimals;
            return this;
        }

        public Builder friendlyFire(boolean friendlyFire){
            this.friendlyFire = friendlyFire;
            return this;
        }

        public Builder canTakeFallDamage(boolean canTakeFallDamage){
            this.canTakeFallDamage = canTakeFallDamage;
            return this;
        }

        public MinigameSettings build(){
            return new MinigameSettings(
                    name,
                    description,
                    rules,
                    arenas,
                    minPlayers,
                    maxPlayer,
                    canJoinInProgress,
                    canSpectate,
                    canRespawn,
                    canBreakBlocks,
                    canPlaceBlocks,
                    canDropItems,
                    canPickupItems,
                    canDamagePlayers,
                    canDamageMobs,
                    canDamageAnimals,
                    canDamageVehicles,
                    canDamageBlocks,
                    canUseItems,
                    canUseBlocks,
                    canUseVehicles,
                    canUseMobs,
                    canUseAnimals,
                    friendlyFire,
                    canTakeFallDamage
            );
        }
    }
}
