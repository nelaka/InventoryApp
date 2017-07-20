package com.example.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.BookContract.BookEntry;

import butterknife.BindView;
import butterknife.ButterKnife;

/***
 * {@link BookCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of book data as its data source. This adapter knows
 * how to create list items for each row of book data in the {@link Cursor}.
 */

public class BookCursorAdapter extends CursorAdapter {

    BookCursorAdapter.ViewHolder holder;

    /**
     * Constructs a new {@link BookCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.book_list_item, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = view;


        holder = new ViewHolder(listItemView);
        listItemView.setTag(holder);

        int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
        int titleColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_TITLE);
        int imageColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_IMAGE);
        int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);

        // Extract properties from cursor

        String bookTitle = cursor.getString(titleColumnIndex);
        int bookPrice = cursor.getInt(priceColumnIndex);
        final int bookQuantity = cursor.getInt(quantityColumnIndex);
        String bookImage = cursor.getString(imageColumnIndex);

        // Populate fields with extracted properties
        holder.mImageView.setImageURI(Uri.parse(bookImage));
        holder.mTitleTextView.setText(bookTitle);
        holder.mQuantityTextView.setText(String.valueOf(bookQuantity));
        holder.mPriceTextView.setText("Price: " + String.valueOf(bookPrice) + "€");
        holder.mQuantityTextView.setText("Quantity: " + String.valueOf(bookQuantity));
        final int bookId = cursor.getInt(idColumnIndex);

        // Set a clickListener on sale button
        holder.mSaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri currentBookUri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, bookId);
                reduceBookQuantity(view, bookQuantity, currentBookUri);
            }
        });
    }

    private void reduceBookQuantity(View view, int quantity, Uri uri) {

        if (quantity > 0) {
            quantity--;

            ContentValues values = new ContentValues();
            values.put(BookEntry.COLUMN_BOOK_QUANTITY, quantity);
            mContext.getContentResolver().update(uri, values, null, null);
        }
        else {
            Toast.makeText(view.getContext(), "This book has no stock", Toast.LENGTH_SHORT).show();
        }
    }
    static class ViewHolder {
        @BindView(R.id.title)
        TextView mTitleTextView;
        @BindView(R.id.price)
        TextView mPriceTextView;
        @BindView(R.id.quantity)
        TextView mQuantityTextView;
        @BindView(R.id.book_image)
        ImageView mImageView;
        @BindView(R.id.button_sale)
        Button mSaleButton;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

