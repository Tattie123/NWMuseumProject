package org.museum.other;

public class Room
{
    private final String roomNum;
    private final String roomName;
    private final int capacity;

    public boolean checkFits()
    {
        //todo implement feat
        return true;
    }

    public Room(String roomNum, String roomName, int capacity)
    {
        this.roomNum = roomNum;
        this.roomName = roomName;
        this.capacity = capacity;
    }

    public String getRoomNum()
    {
        return roomNum;
    }

    public String getRoomName()
    {
        return roomName;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public String getName()
    {
        return roomName;
    }
}
