package com.txt1stparkuor.Ecommerce.constant;

public class ErrorMessage {


    private ErrorMessage() {}
    public static final String ERR_EXCEPTION_GENERAL = "Something went wrong";
    public static final String UNAUTHORIZED = "Sorry, you need to provide authentication credentials to perform this action";
    public static final String ERR_DUPLICATE = "%s with value %s already exists.";
    public static final String FORBIDDEN = "Sorry, you do not have permission to perform this action";
    public static final String INVALID_IMAGE_FILE="Only PNG, JPG, JPEG, WEBP or GIF images are allowed";
    public static final String INVALID_JSON_FORMAT = "Invalid JSON format. Please check your input.";
    public static final String TO_MANY_REQUEST = "You have sent too many requests. Please try again later.";
    public static final String INCORRECT_PASSWORD = "Incorrect password";
    public static final String NOT_FOUND = "Resource not found";
    public static final String INVALID_SORT_FIELD = "Invalid sort field '%s'. Allowed fields are: %s.";
    public static final String HTTP_METHOD_NOT_SUPPORTED = "This HTTP method is not supported.";

    // Validation Errors (DTOs)
    public static class Validation {
        public static final String NOT_BLANK = "Cannot be blank";
        public static final String INVALID_FORMAT_PASSWORD = "Password is not strong enough (at least 6 characters, including letters and numbers)";
        public static final String NOT_NULL= "This field is required";
        public static final String NOT_EMPTY="This field cannot be empty";
        public static final String INVALID_FORMAT_FIELD="Invalid format";
        public static final String MUST_IN_PAST = "Date must be in the past";
        public static final String MUST_IN_FUTURE = "Date must be in the future";
        public static final String POSITIVE="Number must be > 0";
        public static final String INVALID_ENUM_VALUE = "Invalid value '%s'. Accepted values are: %s";
        public static final String INVALID_TYPE_VALUE = "Invalid value '%s' for this field.";
        public static final String MUST_BE_JSON_STRING = "This field must be a valid JSON string.";
        public static final String INVALID_SCORE = "Invalid rating. Must be between 1 and 5.";
    }

    public static class Auth {
        public static final String ERR_INCORRECT_CREDENTIALS = "Incorrect username or password";
        public static final String ERR_INVALID_REFRESH_TOKEN = "Invalid refresh token";

    }

    public static class User {
        public static final String ERR_NOT_FOUND_ID = "User not found with id: %s";
        public static final String NOT_FOUND_ONE_OR_MORE = "User not found with id: %s";
        public static final String USERNAME_NOT_FOUND="User not found with username" ;
        public static final String INVALID_ROLE = "Invalid role";
        public static final String ERR_NOT_FOUND_USERNAME = "User not found with username: %s";
    }

    // File Handling Errors
    public static class File {
        public static final String FILE_IS_EMPTY = "File cannot be empty or missing.";
        public static final String INVALID_IMAGE_TYPE = "Invalid file type. Only image files (png, jpg, jpeg, gif...) are allowed.";
        public static final String FILE_TOO_LARGE = "File is too large. Maximum allowed size is 5MB.";
        public static final String UPLOAD_FAILED = "File upload failed. Please try again.";
        public static final String DESTROY_FAILED = "File deletion failed.";
        public static final String INVALID_CLOUDINARY_URL = "Invalid Cloudinary image URL.";
    }

    public static class Product {
        public static final String ERR_NOT_ENOUGH_STOCK = "Product does not have enough stock.";
        public static final String ERR_INVALID_PRICE_RANGE = "Minimum price cannot be greater than maximum price.";
    }

    public static class Cart {
        public static final String ERR_CART_EMPTY = "Cart is empty";
        public static final String ERR_ITEM_NOT_IN_CART = "One or more items not found in cart";
    }

    public static class Order {
        public static final String ERR_CANCEL_ORDER = "Order cannot be cancelled in its current state.";
    }
}
