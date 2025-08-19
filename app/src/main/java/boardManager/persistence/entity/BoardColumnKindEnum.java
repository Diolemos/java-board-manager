package boardManager.persistence.entity;

import java.util.stream.Stream;

public enum BoardColumnKindEnum {
    INITIAL, FINAL, CANCEL, PENDING;

    public static BoardColumnKindEnum findByName(String name) {
        return Stream.of(values())
                     .filter(k -> k.name().equalsIgnoreCase(name))
                     .findFirst()
                     .orElseThrow();
    }
}
