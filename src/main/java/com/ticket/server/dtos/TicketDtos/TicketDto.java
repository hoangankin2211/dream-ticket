package com.ticket.server.dtos.TicketDtos;

import com.ticket.server.entities.TicketEntity;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDto {
    private long id;
    private String name;
    private String gender;
    private String phoneNumber;
    private String emailAddress;
    private String seat;
    private double luggage;
    private long birthday;
    private long timeBought;

    static public TicketDto fromEntity(TicketEntity entity){
        return TicketDto
                .builder()
                .id(entity.getId())
                .birthday(entity.getDob().getTime())
                .emailAddress(entity.getEmailAddress())
                .gender(entity.getGender().toString())
                .seat(entity.getSeat())
                .timeBought(entity.getTimeBought().getTime())
                .phoneNumber(entity.getPhoneNumber())
                .luggage(entity.getLuggage())
                .name(entity.getName())
                .build();
    }

}
