package com.ajay.HolidayVilla.Transformer;

import com.ajay.HolidayVilla.Enum.RoomStatus;
import com.ajay.HolidayVilla.dto.request.RoomRequest;
import com.ajay.HolidayVilla.dto.response.RoomResponse;
import com.ajay.HolidayVilla.model.Room;

public class RoomTransformer {

    public static Room roomRequestToRoom(RoomRequest roomRequest){
        return Room.builder()
                .roomStatus(RoomStatus.VACANT)
                .farePerDay(roomRequest.getFarePerDay())
                .roomType(roomRequest.getRoomType())
                .capacity(roomRequest.getCapacity())
                .roomNO(roomRequest.getRoomNO())
                .build();
    }
    public static RoomResponse roomToRoomResponse(Room room){
        return RoomResponse.builder()
                .roomStatus(room.getRoomStatus())
                .farePerDay(room.getFarePerDay())
                .roomType(room.getRoomType())
                .capacity(room.getCapacity())
                .roomNO(room.getRoomNO())
                .build();
    }
}
