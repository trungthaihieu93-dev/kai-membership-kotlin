package com.kardia.membership.core.exception

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {
    object NetworkConnection : Failure()
    class ServerError(val error: String, val errorCode: Int) : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}
