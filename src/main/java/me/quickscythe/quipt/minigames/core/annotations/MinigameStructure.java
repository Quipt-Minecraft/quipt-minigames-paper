package me.quickscythe.quipt.minigames.core.annotations;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MinigameStructure {
    

    int DEFAULT_MIN_PLAYERS = 4;
    int DEFAULT_MAX_PLAYERS = 50;
    boolean DEFAULT_CAN_JOIN_IN_PROGRESS = false;
    boolean DEFAULT_CAN_SPECTATE = true;
    boolean DEFAULT_CAN_RESPAWN = true;
    boolean DEFAULT_CAN_BREAK_BLOCKS = false;
    boolean DEFAULT_CAN_PLACE_BLOCKS = false;
    boolean DEFAULT_CAN_DROP_ITEMS = false;
    boolean DEFAULT_CAN_PICKUP_ITEMS = false;
    boolean DEFAULT_CAN_DAMAGE_PLAYERS = false;
    boolean DEFAULT_CAN_DAMAGE_MOBS = false;
    boolean DEFAULT_CAN_DAMAGE_ANIMALS = false;
    boolean DEFAULT_CAN_DAMAGE_VEHICLES = false;
    boolean DEFAULT_CAN_DAMAGE_BLOCKS = false;
    boolean DEFAULT_CAN_USE_ITEMS = false;
    boolean DEFAULT_CAN_USE_BLOCKS = false;
    boolean DEFAULT_CAN_USE_VEHICLES = false;
    boolean DEFAULT_CAN_USE_MOBS = false;
    boolean DEFAULT_CAN_USE_ANIMALS = false;


    String name();

    String description();

    String author();

    String[] rules();

    String[] arenas();

    int minPlayers() default DEFAULT_MIN_PLAYERS;

    int maxPlayers() default DEFAULT_MAX_PLAYERS;

    boolean canJoinInProgress() default DEFAULT_CAN_JOIN_IN_PROGRESS;

    boolean canSpectate() default DEFAULT_CAN_SPECTATE;

    boolean canRespawn() default DEFAULT_CAN_RESPAWN;

    boolean canBreakBlocks() default DEFAULT_CAN_BREAK_BLOCKS;

    boolean canPlaceBlocks() default DEFAULT_CAN_PLACE_BLOCKS;

    boolean canDropItems() default DEFAULT_CAN_DROP_ITEMS;

    boolean canPickupItems() default DEFAULT_CAN_PICKUP_ITEMS;

    boolean canDamagePlayers() default DEFAULT_CAN_DAMAGE_PLAYERS;

    boolean canDamageMobs() default DEFAULT_CAN_DAMAGE_MOBS;

    boolean canDamageAnimals() default DEFAULT_CAN_DAMAGE_ANIMALS;

    boolean canDamageVehicles() default DEFAULT_CAN_DAMAGE_VEHICLES;

    boolean canDamageBlocks() default DEFAULT_CAN_DAMAGE_BLOCKS;

    boolean canUseItems() default DEFAULT_CAN_USE_ITEMS;

    boolean canUseBlocks() default DEFAULT_CAN_USE_BLOCKS;

    boolean canUseVehicles() default DEFAULT_CAN_USE_VEHICLES;

    boolean canUseMobs() default DEFAULT_CAN_USE_MOBS;

    boolean canUseAnimals() default DEFAULT_CAN_USE_ANIMALS;

}
