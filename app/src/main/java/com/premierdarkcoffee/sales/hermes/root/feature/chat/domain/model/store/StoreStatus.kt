package com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.store.StoreStatusEntity
import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.store.StoreStatusDto

data class StoreStatus(val isActive: Boolean,
                       val isVerified: Boolean,
                       val isPromoted: Boolean,
                       val isSuspended: Boolean,
                       val isClosed: Boolean,
                       val isPendingApproval: Boolean,
                       val isUnderReview: Boolean,
                       val isOutOfStock: Boolean,
                       val isOnSale: Boolean) {

    fun toStoreStatusDto(): StoreStatusDto {
        return StoreStatusDto(isActive = this.isActive,
                              isVerified = this.isVerified,
                              isPromoted = this.isPromoted,
                              isSuspended = this.isSuspended,
                              isClosed = this.isClosed,
                              isPendingApproval = this.isPendingApproval,
                              isUnderReview = this.isUnderReview,
                              isOutOfStock = this.isOutOfStock,
                              isOnSale = this.isOnSale)
    }

    fun toStoreStatusEntity(): StoreStatusEntity {
        return StoreStatusEntity(isActive = this.isActive,
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
