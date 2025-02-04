package me.quickscythe.quipt.minigames.tests;

import com.mojang.brigadier.Message;
import me.quickscythe.quipt.QuiptPaperIntegration;
import me.quickscythe.quipt.minigames.MinigamesIntegration;
import me.quickscythe.quipt.minigames.core.MinigameManager;
import me.quickscythe.quipt.minigames.core.templates.Spleef;
import me.quickscythe.quipt.minigames.tests.objects.ObjectFactory;
import me.quickscythe.quipt.minigames.utils.MinigamesUtils;
import me.quickscythe.quipt.utils.CoreUtils;
import me.quickscythe.quipt.utils.chat.MessageUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

public class MinigameTests {


    private QuiptPaperIntegration quiptPaperIntegration;
    private MinigamesIntegration paperIntegration;

    @BeforeEach
    void setUp() {
        quiptPaperIntegration = new QuiptPaperIntegration(null);
        CoreUtils.init(quiptPaperIntegration);

        paperIntegration = new MinigamesIntegration(null);
        MinigamesUtils.init(paperIntegration);
    }

    @Test
    void testSpleef() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        MinigameManager.registerMinigame(Spleef.class);
        Spleef spleef = MinigameManager.createMinigame(paperIntegration, Spleef.class, ObjectFactory.testArenaDefinition());
        spleef.start();
        assertFalse(spleef.open(), "Spleef did not open");
    }

    @Test
    void testCoreMessages() {
        MessageUtils.addMessage("test.message", "{\"text\":\"Test Message\"}");

        assertEquals("Test Message", MessageUtils.plainText(MessageUtils.getMessage("test.message")));
    }

    @AfterEach
    void tearDown() throws IOException {
        paperIntegration.destroy();
        quiptPaperIntegration.destroy();
    }
}
