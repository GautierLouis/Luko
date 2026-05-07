package com.louisgautier.server.domain.repo.implem

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NormalizeQueryTest {

    @Test
    fun `toneVariants should return all variants for a`() {
        val result = "ma".toneVariants()
        val expected = listOf("ma", "mā", "má", "mǎ", "mà")
        assertEquals(expected.size, result.size)
        assertTrue(result.containsAll(expected))
    }

    @Test
    fun `toneVariants should return all variants for e`() {
        val result = "me".toneVariants()
        val expected = listOf("me", "mē", "mé", "mě", "mè")
        assertEquals(expected.size, result.size)
        assertTrue(result.containsAll(expected))
    }

    @Test
    fun `toneVariants should return all variants for i`() {
        val result = "mi".toneVariants()
        val expected = listOf("mi", "mī", "mí", "mǐ", "mì")
        assertEquals(expected.size, result.size)
        assertTrue(result.containsAll(expected))
    }

    @Test
    fun `toneVariants should return all variants for o`() {
        val result = "mo".toneVariants()
        val expected = listOf("mo", "mō", "mó", "mǒ", "mò")
        assertEquals(expected.size, result.size)
        assertTrue(result.containsAll(expected))
    }

    @Test
    fun `toneVariants should return all variants for u including umlauts`() {
        val result = "lu".toneVariants()
        val expected = listOf("lu", "lū", "lú", "lǔ", "lù", "lü", "lǖ", "lǘ", "lǚ", "lǜ")
        assertEquals(expected.size, result.size)
        assertTrue(result.containsAll(expected))
    }

    @Test
    fun `toneVariants should handle multiple vowels`() {
        val result = "lia".toneVariants()
        // It will first replace 'i' then 'a'
        val expected = listOf(
            "lia",
            "līa", "lía", "lǐa", "lìa", // from i
            "liā", "liá", "liǎ", "lià"  // from a
        )
        assertEquals(expected.size, result.size)
        assertTrue(result.containsAll(expected))
    }

    @Test
    fun `toneVariants should handle complex cases like guo`() {
        val result = "guo".toneVariants()
        val expected = listOf(
            "guo",
            "gūo", "gúo", "gǔo", "gùo", "güo", "gǖo", "gǘo", "gǚo", "gǜo", // from u
            "guō", "guó", "guǒ", "guò" // from o
        )
        assertEquals(expected.size, result.size)
        assertTrue(result.containsAll(expected))
    }

    @Test
    fun `toneVariants should handle already accented vowels`() {
        val result = "mā".toneVariants()
        // It should only return the original since 'ā' doesn't contain 'a'
        assertEquals(listOf("mā"), result)
    }
}
