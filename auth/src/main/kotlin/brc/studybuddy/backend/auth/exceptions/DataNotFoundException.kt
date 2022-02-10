package brc.studybuddy.backend.auth.exceptions

class DataNotFoundException(message: String) : ServiceException(message, 400) { }