package io.github.alpha2303.pastenotes.pastenotes.routes.notes;

import io.github.alpha2303.pastenotes.pastenotes.utils.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;


@RestController
public class NotesController {

    private static final Logger log = LogManager.getLogger(NotesController.class);
    public final INotesRepository notesRepository;
    private final NotesService notesService;

    public NotesController(ApplicationContext context) {
        this.notesRepository = context.getBean(NotesRepository.class);
        this.notesService = new NotesService(context);
    }

    @PostMapping("/notes")
    public String post(@RequestBody NotePayload payload) {
        Note newNote = notesService.createNote(payload);

        Result<UUID> createdIdResult = notesRepository.create(newNote);
        if (createdIdResult.isErr()) {
            log.atError().log("NotesController.post() - Failed to create Note.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            return createdIdResult.unwrap().toString();
        } catch (Exception e) {
            log.atError().log("NotesController.post() - {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/notes/{id}")
    public Note get(@PathVariable String id) {
        UUID noteUUID = UUID.fromString(id);
        Result<Note> noteResult = notesRepository.get(noteUUID);
        if (noteResult.isErr()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        try {
            Note note = noteResult.unwrap();
            if (note.getExpiryAt() != null && LocalDateTime.now().isAfter(note.getExpiryAt().toLocalDateTime())) {
                notesRepository.delete(note.getId()).unwrap();
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
            return note;
        } catch (Exception e) {
            log.atError().log("NotesController.get() - {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/notes/{id}")
    public String delete(@PathVariable String id) {
        try {
            UUID noteUUID = UUID.fromString(id);
            Result<UUID> deletedIdResult = notesRepository.delete(noteUUID);

            if (deletedIdResult.isErr()) {
                log.atError().log("NotesController.delete() - Failed to delete Note");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            return deletedIdResult.unwrap().toString();
        } catch (Exception e) {
            log.atError().log("NotesController.delete() - {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
