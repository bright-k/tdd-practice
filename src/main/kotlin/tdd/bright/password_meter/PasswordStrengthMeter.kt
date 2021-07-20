package tdd.bright.password_meter

/**
 *  Created by eastbright on 2021/07/20
 */
class PasswordStrengthMeter {
    fun meter(s: String): PasswordStrength {
        if (s.isEmpty()) return PasswordStrength.INVALID

        var metCounts = getMetCriteriaCounts(s)

        if (metCounts <= 1) return PasswordStrength.WEAK
        if (2 == metCounts) return PasswordStrength.NORMAL

        return PasswordStrength.STRONG
    }

    private fun getMetCriteriaCounts(s: String): Int {
        var metCounts = 0
        if (8 <= s.length) ++metCounts
        if (meetsContainingNumberCriteria(s)) ++metCounts
        if (meetsContainingUppercaseCriteria(s)) ++metCounts

        return metCounts
    }

    private fun meetsContainingUppercaseCriteria(s: String): Boolean {
        s.forEach {
            if (it in 'A'..'Z') {
                return true
            }
        }
        return false
    }

    private fun meetsContainingNumberCriteria(s: String): Boolean {
        s.forEach {
            if (it in '0'..'9') {
                return true
            }
        }
        return false
    }
}
