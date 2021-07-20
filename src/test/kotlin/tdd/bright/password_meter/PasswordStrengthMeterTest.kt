package tdd.bright.password_meter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 *  Created by eastbright on 2021/07/20
 */
class PasswordStrengthMeterTest {
    private val meter = PasswordStrengthMeter()

    @DisplayName("모든 조건을 충족하면 암호 강도는 강함이어야 함")
    @Test
    fun meetsAllCriteria_Then_Strong() {
        assertStrength("ab12!@AB", PasswordStrength.STRONG)
        assertStrength("abc1!Add", PasswordStrength.STRONG)
    }

    @DisplayName("길이만 8글자 미만이고 나머지 조건은 충족한다면 암호 강도는 보통이어야 함")
    @Test
    fun meetsOtherCriteria_except_for_Length_Then_Normal() {
        assertStrength("ab12!@A", PasswordStrength.NORMAL)
        assertStrength("Ab12!c", PasswordStrength.NORMAL)
    }

    @DisplayName("숫자를 포함하지 않고 나머지 조건은 충족하는 경우 암호 강도는 보통이어야 함")
    @Test
    fun meetsOtherCriteria_except_for_number_Then_Normal() {
        assertStrength("ab!@ABqwer", PasswordStrength.NORMAL)
    }

    @DisplayName("공백 문자열이 입력되면 INVALID를 리턴한다")
    @Test
    fun nullInput_Then_Invalid() {
        assertStrength("", PasswordStrength.INVALID)
    }

    @DisplayName("대문자를 포함하지 않고 나머지 조건은 충족하는 경우 암호 강도는 보통이어야 함")
    @Test
    fun meetsOtherCriteria_except_for_Uppercase_Then_Normal() {
        assertStrength("ab12!@df", PasswordStrength.NORMAL)
    }

    @DisplayName("길이가 8글자 이상인 조건만 충족하는 경우 암호 강도는 약함이어야 함")
    @Test
    fun meetsOnlyLengthCriteria_Then_Weak() {
        assertStrength("abcdefgh", PasswordStrength.WEAK)
    }

    @DisplayName("숫자 조건만 충족하는 경우 암호 강도는 약함이어야 함")
    @Test
    fun meetsOnlyNumCriteria_Then_Weak() {
        assertStrength("12345", PasswordStrength.WEAK)
    }

    @DisplayName("대문자 조건만 충족하는 경우 암호 강도는 약함이어야 함")
    @Test
    fun meetsOnlyUpperCriteria_Then_Weak() {
        assertStrength("ABZEF", PasswordStrength.WEAK)
    }

    @DisplayName("아주 조건도 충족하지 않은 경우 암호 강도는 약함이어야 함")
    @Test
    fun meetsNoCriteria_Then_Weak() {
        assertStrength("abc", PasswordStrength.WEAK)
    }

    private fun assertStrength(password: String, expStr: PasswordStrength) {
        val result = meter.meter(password)
        assertThat(result).isEqualTo(expStr)
    }
}
