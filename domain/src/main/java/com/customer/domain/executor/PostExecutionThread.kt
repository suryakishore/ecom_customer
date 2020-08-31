package com.customer.domain.executor

import io.reactivex.Scheduler

/**
 * @author 3Embed
 *
 * used for Scheduler
 *
 * @since 1.0 (23-Aug-2019)
 */
interface PostExecutionThread {
    val scheduler: Scheduler
}