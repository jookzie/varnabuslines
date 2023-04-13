package com.varnabuslines.persistence;

import com.varnabuslines.domain.Bus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepo extends CrudRepository<Bus, Long>
{
}