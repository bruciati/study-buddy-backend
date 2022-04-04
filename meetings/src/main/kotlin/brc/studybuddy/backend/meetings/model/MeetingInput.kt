package brc.studybuddy.backend.meetings.model

import brc.studybuddy.model.Meeting



data class MeetingInput(
     val groupId: Long ? = null,
     val name: String ? = null,
     val dateTime: Long ? = null,
     val type: Meeting.Type ? = null,
     val location: String ? = null,
)
{
     fun toMeeting(): Meeting = Meeting(
          groupId = this.groupId!!,
          name = this.name!!,
          dateTime = this.dateTime!!,
          type = this.type!!,
          location = this.location!!,
     )

     fun updateMeeting(meeting: Meeting): Meeting = Meeting(
          meeting.id,
          meeting.groupId,
          this.name ?: meeting.name,
          this.dateTime ?: meeting.dateTime,
          this.type ?: meeting.type,
          this.location ?: meeting.location
     )

}


