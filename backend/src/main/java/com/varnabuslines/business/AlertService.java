package com.varnabuslines.business;

import com.varnabuslines.domain.Alert;
import com.varnabuslines.persistence.AlertRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AlertService
{
    private AlertRepo repository;

    public Alert create(final String title, final String content)
    {
        return repository.save(new Alert(title, content, new Date()));
    }

    public Iterable<Alert> get()
    {
        return repository.findAll();
    }

    public Optional<Alert> get(final long id)
    {
        return repository.findById(id);
    }

    public Optional<Alert> update(final long id, final String title, final String content)
    {
        var opt = repository.findById(id);
        if (opt.isEmpty())
            return Optional.empty();

        var alert = opt.get();
        alert.setTitle(title);
        alert.setContent(content);
        alert.setTimestamp(new Date());

        return Optional.of(repository.save(alert));
    }

    public void delete(final long id)
    {
        repository.deleteById(id);
    }
}
