package jp.tinyport.mylibrary

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class KotlinLibraryTest {
    @Test
    fun hello() {
        val lib = KotlinLibrary()
        assertThat(lib.hello()).isEqualTo("hello!")
    }
}
