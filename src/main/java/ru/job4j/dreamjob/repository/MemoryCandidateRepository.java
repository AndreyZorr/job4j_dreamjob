package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryCandidateRepository implements CandidateRepository {

    private static final MemoryCandidateRepository INSTANCE = new MemoryCandidateRepository();

    private int nextId = 1;

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    private MemoryCandidateRepository() {
        save(new Candidate(0, "Simov Aleksandr",
                "Looking for a job Senior Java", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)));
        save(new Candidate(0, "Rogov Ilya",
                "Looking for a job Junior Java", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)));
        save(new Candidate(0, "Pases Viktor",
                "Looking for a job Junior+ Java Developer", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)));
        save(new Candidate(0, "Adaev Andrey",
                "Looking for a job Intern Java Developer", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)));
        save(new Candidate(0, "Popov Vladislav",
                "Looking for a job Middle+ Java Developer", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)));
        save(new Candidate(0, "Batov Sergey",
                "Looking for a job Middle Java Developer", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)));
    }

    public static MemoryCandidateRepository getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
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
                (id, oldCandidate) -> new Candidate(
                        oldCandidate.getId(), candidate.getName(), candidate.getDescription(),
                        candidate.getCreationDate())) != null;
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
