package tdd.bright.expiry_date_calculator

import java.time.LocalDate
import java.time.YearMonth

class ExpiryDateCalculator {
    fun calculateExpiryDate(payData: PayData): LocalDate {
        val addedMonths: Long = calcAddedDateOfUse(payData.payAmount)

        payData.firstBillingDate?.let {
            return expiryDateUsingFirstBillingDate(payData, addedMonths)
        }
        return payData.billingDate.plusMonths(addedMonths)
    }

    private fun expiryDateUsingFirstBillingDate(payData: PayData, addedMonths: Long): LocalDate {
        val candidateExp = payData.billingDate.plusMonths(addedMonths)
        val dayOfFirstBilling = payData.firstBillingDate!!.dayOfMonth
        if (dayOfFirstBilling != candidateExp.dayOfMonth) {
            val dayLenOfCandiMon = YearMonth.from(candidateExp).lengthOfMonth()
            if (dayLenOfCandiMon < payData.firstBillingDate.dayOfMonth) {
                return candidateExp.withDayOfMonth(dayLenOfCandiMon)
            }
            return candidateExp.withDayOfMonth(dayOfFirstBilling)
        } else {
            return candidateExp
        }
    }

    private fun calcAddedDateOfUse(payAmount: Int): Long {
        val years = payAmount / 100_000
        val months = (payAmount % 100_000) / 10_000
        return (12L * years) + months
    }
}
