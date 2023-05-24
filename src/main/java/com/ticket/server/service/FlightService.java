package com.ticket.server.service;

import com.ticket.server.model.Flight;
import com.ticket.server.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {
    @Autowired
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public ResponseEntity<Flight> addFlight(Flight flight){
        Flight createdFlight = flightRepository.save(flight);
        return ResponseEntity.created(URI.create("/flight/" + createdFlight.getId())).body(createdFlight);
    }

    public ResponseEntity<Flight> getFlight(Long id){
        Optional<Flight> optionalFlight = flightRepository.findById(id);
        return optionalFlight.map(flight -> ResponseEntity.ok().body(flight))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public List<Flight> getAllFlight(){
        return flightRepository.findAll();
    }

    public ResponseEntity<Flight> deleteFlight(Long id){
        Optional<Flight> optionalFlight = flightRepository.findById(id);
        if(optionalFlight.isPresent()){
            flightRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Flight> updateFlight(Long id, Flight flight){
        Optional<Flight> optionalFlight = flightRepository.findById(id);
        if(optionalFlight.isPresent()){
            Flight updatedFlight = optionalFlight.get();
            updatedFlight.setArrivalAirport(flight.getArrivalAirport());
            updatedFlight.setDepartureAirport(flight.getDepartureAirport());
            updatedFlight.setDepartureTime(flight.getDepartureTime());
            updatedFlight.setDistance(flight.getDistance());
            updatedFlight.setArrivalTime(flight.getArrivalTime());
            updatedFlight.setFlightClassSeatsList(flight.getFlightClassSeatsList());
            updatedFlight.setStops(flight.getStops());

            flightRepository.save(updatedFlight);
            return ResponseEntity.ok().body(updatedFlight);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<Flight>> searchFlights(String departureKeyword, String arrivalKeyword, String arrivalTimeKeyword) {
        List<Flight> flights = flightRepository.findByDepartureAirportNameContainingOrArrivalAirportNameContainingAndArrivalTimeContaining(departureKeyword, arrivalKeyword, arrivalTimeKeyword);
        return ResponseEntity.ok().body(flights);
    }

    public ResponseEntity<List<Flight>> getFlightsSortedBy(String sortBy, boolean ascending) {
        Sort.Direction direction = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, sort);
        List<Flight> flights = flightRepository.findAll(pageable).getContent();
        return ResponseEntity.ok().body(flights);
    }
}
