package uk.co.networkrail.trackgeometry.repository;

import uk.co.networkrail.trackgeometry.domain.LogEntry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LogEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {

}
