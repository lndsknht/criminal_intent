package com.bignerdranch.android.criminalintent;

import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class CrimeFragment extends Fragment {
	private Crime mCrime;
	private EditText mTitleField;
	private CheckBox mSolvedCheckBox;
	private Button mDateButton;
	
	public static final String EXTRA_CRIME_ID =
			"com.bignerdranch.android.criminalintent.crime_id";
	public static final String DIALOG_DATE = "date";
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID); // глава 10
		mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v = inflater.inflate(R.layout.fragment_crime, parent, false);
		mTitleField=(EditText)v.findViewById(R.id.crime_title);
		mTitleField.setText(mCrime.getTitle()); //глава 10
		
		mTitleField.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence c, int start, int before, int count) {
					mCrime.setTitle(c.toString());
					}
					public void beforeTextChanged(CharSequence c, int start, int count, int after) {
					// Здесь намеренно оставлено пустое место
					}
					public void afterTextChanged(Editable c) {
					// И здесь тоже
					}
					});
		mDateButton=(Button)v.findViewById(R.id.crime_date);
		mDateButton.setText(mCrime.getDate().toString());
		
		mDateButton.setOnClickListener(new View.OnClickListener() {			// глава 12
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				DatePickerFragment datePicker = new DatePickerFragment();
				datePicker.show(fm, DIALOG_DATE);
				
			}
		});
		
		mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
		
		mSolvedCheckBox.setChecked(mCrime.isSolved()); // глава 10
		mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				// Назначение флага раскрытия преступления
				mCrime.setSolved(isChecked);
			}
		});
		
		
		return v;
		
		
	}
	
	public static CrimeFragment newInstance(UUID crimeId)
	{
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_CRIME_ID, crimeId);
		CrimeFragment fragment = new CrimeFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	
	

}
