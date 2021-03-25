package it.cardinali.report.repository;

import it.cardinali.report.entity.Report1Entity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/************************************************************************
 * Created: 24/03/2021                                                  *
 * Author:  mcardinali                                                  *
 ************************************************************************/
@Repository
public interface Report1Repository extends CrudRepository<Report1Entity, Integer> {

      Collection<Report1Entity> findByName(String name);
}
