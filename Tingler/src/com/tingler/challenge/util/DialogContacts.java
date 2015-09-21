package com.tingler.challenge.util;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.tingler.challenge.R;
import com.tingler.challenge.adapter.ContactsAdapter;

public class DialogContacts {
	EditText etxt_search;
	ListView lv_contacts;
	Button btn_select, btn_cancel;
	Context context;
	ContactsAdapter contactsAdapter;
	ArrayList<ContactItem> contactsArrayList;;

	public DialogContacts(Context context) {
		this.context = context;

	}

	public void contactList(String title) {
		Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_contact);
	//	dialog.setTitle(title);
		
		init(dialog);
		dialog.show();

	}

	public void init(Dialog dialog) {
		contactsArrayList = new ArrayList<ContactItem>();
		etxt_search = (EditText) dialog.findViewById(R.id.etxt_search);
		lv_contacts = (ListView) dialog.findViewById(R.id.lv_contacts);
		new ContactsAsy().execute();
		etxt_search.addTextChangedListener(watcher());

	}

	public class ContactsAsy extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Cursor cursor = context.getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					null, null, null);
			while (cursor.moveToNext()) {
				
				String name = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

				String phoneNumber = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				ContactItem contactItem = new ContactItem(name, phoneNumber,
						"level");
                
				if(!contactsArrayList.contains(contactItem))
				{contactsArrayList.add(contactItem);
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			contactsAdapter = new ContactsAdapter(context, contactsArrayList);
			lv_contacts.setAdapter(contactsAdapter);
		}

	}

	public TextWatcher watcher() {
		TextWatcher textWatcher = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String text = etxt_search.getText().toString()
						.toLowerCase(Locale.getDefault());
				contactsAdapter.filter(text);
			}
		};
		return textWatcher;
	}

}