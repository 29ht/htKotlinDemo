package com.example.htkotlinmvvmdemo1.repository

import com.caspar.xl.db.RoomManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *  @Create 2021/12/24.
 *  @Use
 */
object RoomRepository {
      val userDao = RoomManager.getInstance().getUserDao()

      val teacherDao = RoomManager.getInstance().getTeacherDao()

}