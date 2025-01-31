package me.quickscythe.quipt.minigames.core;

import me.quickscythe.quipt.minigames.utils.MinigamesUtils;
import me.quickscythe.quipt.utils.chat.MessageUtils;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;

public class MinigameCountdown implements Runnable {

    private final Minigame minigame;
    private final int secondsToPass;
    private final Type type;
    private final long started;
    private final Runnable finish;
    private final CountdownChecker checker;
    private final Runnable fail;
    private final String messageKey;
    private int secondsPassed = 0;
    private boolean finished = false;


    public MinigameCountdown(Minigame minigame, int secondsToPass, @Nullable Type type, @Nullable String messageKey, CountdownChecker checker, Runnable finish, Runnable fail) {
        this.minigame = minigame;
        this.secondsToPass = secondsToPass;
        this.type = type;
        this.messageKey = messageKey;
        this.started = System.currentTimeMillis();
        this.finish = finish;
        this.checker = checker;
        this.fail = fail;
    }

    @Override
    public void run() {
        if (!checker.check()) {
            fail.run();
            return;
        }
        long now = System.currentTimeMillis();
        if (now - started >= secondsPassed * 1000L) {
            //send message

            if (type != null && messageKey != null) {
                if (type == Type.CHAT) {
                    int remainingSeconds = secondsToPass - secondsPassed;
                    if (secondsPassed == 0 || remainingSeconds == 60 || remainingSeconds == 30 || remainingSeconds == 20 ||remainingSeconds == 10 || remainingSeconds == 5 || remainingSeconds <= 3)
                        minigame.broadcast(MessageUtils.getMessage(messageKey, String.valueOf(remainingSeconds)));
                }
            }
            secondsPassed = secondsPassed + 1;
            if (secondsPassed >= secondsToPass) {
                finish.run();
                finished = true;
                return;
            }
        }

        Bukkit.getScheduler().runTaskLater(MinigamesUtils.plugin(), this, 1);
    }

    public boolean finished(){
        return finished;
    }

    public enum Type {
        CHAT, TITLE;
    }

    @FunctionalInterface
    public interface CountdownChecker {
        boolean check();
    }
}
