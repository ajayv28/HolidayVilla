package com.ajay.HolidayVilla.service;

import com.ajay.HolidayVilla.Transformer.RoomTransformer;
import com.ajay.HolidayVilla.dto.request.RoomRequest;
import com.ajay.HolidayVilla.dto.response.RoomResponse;
import com.ajay.HolidayVilla.model.Room;
import com.ajay.HolidayVilla.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    public RoomResponse addRoom(RoomRequest roomRequest) {
        Room savedRoom = roomRepository.save(RoomTransformer.roomRequestToRoom(roomRequest));
        return RoomTransformer.roomToRoomResponse(savedRoom);
    }
}
