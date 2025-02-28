package io.github.alpha2303.pastenotes.pastenotes.routes.notes;

import io.github.alpha2303.pastenotes.pastenotes.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

@Repository
public class NotesRepository implements INotesRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NotesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Result<UUID> create(Note newNote) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String query = "INSERT INTO notes (title, content, \"createdAt\", \"expiryAt\", \"isBurnedAfterRead\") VALUES (?,?,?,?,?) RETURNING id;";
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, newNote.getTitle());
            ps.setString(2, newNote.getContent());
            ps.setTimestamp(3, newNote.getCreatedAt());
            ps.setTimestamp(4, newNote.getExpiryAt());
            ps.setBoolean(5, newNote.isBurnedAfterRead());

            return ps;
        }, keyHolder);

        return Result.Ok(keyHolder.getKeyAs(UUID.class));
    }

    @Override
    public Result<Note> get(UUID id) {
        String query = String.format("SELECT * FROM notes WHERE id = '%s';", id);
        System.out.println(query);
        List<Note> fetchedNotes = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Note.class));

        if (fetchedNotes.size() != 1) {
            return Result.Err(new Exception(String.format("Note not found at id: %s", id)));
        }
        return Result.Ok(fetchedNotes.get(0));
    }

    @Override
    public Result<UUID> delete(UUID id) {
        String query = String.format("DELETE FROM notes WHERE id = '%s';", id);
        int deletedRowCount = jdbcTemplate.update(query);

        if (deletedRowCount != 1) {
            return Result.Err(new Exception(String.format("Note not found at id: %s", id)));
        }

        return Result.Ok(id);
    }
}
