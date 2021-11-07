package com.wafflestudio.assignment3skeleton.repository

import com.wafflestudio.assignment3skeleton.db.MemberDao
import com.wafflestudio.assignment3skeleton.network.MemberService

// TODO
class MemberRepository constructor(
    private val memberDao: MemberDao,
    private val memberService: MemberService
    ) {

    // TODO : Complete MemberRepository

    companion object {
        @Volatile
        private var INSTANCE: MemberRepository? = null

        @JvmStatic
        fun getInstance(memberDao: MemberDao, memberService: MemberService) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MemberRepository(memberDao, memberService).also { INSTANCE = it }
            }
    }
}
