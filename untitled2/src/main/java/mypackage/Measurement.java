package mypackage;

import mypackage.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Measurement {

    private final LocalDateTime start;
    private final LocalDateTime end;
    private final BigDecimal value;
    private final User user;

    public Measurement(final LocalDateTime start, final LocalDateTime end, final BigDecimal value, final User user) {
        this.start = start;
        this.end = end;
        this.value = value;
        this.user = user;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public BigDecimal getValue() {
        return value;
    }

    public User getUser() {
        return user;
    }
}

