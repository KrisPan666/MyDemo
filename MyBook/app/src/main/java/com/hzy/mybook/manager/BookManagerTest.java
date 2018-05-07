package com.hzy.mybook.manager;

import android.test.AndroidTestCase;
import android.util.Log;

import com.hzy.mybook.model.Book;

import java.util.ArrayList;
import java.util.Date;

public class BookManagerTest extends AndroidTestCase {
	private static final String TAG = "MyBookActivity";
	private BookManager bookManager;

	/**
	 * 调用test方法之前自动调用
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		bookManager = new BookManager(getContext());
	}

	public void testSave() {
		for (int i = 0; i < 20; i++) {
			Book book = new Book("test", "/mnt/abc.txt", new Date(), 1, "0.01%");
			Log.d(TAG, bookManager.save(book).toString());
		}
	}

	public void testGet() {
		Book book = bookManager.get(1L);
		Log.d(TAG, book.toString());
	}

	public void testUpdate() {
		String updateName = "射白鹿";
		Book book = bookManager.get(1L);
		book.setName(updateName);
		bookManager.update(book);

		book = bookManager.get(1L);// 再次获取
		Log.d(TAG, book.toString());
		assertEquals(updateName, book.getName());
	}

	public void testGetAll() {
		ArrayList<Book> books = bookManager.getAll();
		for (Book book : books) {
			Log.d(TAG, book.toString());
		}
		// assertEquals(1, books.size());
	}

	public void testDelete() {
		bookManager.delete(2L);
	}
	
	public void testDeleteAll() {
		ArrayList<Book> books = bookManager.getAll();
		for (Book book : books) {
			bookManager.delete(book.getId());
		}
	}
}
