package org.museum.other;

import org.museum.artefacts.Artefact;
import org.museum.data.DataBase;

import java.util.List;

/**
 * Represents a physical room in the museum with capacity metadata.
 */
public class Room
{
    private final String roomNum;
    private final String roomName;
    private final int capacity;

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
    public int getCurrentSpace(boolean testMode) throws Exception
    {
        List<Artefact> artefacts = DataBase.PullArtefacts(testMode);
        int capacity = this.capacity;
        assert artefacts != null;
        for (Artefact artefact : artefacts)
        {
            if (artefact.getCurrentRoom().equals(this.roomNum))
            {
                capacity -= 1;
            }
        }
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

    public int getCapacity()
    {
        return capacity;
    }
}
