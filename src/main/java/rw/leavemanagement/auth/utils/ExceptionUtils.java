package rw.leavemanagement.auth.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import rw.leavemanagement.auth.dto.response.ApiResponse;
import rw.leavemanagement.auth.exceptions.BadRequestException;
import rw.leavemanagement.auth.exceptions.ConflictException;
import rw.leavemanagement.auth.exceptions.InternalServerErrorException;
import rw.leavemanagement.auth.exceptions.NotFoundException;


public class ExceptionUtils {
    // Method to handle exceptions and return a ResponseEntity with an ApiResponse
    // object
    public static ResponseEntity<ApiResponse<Object>> handleResponseException(Exception e) {
        if (e instanceof NotFoundException) {
            // If the exception is of type NotFoundException, return a ResponseEntity with a
            // NOT_FOUND status
            return ApiResponse.error(
                    e.getMessage(),
                    HttpStatus.NOT_FOUND,
                    null);
        }
        if (e instanceof ConflictException) {
            // If the exception is of type ConflictException, return a ResponseEntity with a
            // CONFLICT status
            return ApiResponse.error(
                    e.getMessage(),
                    HttpStatus.CONFLICT,
                    null);
        } else if (e instanceof InternalServerErrorException) {
            // If the exception is of type InternalServerErrorException, return a
            // ResponseEntity with an INTERNAL_SERVER_ERROR status
            return ApiResponse.error(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        } else if (e instanceof BadRequestException || e instanceof InternalAuthenticationServiceException
                || e instanceof BadCredentialsException || e instanceof AccessDeniedException) {
            // If the exception is of type BadRequestException,
            // InternalAuthenticationServiceException, BadCredentialsException, or
            // AccessDeniedException, return a ResponseEntity with a BAD_REQUEST status
            return ApiResponse.error(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST,
                    null);
        } else {
            // For any other type of exception, return a ResponseEntity with an
            // INTERNAL_SERVER_ERROR status
            return ApiResponse.error(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }
}
