package io.github.alpha2303.pastenotes.pastenotes.routes.notes;

import jakarta.validation.constraints.NotEmpty;

public class NotePayload {
    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private long expiryAtMins;

    private boolean isBurnedAfterRead;

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

    public long getExpiryAtMins() {
        return expiryAtMins;
    }

    public void setExpiryAtMins(long expiryAtMins) {
        this.expiryAtMins = expiryAtMins;
    }

    public boolean isBurnedAfterRead() {
        return isBurnedAfterRead;
    }

    public void setBurnedAfterRead(boolean burnedAfterRead) {
        isBurnedAfterRead = burnedAfterRead;
    }
}
