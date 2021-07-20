package tdd.bright.calculator

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 *  Created by eastbright on 2021/07/20
 */
class CalculatorTest {
    @Test
    fun plus() {
        assertThat(Calculator.plus(1, 2)).isEqualTo(3)
        assertThat(Calculator.plus(2, 3)).isEqualTo(5)
    }
}
