package me.makarov.lintsample

import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test

class SomeTest {

    @Test
    fun test1() {
        assertEquals(1 + 1, 2)
    }

    @Test
    @Ignore
    fun test2() {
        assertEquals(1 + 4, 3)
    }


    @Test
    @Ignore("something")
    fun test3() {
        assertEquals(1 + 1, 2)
    }

}