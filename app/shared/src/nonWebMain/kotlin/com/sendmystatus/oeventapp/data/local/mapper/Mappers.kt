package com.sendmystatus.oeventapp.data.local.mapper

import com.sendmystatus.oeventapp.data.local.entities.*
import com.sendmystatus.oeventapp.data.model.event.*
import com.sendmystatus.oeventapp.data.model.user.User

fun AttendeeEntity.toDomain() = AttendeeRegistrationToEvent(
    id = id,
    numberOfGuest = numberOfGuest,
    price = price,
    guests = guests,
    status = status,
    eventId = eventId,
    enrolledTimestamp = enrolledTimestamp,
    cancelledTimestamp = cancelledTimestamp,
    userId = userId
)

fun AttendeeRegistrationToEvent.toEntity() = AttendeeEntity(
    id = id,
    numberOfGuest = numberOfGuest,
    price = price,
    guests = guests,
    status = status,
    eventId = eventId,
    enrolledTimestamp = enrolledTimestamp,
    cancelledTimestamp = cancelledTimestamp,
    userId = userId
)

fun EventEntity.toDomain() = Event(
    id = id,
    name = name,
    description = description,
    type = type,
    icon = icon,
    isPublic = isPublic,
    startTimestamp = startTimestamp,
    endTimestamp = endTimestamp,
    location = location,
    venueName = venueName
)

fun Event.toEntity() = EventEntity(
    id = id,
    name = name,
    description = description,
    type = type,
    icon = icon,
    isPublic = isPublic,
    startTimestamp = startTimestamp,
    endTimestamp = endTimestamp,
    location = location,
    venueName = venueName
)

fun EventTemplateEntity.toDomain() = EventTemplate(
    id = id,
    name = name,
    description = description,
    type = type,
    icon = icon
)

fun EventTemplate.toEntity() = EventTemplateEntity(
    id = id,
    name = name,
    description = description,
    type = type,
    icon = icon
)

fun EventSettingEntity.toDomain() = EventSetting(
    id = id,
    eventId = eventId,
    isFree = isFree,
    price = price,
    isOnline = isOnline,
    currency = currency,
    tokenPrefix = tokenPrefix,
    capacity = capacity,
    status = status,
    images = images,
    modifyTimestamp = modifyTimestamp,
    createdTimestamp = createdTimestamp
)

fun EventSetting.toEntity() = EventSettingEntity(
    id = id,
    eventId = eventId,
    isFree = isFree,
    price = price,
    isOnline = isOnline,
    currency = currency,
    tokenPrefix = tokenPrefix,
    capacity = capacity,
    status = status,
    images = images,
    modifyTimestamp = modifyTimestamp,
    createdTimestamp = createdTimestamp
)

fun MerchantEntity.toDomain() = Merchant(
    id = id,
    name = name,
    description = description,
    logo = logo,
    contactPersonName = contactPersonName,
    contactPersonEmail = contactPersonEmail,
    contactPersonPhone = contactPersonPhone,
    address = address,
    website = website,
    status = status,
    createdTimestamp = createdTimestamp,
    modifiedTimestamp = modifiedTimestamp
)

fun Merchant.toEntity() = MerchantEntity(
    id = id,
    name = name,
    description = description,
    logo = logo,
    contactPersonName = contactPersonName,
    contactPersonEmail = contactPersonEmail,
    contactPersonPhone = contactPersonPhone,
    address = address,
    website = website,
    status = status,
    createdTimestamp = createdTimestamp,
    modifiedTimestamp = modifiedTimestamp
)

fun MerchantEventCatalogEntity.toDomain() = MerchantEventCatalog(
    id = id,
    eventId = eventId,
    merchantId = merchantId,
    name = name,
    description = description,
    price = price,
    status = status,
    createdTimestamp = createdTimestamp,
    modifiedTimestamp = modifiedTimestamp
)

fun MerchantEventCatalog.toEntity() = MerchantEventCatalogEntity(
    id = id,
    eventId = eventId,
    merchantId = merchantId,
    name = name,
    description = description,
    price = price,
    status = status,
    createdTimestamp = createdTimestamp,
    modifiedTimestamp = modifiedTimestamp
)

fun UserEntity.toDomain() = User(
    id = id,
    createdDate = createdDate,
    status = status
)

fun User.toEntity() = UserEntity(
    id = id,
    createdDate = createdDate,
    status = status
)
