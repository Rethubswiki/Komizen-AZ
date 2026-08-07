package com.komizen.az.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class JsonUtilsTest {

    data class TestData(val name: String, val value: Int)

    @Test
    fun `prettyPrint formats JSON`() {
        val input = """{"name":"test","value":42}"""
        val result = JsonUtils.prettyPrint(input)
        assert(result.contains("\n"))
    }

    @Test
    fun `fromJson parses valid JSON`() {
        val json = """{"name":"hello","value":99}"""
        val result = JsonUtils.fromJson<TestData>(json)
        assertNotNull(result)
        assertEquals("hello", result?.name)
        assertEquals(99, result?.value)
    }

    @Test
    fun `fromJson returns null for invalid JSON`() {
        val result = JsonUtils.fromJson<TestData>("not json")
        assertNull(result)
    }

    @Test
    fun `toJson serializes object`() {
        val data = TestData("world", 7)
        val json = JsonUtils.toJson(data)
        assert(json.contains(""name":"world""))
        assert(json.contains(""value":7"))
    }
}
