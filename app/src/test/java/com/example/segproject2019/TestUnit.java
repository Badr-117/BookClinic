package com.example.segproject2019;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestUnit {
    private SimpleMath simpleMath;
    @Before
    public void setUp(){
        simpleMath = new SimpleMath();
        System.out.println("Ready for testing");
    }
    @After
    public void tearDown(){
        System.out.println("Done with testing");
    }
    @Test
    public void testAdd() {
        int total = simpleMath.add(4, 5);
        assertEquals("Simple Math is not adding correctly", 9, total);
    }

    @Test
    public void testAdd2() {
        int total = simpleMath.add(8, 8);
        assertEquals("Simple Math is not adding correctly", 16, total);
    }

    @Test
    public void testDiff() {
        int total = simpleMath.diff(9, 2);
        assertEquals("Simple Math is not subtracting correctly", 7, total);
    }
    @Test
    public void testDiv(){
        double quotient = simpleMath.div(9,3);
        assertEquals("Simple math is not dividing correctly", 3.0, quotient, 0.0);
    }

    @Test
    public void testDivWithZeroDivisor(){
        double quotient = simpleMath.div(9,0);
        assertEquals("Simple math is not handling division by zero correctly", 0.0, quotient, 0.0);
    }
}