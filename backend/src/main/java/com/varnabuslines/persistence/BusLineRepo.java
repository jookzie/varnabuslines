package com.varnabuslines.persistence;

import com.varnabuslines.domain.BusLine;
import org.springframework.data.repository.CrudRepository;

public interface BusLineRepo extends CrudRepository<BusLine, String>
{
}