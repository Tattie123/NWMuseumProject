package org.museum.other;

import org.museum.artefacts.Artefact;
import org.museum.data.DataBase;

import java.util.List;

/**
 * Represents a physical room in the museum with capacity metadata.
 */
public record Room(String roomNum, String roomName, int capacity)
{
    /**
     * Construct a Room instance.
     *
     * @param roomNum  internal room number
     * @param roomName human-readable room name
     * @param capacity capacity integer
     */
    public Room
    {
    }

    /**
     * @return internal room number
     */
    @Override
    public String roomNum()
    {
        return roomNum;
    }

    /**
     * @return human-readable room name
     */
    @Override
    public String roomName()
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
}
