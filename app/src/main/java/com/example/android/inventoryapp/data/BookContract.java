package com.example.android.inventoryapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * API Contract for the Inventory Tracker app.
 */
public final class BookContract {

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.inventoryapp";
    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.inventoryapp/books/ is a valid path for
     * looking at book data. content://com.example.android.inventoryapp/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_BOOKS = "books";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private BookContract() {
    }

    /**
     * Inner class that defines constant values for the books inventory database table.
     * Each entry in the table represents a single book.
     */
    public static class BookEntry implements BaseColumns {
        /**
         * The content URI to access the book data in the provider
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of books.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single book.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        /**
         * Name of database table for books
         */
        public static final String TABLE_NAME = "books";
        public static final String _ID = BaseColumns._ID;

        /**
         * Title of the book.
         * Type: TEXT
         */
        public static final String COLUMN_BOOK_TITLE = "title";
        /**
         * Author of the book.
         * Type: TEXT
         */
        public static final String COLUMN_BOOK_AUTHOR = "author";
        /**
         * Type of the book.
         * The only possible values are {@link #TYPE_UNKNOWN}, {@link #TYPE_NOVEL},
         * {@link #TYPE_TECHNICAL}.
         * Type: TEXT
         */
        public static final String COLUMN_BOOK_TYPE = "type";
        /**
         * Book's price.
         * Type: INTEGER
         */
        public static final String COLUMN_BOOK_PRICE = "price";
        /**
         * Quantity of books in stock.
         * Type: INTEGER
         */
        public static final String COLUMN_BOOK_QUANTITY = "quantity";
        /**
         * Book's supplier.
         * Type: INTEGER
         */
        public static final String COLUMN_BOOK_SUPPLIER = "supplier";
        /**
         * Supplier phone number.
         * Type: String
         */
        public static final String COLUMN_BOOK_SUPPLIER_PHONE = "telephone_supplier";
        /**
         * Supplier's email.
         * Type: String
         */
        public static final String COLUMN_BOOK_SUPPLIER_EMAIL = "email_supplier";
        /**
         * Book's image.
         * Type: String
         */
        public final static String COLUMN_BOOK_IMAGE = "image";

        /**
         * Possible values for the type of the book.
         */
        public static final int TYPE_UNKNOWN = 0;
        public static final int TYPE_NOVEL = 1;
        public static final int TYPE_TECHNICAL = 2;

        /**
         * Returns whether or not the given type is {@link #TYPE_UNKNOWN}, {@link #TYPE_NOVEL},
         * or {@link #TYPE_TECHNICAL}.
         */
        public static boolean isValidType(int type) {
            if (type == TYPE_UNKNOWN || type == TYPE_NOVEL || type == TYPE_TECHNICAL) {
                return true;
            }
            return false;
        }
    }
}