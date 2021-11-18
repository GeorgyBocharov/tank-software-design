package ru.mipt.bit.platformer.publisher;

import ru.mipt.bit.platformer.objects.Activated;

import java.util.List;

public interface Subscriber {
    void updateWithNew(Activated newActivated);
    void updateWithDeleted(Activated deletedActivated);
}
