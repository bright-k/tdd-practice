package tdd.bright.expiry_date_calculator

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate

/**
 *  Created by eastbright on 2021/07/20
 */
class ExpiryDateCalculatorTest {
    @DisplayName("만원을 납부하면 한 달 뒤가 만료일이 됨")
    @Test
    fun pay_10000_then_expiry_date_one_month_later() {
        assertExpiryDate(
            PayData(LocalDate.of(2019, 3, 1), 10_000),
            LocalDate.of(2019, 4, 1)
        )
        assertExpiryDate(
            PayData(LocalDate.of(2019, 4, 1), 10_000),
            LocalDate.of(2019, 5, 1)
        )
    }

    @DisplayName("납부일과 한단 뒤 일자가 같지 않음")
    @Test
    fun different_billing_day_and_expiry_day() {
        assertExpiryDate(
            PayData(LocalDate.of(2019, 1, 31), 10_000),
            LocalDate.of(2019, 2, 28)
        )
        assertExpiryDate(
            PayData(LocalDate.of(2019, 5, 31), 10_000),
            LocalDate.of(2019, 6, 30)
        )
        assertExpiryDate(
            PayData(LocalDate.of(2020, 1, 31), 10_000),
            LocalDate.of(2020, 2, 29)
        )
    }

    @DisplayName("첫 납부일 일자와 만료일 납부일 일자가 같지 않을 때")
    @Test
    fun different_first_billing_day_and_expiry_billing_day() {
        assertExpiryDate(
            PayData(
                firstBillingDate = LocalDate.of(2019, 1, 31),
                billingDate = LocalDate.of(2019, 2, 28),
                payAmount = 10_000,
            ),
            LocalDate.of(2019, 3, 31)
        )

        assertExpiryDate(
            PayData(
                firstBillingDate = LocalDate.of(2019, 1, 30),
                billingDate = LocalDate.of(2019, 2, 28),
                payAmount = 10_000,
            ),
            LocalDate.of(2019, 3, 30)
        )

        assertExpiryDate(
            PayData(
                firstBillingDate = LocalDate.of(2019, 5, 31),
                billingDate = LocalDate.of(2019, 6, 30),
                payAmount = 10_000,
            ),
            LocalDate.of(2019, 7, 31)
        )
    }

    @DisplayName("2만원 이상 납부 시 비례해서 만료일이 계산된다")
    @Test
    fun pay_over_20000_expiry_date_proportionately_calculated() {
        assertExpiryDate(
            PayData(
                billingDate = LocalDate.of(2019, 3, 1),
                payAmount = 20_000,
            ),
            LocalDate.of(2019, 5, 1)
        )

        assertExpiryDate(
            PayData(
                billingDate = LocalDate.of(2019, 3, 1),
                payAmount = 30_000,
            ),
            LocalDate.of(2019, 6, 1)
        )

        assertExpiryDate(
            PayData(
                billingDate = LocalDate.of(2019, 3, 1),
                payAmount = 50_000,
            ),
            LocalDate.of(2019, 8, 1)
        )
    }

    @DisplayName("첫 납부일과 만료일 일자가 다를 때 2만원 이상 납부")
    @Test
    fun pay_over_20000_different_first_billing_day_and_expiry_billing_day() {
        assertExpiryDate(
            PayData(
                firstBillingDate = LocalDate.of(2019, 1, 31),
                billingDate = LocalDate.of(2019, 2, 28),
                payAmount = 20_000,
            ),
            LocalDate.of(2019, 4, 30)
        )

        assertExpiryDate(
            PayData(
                firstBillingDate = LocalDate.of(2019, 3, 31),
                billingDate = LocalDate.of(2019, 4, 30),
                payAmount = 30_000,
            ),
            LocalDate.of(2019, 7, 31)
        )
    }

    @DisplayName("10만원 납부 시 1년 제공")
    @Test
    fun pay_100_000_one_year_expiry_date() {
        assertExpiryDate(
            PayData(
                billingDate = LocalDate.of(2019, 1, 28),
                payAmount = 100_000,
            ),
            LocalDate.of(2020, 1, 28)
        )
    }

    @DisplayName("10만원 이상을 납부하는 경우")
    @Test
    fun pay_over_100_000() {
        assertExpiryDate(
            PayData(
                billingDate = LocalDate.of(2019, 1, 28),
                payAmount = 110_000,
            ),
            LocalDate.of(2020, 2, 28)
        )
        assertExpiryDate(
            PayData(
                billingDate = LocalDate.of(2019, 1, 28),
                payAmount = 120_000,
            ),
            LocalDate.of(2020, 3, 28)
        )
    }

    private fun assertExpiryDate(payData: PayData, expectedExpiryDate: LocalDate) {
        val cal = ExpiryDateCalculator()
        val expiryDate = cal.calculateExpiryDate(payData)
        assertThat(expiryDate).isEqualTo(expectedExpiryDate)
    }
}
