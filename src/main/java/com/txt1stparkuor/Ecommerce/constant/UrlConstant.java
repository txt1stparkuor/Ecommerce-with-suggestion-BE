package com.txt1stparkuor.Ecommerce.constant;

public class UrlConstant {

    public static class Auth {
        private static final String PRE_FIX = "/auth";
        public static final String LOGIN = PRE_FIX + "/login";
        public static final String REGISTER = PRE_FIX + "/register";
        public static final String REFRESH_TOKEN = PRE_FIX + "/refresh-token";

        private Auth() {
        }
    }

    public static class User {
        private static final String PRE_FIX = "/users";
        public static final String USER_COMMON = PRE_FIX;
        public static final String USER_ID = PRE_FIX + "/{id}";
        public static final String GET_CURRENT_USER = PRE_FIX + "/me";
        public static final String GET_MY_CLASSROOMS = GET_CURRENT_USER + "/classrooms";
        public static final String GET_MY_COMPETITIONS = GET_CURRENT_USER + "/competitions";
        public static final String GET_CLASS_USER = USER_ID + "/classrooms";
        public static final String GET_MY_POSTS = GET_CURRENT_USER + "/posts";
        public static final String RESET_USERS = PRE_FIX + "/reset";
        public static final String USER_RECOMMENDATIONS = PRE_FIX + "/recommendations";

        private User() {
        }
    }

    public static class Product {
        private static final String PRE_FIX = "/products";
        public static final String PRODUCT_COMMON = PRE_FIX;
        public static final String PRODUCT_ID = PRE_FIX + "/{id}";
        public static final String PRODUCT_REVIEWS = PRODUCT_ID + "/reviews";
        public static final String PRODUCT_RECOMMENDATIONS = PRODUCT_ID + "/recommendations";
        public static final String PRODUCT_RECOMMENDATIONS_HYBRID = PRODUCT_ID + "/recommendations/hybrid";


        private Product() {
        }
    }

    public static final class Image {
        public static final String PRE_FIX = "/images";
        public static final String IMAGE_COMMON=PRE_FIX;
    }

    public static class Role {
        private static final String PRE_FIX = "/roles";
        public static final String ROLE_COMMON = PRE_FIX;

        private Role() {
        }
    }

    public static class Category {
        private static final String PRE_FIX = "/categories";
        public static final String CATEGORY_COMMON = PRE_FIX;
        public static final String CATEGORIES_WITH_PRODUCTS = PRE_FIX + "/with-products";
        public static final String LEAF_CATEGORIES = PRE_FIX + "/leaf";


        private Category() {
        }
    }

    public static class Cart {
        private static final String PRE_FIX = "/cart";
        public static final String CART_COMMON = PRE_FIX;
        public static final String CART_ITEMS = PRE_FIX + "/items";
        public static final String CART_ITEM_ID = CART_ITEMS + "/{id}";

        private Cart() {
        }
    }

    public static class Order {
        private static final String PRE_FIX = "/orders";
        public static final String ORDER_COMMON = PRE_FIX;
        public static final String ORDER_ID = PRE_FIX + "/{id}";
        public static final String GET_MY_ORDERS = PRE_FIX + "/me";
        public static final String UPDATE_ORDER_STATUS = ORDER_ID + "/status";
        public static final String CANCEL_ORDER = ORDER_ID + "/cancel";


        private Order() {
        }
    }

    public static class Export {
        private static final String PRE_FIX = "/export";
        public static final String EXPORT_AMAZON_CSV = PRE_FIX + "/amazon.csv";

        private Export() {
        }
    }
}
