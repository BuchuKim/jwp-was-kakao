package webserver.entity.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SessionEntity {
    private final Map<String, Object> sessionStorage;

    public SessionEntity() {
        this.sessionStorage = new HashMap<>();
    }

    public void setAttribute(final String key, final Object value) {
        sessionStorage.put(key, value);
    }

    public void removeAttribute(final String key) {
        sessionStorage.remove(key);
    }

    public Optional<Object> getAttribute(final String key) {
        return Optional.ofNullable(sessionStorage.get(key));
    }
}
