package brc.studybuddy.backend.auth.exception

class DataNotFoundException(message: String) : ServiceException(message, 400) { }