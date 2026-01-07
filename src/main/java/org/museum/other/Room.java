package org.museum.other;

/**
 * Represents a physical room in the museum with capacity metadata.
 */
public class Room
{
    private final String roomNum;
    private final String roomName;
    private final int capacity;

    /**
     * Placeholder to check whether an artefact fits in this room.
     *
     * @return true if fits (default true until implemented)
     */
    public boolean checkFits()
    {
        //todo implement feat
        return true;
    }

    /**
     * Construct a Room instance.
     *
     * @param roomNum internal room number
     * @param roomName human-readable room name
     * @param capacity capacity integer
     */
    public Room(String roomNum, String roomName, int capacity)
    {
        this.roomNum = roomNum;
        this.roomName = roomName;
        this.capacity = capacity;
    }

    /**
     * @return internal room number
     */
    public String getRoomNum()
    {
        return roomNum;
    }

    /**
     * @return human-readable room name
     */
    public String getRoomName()
    {
        return roomName;
    }

    /**
     * @return room capacity
     */
    public int getCapacity()
    {
        return capacity;
    }

    /**
     * Alias for getRoomName().
     *
     * @return room name
     */
    public String getName()
    {
        return roomName;
    }
}
