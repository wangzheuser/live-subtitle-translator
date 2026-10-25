package app.yuxino.mimi.android

import org.junit.Assert.*
import org.junit.Test
import java.io.File
import java.io.IOException

class SavedSessionsTest {
    @Test fun paginationUsesSharedNewestFirstOneBasedIndices() {
        fun page(total: Int, number: Int): SavedSessionPage {
            val indices = (total downTo 1).drop(number * 30).take(30)
            return SavedSessionPage(total, number, indices.map { SavedSessionCaption("system", "", "", 0, it) })
        }
        assertFalse(page(0, 0).hasNext)
        assertFalse(page(1, 0).hasNext)
        assertFalse(page(30, 0).hasNext)
        assertTrue(page(31, 0).hasNext)
        assertFalse(page(31, 1).hasNext)
        assertTrue(page(60, 0).hasNext)
        assertFalse(page(60, 1).hasNext)
    }
    @Test fun formatterFailureRemovesTheNewDestination() {
        var removed = false
        assertThrows(IOException::class.java) {
            exportSavedSession({ throw IOException("synthetic") }, { fail("must not write") }, { removed = true })
        }
        assertTrue(removed)
    }
    @Test fun failedCopyRemovesTemporaryAndNewDestination() {
        val temporary = File.createTempFile("mimi-export-test", ".txt")
        var removed = false
        assertThrows(IOException::class.java) {
            exportSavedSession({ temporary }, { throw IOException("synthetic") }, { removed = true })
        }
        assertTrue(removed); assertFalse(temporary.exists())
    }
    @Test fun successfulCopyRemovesOnlyTemporary() {
        val temporary = File.createTempFile("mimi-export-test", ".txt").apply { writeText("synthetic fixture") }
        var copied = false
        exportSavedSession({ temporary }, { copied = it.readText() == "synthetic fixture" }, { fail("must preserve destination") })
        assertTrue(copied); assertFalse(temporary.exists())
    }
}
