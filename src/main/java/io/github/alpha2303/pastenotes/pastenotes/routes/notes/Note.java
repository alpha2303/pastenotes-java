package io.github.alpha2303.pastenotes.pastenotes.routes.notes;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.UUID;

@Component
@Scope("prototype")
public class Note {

    private UUID id;

    private String title;

    private String content;

    private long bytes;

    private Timestamp createdAt;

    private Timestamp expiryAt;

    private boolean isBurnedAfterRead;

    public Note(UUID id, String title, String content, long bytes, Timestamp createdAt, Timestamp expiryAt, boolean isBurnedAfterRead) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.bytes = bytes;
        this.createdAt = createdAt;
        this.expiryAt = expiryAt;
        this.isBurnedAfterRead = isBurnedAfterRead;
    }

    public Note(String title, String content, long bytes, Timestamp createdAt, Timestamp expiryAt, boolean isBurnedAfterRead) {
        this.title = title;
        this.content = content;
        this.bytes = bytes;
        this.createdAt = createdAt;
        this.expiryAt = expiryAt;
        this.isBurnedAfterRead = isBurnedAfterRead;
    }

    public Note() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public long getBytes() {
        return bytes;
    }

    public void setBytes(long bytes) {
        this.bytes = bytes;
    }

    public Timestamp getExpiryAt() {
        return expiryAt;
    }

    public void setExpiryAt(Timestamp expiryAt) {
        this.expiryAt = expiryAt;
    }

    public boolean isBurnedAfterRead() {
        return isBurnedAfterRead;
    }

    public void setBurnedAfterRead(boolean burnedAfterRead) {
        isBurnedAfterRead = burnedAfterRead;
    }

}
