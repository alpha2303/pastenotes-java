package io.github.alpha2303.pastenotes.pastenotes.routes.notes;

import org.springframework.context.ApplicationContext;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class NotesService {
    private final ApplicationContext appContext;
    public NotesService(ApplicationContext context) {
        this.appContext = context;
    }
    public Note createNote(NotePayload payload) {
        long bytes = payload.getContent().getBytes(StandardCharsets.UTF_8).length;
        Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

        Timestamp expiryAt = null;
        if (payload.getExpiryAtMins() > 0) {
            LocalDateTime expiryAtLocalTime = LocalDateTime.now().plusMinutes(payload.getExpiryAtMins());
            expiryAt = Timestamp.valueOf(expiryAtLocalTime);
        }

        Note note = this.appContext.getBean(Note.class);
        note.setTitle(payload.getTitle());
        note.setContent(payload.getContent());
        note.setBytes(bytes);
        note.setCreatedAt(createdAt);
        note.setExpiryAt(expiryAt);
        note.setBurnedAfterRead(payload.isBurnedAfterRead());

        return note;
    }
}
