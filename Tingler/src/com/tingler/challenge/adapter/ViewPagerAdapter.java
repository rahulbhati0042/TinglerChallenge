package com.tingler.challenge.adapter;

import java.util.ArrayList;

import com.tingler.challenge.fragment.Challenge;
import com.tingler.challenge.fragment.Watchers;
import com.tingler.challenge.fragment.Witness;
import com.tingler.challenge.util.ProfileMemberItems;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
	
	CharSequence Titles[]; // This will Store the Titles of the Tabs which are
							// Going to be passed when ViewPagerAdapter is
							// created
	int NumbOfTabs; // Store the number of tabs, this will also be passed when
					// the ViewPagerAdapter is created

	// Build a Constructor and assign the passed Values to appropriate values in
	// the class
	public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[],
			int mNumbOfTabsumb) {
		super(fm);
		
		this.Titles = mTitles;
		this.NumbOfTabs = mNumbOfTabsumb;

	}

	// This method return the fragment for the every position in the View Pager
	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		if (position == 0) // if the position is 0 we are returning the First
							// tab
		{
			fragment = new Challenge();

		} else if (position == 1) // As we are having 2 tabs if the position is
									// now 0 it must be 1 so we are returning
									// second tab
		{
			fragment = new Witness();

		} else if (position == 2) {
			fragment = new Watchers();
		}

		return fragment;
	}

	// This method return the titles for the Tabs in the Tab Strip

	@Override
	public CharSequence getPageTitle(int position) {
		return Titles[position];
	}

	// This method return the Number of tabs for the tabs Strip

	@Override
	public int getCount() {
		return NumbOfTabs;
	}
}