package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class MemoryVacancyRepository implements VacancyRepository {

    private final AtomicInteger nextId = new AtomicInteger(0);
    private final Map<Integer, Vacancy> vacancies = new ConcurrentHashMap<>();

    private MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "Good job",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), true));
        save(new Vacancy(0, "Junior Java Developer", "Great job",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false));
        save(new Vacancy(0, "Junior+ Java Developer", "High paying job",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), true));
        save(new Vacancy(0, "Middle Java Developer", "Good job",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false));
        save(new Vacancy(0, "Middle+ Java Developer", "Hard work, good job",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), true));
        save(new Vacancy(0, "Senior Java Developer", "High paying job, hard work",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), false));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId.incrementAndGet());
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
          return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(),
                (id, oldVacancy) -> new Vacancy(oldVacancy.getId(),
                        vacancy.getTitle(), vacancy.getDescription(), vacancy.getCreationDate(), vacancy.getVisible()))
                != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }
}