package com.varnabuslines.persistence;

import com.varnabuslines.domain.Alert;
import org.springframework.data.repository.CrudRepository;

public interface AlertRepo extends CrudRepository<Alert, Long>
{
}
