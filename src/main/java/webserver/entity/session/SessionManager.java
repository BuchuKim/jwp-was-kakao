package webserver.entity.session;

import java.util.Map;
import java.util.Optional;

public class SessionManager {
    // key: sessionId, value: sessionStorage
    private final Map<String, SessionEntity> session;

    public SessionManager(final Map<String, SessionEntity> session) {
        this.session = session;
    }

    public void createSession(final String sessionId) {
        session.put(sessionId, new SessionEntity());
    }

    public SessionEntity getSessionBySessionId(final String sessionId) {
        return Optional.ofNullable(session.get(sessionId))
            .orElse(new SessionEntity());
    }
}
