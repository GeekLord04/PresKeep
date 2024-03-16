package com.geekster.preskeep.models

data class UserRequest (
    val uniqueId : String,
    val phoneNo : String,
    val name : String? = null,
    val gender : String? = null
)