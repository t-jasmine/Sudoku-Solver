package org.openjfx;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class BoardTest {
    @Test
    void testBoard()
    {
        Board b = new Board();
    }

    @Test
    void testBoardCopy()
    {
        Board b = new Board();
        for(int r = 0; r<9; r++)
        {
            for(int c = 0; c<9; c++)
            {
                b.set(c,r,1);
            }
        }

        Board b2 = new Board(b);

        for(int r = 0; r<9; r++)
        {
            for(int c = 0; c<9; c++)
            {
                assertTrue(b2.get(c,r)==1);
            }
        }
    }

    @Test
    void testGet()
    {
        Board b = new Board();
        for(int r = 0; r<9; r++)
        {
            for(int c = 0; c<9; c++)
            {
                assertTrue(b.get(c,r)==null);
            }
        }
    }

    @Test
    void testSet()
    {
        Board b = new Board();
        for(int r = 0; r<9; r++)
        {
            for(int c = 0; c<9; c++)
            {
                b.set(c,r,1);
            }
        }
    }

}
