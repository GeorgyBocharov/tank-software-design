package ru.mipt.bit.platformer.commands;

public interface CommandScheduler {
    boolean scheduleCommand(Command command);
}
