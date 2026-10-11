package com.nic.lgpower

import org.junit.Assert.assertEquals
import org.junit.Test

class MacListTest {
    @Test fun oldSingleMacStillWorks() =
        assertEquals(listOf("AA:BB:CC:DD:EE:FF"), macList("AA:BB:CC:DD:EE:FF"))

    @Test fun commaListSplits() =
        assertEquals(listOf("AA:BB:CC:DD:EE:FF", "20:3D:BD:DE:49:12"), macList("AA:BB:CC:DD:EE:FF, 20:3D:BD:DE:49:12"))

    @Test fun blankAndStrayEntriesAreIgnored() {
        assertEquals(emptyList<String>(), macList(""))
        assertEquals(emptyList<String>(), macList("  , ;\n "))
        assertEquals(listOf("A", "B"), macList(",A,, B\n"))
    }
}
