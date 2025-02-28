package io.github.alpha2303.pastenotes.pastenotes.routes.notes;

import io.github.alpha2303.pastenotes.pastenotes.utils.Result;

import java.util.UUID;

public interface INotesRepository {
    Result<UUID> create(Note newNote);

    Result<Note> get(UUID id);

    Result<UUID> delete(UUID id);
}
