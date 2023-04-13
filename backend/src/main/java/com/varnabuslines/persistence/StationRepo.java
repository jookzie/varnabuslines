package com.varnabuslines.persistence;

import com.varnabuslines.domain.Station;
import org.springframework.data.repository.CrudRepository;

public interface StationRepo extends CrudRepository<Station, Long>
{
}