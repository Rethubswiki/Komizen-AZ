package com.komizen.az.data.local

import com.komizen.az.data.model.Source
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest {

    private val converters = Converters()

    @Test
    fun `source list roundtrip`() {
        val sources = listOf(
            Source("1", "Source A", "en", "https://a.com"),
            Source("2", "Source B", "es", "https://b.com")
        )
        val json = converters.fromSourceList(sources)
        val result = converters.toSourceList(json)
        assertEquals(sources, result)
    }

    @Test
    fun `empty list roundtrip`() {
        val sources = emptyList<Source>()
        val json = converters.fromSourceList(sources)
        val result = converters.toSourceList(json)
        assertEquals(sources, result)
    }
}
