package com.ajay.HolidayVilla.service;

import com.ajay.HolidayVilla.Transformer.RoomTransformer;
import com.ajay.HolidayVilla.dto.request.RoomRequest;
import com.ajay.HolidayVilla.dto.response.RoomResponse;
import com.ajay.HolidayVilla.model.Room;
import com.ajay.HolidayVilla.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    public RoomResponse addRoom(RoomRequest roomRequest) {
        Room savedRoom = roomRepository.save(RoomTransformer.roomRequestToRoom(roomRequest));
        return RoomTransformer.roomToRoomResponse(savedRoom);
    }



    public List<RoomResponse> getAllTodayInHouseRoom() {
        List<Room> roomList = roomRepository.getAllTodayInHouseRoom();
        List<RoomResponse> responseList = new ArrayList<>();
        for(Room room:roomList)
            responseList.add(RoomTransformer.roomToRoomResponse(room));

        return responseList;
    }


    public int getCountOfTodayInHouseRoom() {
        int count = roomRepository.getCountOfTodayInHouseRoom();
        return count;
    }


    public List<RoomResponse> getAllRoomByRoomStatus(String roomStatus) {
        List<Room> roomList = roomRepository.getAllRoomByRoomStatus(roomStatus);
        List<RoomResponse> responseList = new ArrayList<>();
        for(Room room:roomList)
            responseList.add(RoomTransformer.roomToRoomResponse(room));

        return responseList;
    }
}
