package me.quickscythe.quipt.minigames.tests;

import me.quickscythe.quipt.QuiptPaperIntegration;
import me.quickscythe.quipt.minigames.MinigamesIntegration;
import me.quickscythe.quipt.minigames.core.MinigameManager;
import me.quickscythe.quipt.minigames.core.arenas.ArenaDefinition;
import me.quickscythe.quipt.minigames.core.arenas.ArenaManager;
import me.quickscythe.quipt.minigames.core.templates.Spleef;
import me.quickscythe.quipt.minigames.tests.objects.ObjectFactory;
import me.quickscythe.quipt.minigames.utils.MinigamesUtils;
import me.quickscythe.quipt.utils.CoreUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MinigameTests {


    private MinigamesIntegration paperIntegration;

    @BeforeEach
    public void setUp() {
        paperIntegration = new MinigamesIntegration(null);
        MinigamesUtils.init(paperIntegration);
    }

    @Test
    void testSpleef() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        MinigameManager.registerMinigame(Spleef.class);
        Spleef spleef = MinigameManager.createMinigame(paperIntegration, Spleef.class, ObjectFactory.testArenaDefinition());

    }

    @Test
    void alwaysPass(){
        assertTrue(true);
    }
}
