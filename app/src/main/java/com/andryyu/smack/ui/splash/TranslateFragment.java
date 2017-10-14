package com.andryyu.smack.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.andryyu.smack.R;
import com.andryyu.smack.ui.LoginActivity;

public class TranslateFragment extends Fragment {

	private int pageIndex;
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		Bundle bundle = getArguments();
		int layoutId = bundle.getInt("layoutId");
		pageIndex = bundle.getInt("pageIndex");
		
		View view = inflater.inflate(layoutId, null);
		view.setTag(pageIndex);

		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if(pageIndex==2){
			Button btnEnter = (Button)view.findViewById(R.id.btn_enter);
			btnEnter.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getActivity(), LoginActivity.class));
				}
			});
		}
	}
}
