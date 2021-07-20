package tdd.bright.expiry_date_calculator

import java.time.LocalDate

/**
 *  Created by eastbright on 2021/07/20
 */
data class PayData(
    val billingDate: LocalDate,
    val payAmount: Int,
    val firstBillingDate: LocalDate? = null
)
