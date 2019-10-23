package com.temporary.demoproject.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.temporary.demoproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment {

    @BindView(R.id.content_textview)
    TextView mContentTV;
    @BindView(R.id.test_button)
    Button mTestBtn;
    @BindView(R.id.test_searchview)
    SearchView mTestSV;
    @BindView(R.id.test_edittext)
    EditText mTestET;
    /*@BindView(R.id.topbar)
    QMUITopBar mTopbar;*/
    private Unbinder mUnbinder;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        //QMUIStatusBarHelper.translucent(getActivity());
        /*mTopbar.setTitle("123");
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("wyy", "OneFragment onClick ");
            }
        });*/

        /*监听键盘输入*/
        mTestET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /*监听键盘回车键*/
        mTestET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    Toast.makeText(getContext(), textView.getText(), Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("wyy", "OneFragment onHiddenChanged " + hidden);
    }

    @OnClick(R.id.test_button)
    public void onViewClicked() {
        mContentTV.setText("this is one fragment!");
    }
}
