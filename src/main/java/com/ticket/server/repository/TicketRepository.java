package com.ticket.server.repository;

import com.ticket.server.entities.TicketEntity;
import com.ticket.server.model.Ticket;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    @Query(value =
            """
                Select tic.* from ticket_entity as tic
                where tic.flight_id = flightId
            """
            , nativeQuery = true
    )
    List<TicketEntity> findAllByFlight(long flightId);
}
