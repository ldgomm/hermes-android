package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.store

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.StoreStatus

data class StoreStatusEntity(val isActive: Boolean,
                             val isVerified: Boolean,
                             val isPromoted: Boolean,
                             val isSuspended: Boolean,
                             val isClosed: Boolean,
                             val isPendingApproval: Boolean,
                             val isUnderReview: Boolean,
                             val isOutOfStock: Boolean,
                             val isOnSale: Boolean) {

    fun toStatus(): StoreStatus {
        return StoreStatus(isActive = this.isActive,
                           isVerified = this.isVerified,
                           isPromoted = this.isPromoted,
                           isSuspended = this.isSuspended,
                           isClosed = this.isClosed,
                           isPendingApproval = this.isPendingApproval,
                           isUnderReview = this.isUnderReview,
                           isOutOfStock = this.isOutOfStock,
                           isOnSale = this.isOnSale)
    }
}
