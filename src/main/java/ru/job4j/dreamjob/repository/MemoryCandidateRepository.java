package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class MemoryCandidateRepository implements CandidateRepository {

    private final AtomicInteger nextId = new AtomicInteger(0);

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    private MemoryCandidateRepository() {
        save(new Candidate(0, "Simov Aleksandr",
                "Looking for a job Senior Java", LocalDateTime.now(), true, 1, 0));
        save(new Candidate(0, "Rogov Ilya",
                "Looking for a job Junior Java", LocalDateTime.now(), false, 2, 0));
        save(new Candidate(0, "Pases Viktor",
                "Looking for a job Junior+ Java Developer", LocalDateTime.now(), false, 3, 0));
        save(new Candidate(0, "Adaev Andrey",
                "Looking for a job Intern Java Developer", LocalDateTime.now(), false, 4, 0));
        save(new Candidate(0, "Popov Vladislav",
                "Looking for a job Middle+ Java Developer", LocalDateTime.now(), false, 4, 0));
        save(new Candidate(0, "Batov Sergey",
                "Looking for a job Middle Java Developer", LocalDateTime.now(), false, 1, 0));
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId.incrementAndGet());
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
       return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(),
                (id, oldCandidate) -> new Candidate(oldCandidate.getId(),
                        candidate.getName(),
                        candidate.getDescription(),
                        candidate.getCreationDate(),
                        candidate.getVisible(),
                        candidate.getCityId(),
                        candidate.getFileId())) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}