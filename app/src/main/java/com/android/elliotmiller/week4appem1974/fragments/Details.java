package com.android.elliotmiller.week4appem1974.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.elliotmiller.week4appem1974.R;
import com.android.elliotmiller.week4appem1974.model.DatabaseHandler;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsInterface} interface
 * to handle interaction events.
 * Use the {@link Details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Details extends Fragment implements View.OnClickListener {
    public static final String ACTION_ADD = "add_new_details";
    public static final String ACTION_UPDATE = "update_details";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ACTION_TYPE = "ACTION_TYPE";
    private static final String DETAIL_ID = "DETAIL_ID";
    private static final String DETAIL_FIRST_NAME = "DETAIL_FIRST_NAME";
    private static final String DETAIL_LAST_NAME = "DETAIL_LAST_NAME";
    private static final String DETAIL_MAKE = "DETAIL_MAKE";
    private static final String DETAIL_MODEL = "DETAIL_MODEL";
    private static final String DETAIL_COST = "DETAIL_COST";


    // TODO: Rename and change types of parameters
    private String detailId;
    private String actionType;

    private DetailsInterface mListener;
    private EditText etFirstName, etLastName, etCarMake, etCarModel, etCarCost;
    private TextView tvId;

    public Details() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param aType ActionType.
     * @return A new instance of fragment Details.
     */
    public static Details newInstance(String aType) {
        Details fragment = new Details();
        Bundle args = new Bundle();
        args.putString(ACTION_TYPE, aType);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param aType ActionType.
     * @param id Detail Id.
     * @return A new instance of fragment Details.
     */
    public static Details newInstance(
            String aType,
            String id,
            String fName,
            String lName,
            String make,
            String model,
            String cost
            ) {
        Details fragment = new Details();
        Bundle args = new Bundle();
        args.putString(ACTION_TYPE, aType);
        args.putString(DETAIL_ID, id);
        args.putString(DETAIL_FIRST_NAME, fName);
        args.putString(DETAIL_LAST_NAME, lName);
        args.putString(DETAIL_MAKE, make);
        args.putString(DETAIL_MODEL, model);
        args.putString(DETAIL_COST, cost);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionType = getArguments().getString(ACTION_TYPE);
        if (actionType == ACTION_UPDATE) {
            detailId = getArguments().getString(DETAIL_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        tvId = view.findViewById(R.id.tv_id);
        etCarCost = view.findViewById(R.id.et_car_cost);
        etCarMake = view.findViewById(R.id.et_car_make);
        etCarModel = view.findViewById(R.id.et_car_model);
        etFirstName = view.findViewById(R.id.et_first_name);
        etLastName = view.findViewById(R.id.et_last_name);
        if (this.actionType == ACTION_UPDATE) {
            tvId.setVisibility(View.VISIBLE);
            Bundle args = getArguments();
            tvId.setText("ID :" + args.getString(DETAIL_ID));
            ((Button)view.findViewById(R.id.btn_add_update)).setText(getString(R.string.udpate));
            etCarCost.setText(args.getString(DETAIL_COST));
            etCarMake.setText(args.getString(DETAIL_MAKE));
            etCarModel.setText(args.getString(DETAIL_MODEL));
            etFirstName.setText(args.getString(DETAIL_FIRST_NAME));
            etLastName.setText(args.getString(DETAIL_LAST_NAME));
            view.findViewById(R.id.btn_delete).setVisibility(View.VISIBLE);
        }

        view.findViewById(R.id.btn_delete).setOnClickListener(this);
        view.findViewById(R.id.btn_add_update).setOnClickListener(this);
        view.findViewById(R.id.btn_view).setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DetailsInterface) {
            mListener = (DetailsInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DetailsInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        DatabaseHandler db = new DatabaseHandler(getContext());
        switch (view.getId()) {
            case R.id.btn_add_update: {
                if (!inputsValid()) {
                    return;
                }
                ContentValues cv = new ContentValues();
                cv.put(DatabaseHandler.COLUMN_FIRST_NAME, etFirstName.getText().toString());
                cv.put(DatabaseHandler.COLUMN_LAST_NAME, etLastName.getText().toString());
                cv.put(DatabaseHandler.COLUMN_MAKE, etCarMake.getText().toString());
                cv.put(DatabaseHandler.COLUMN_MODEL, etCarModel.getText().toString());
                cv.put(DatabaseHandler.COLUMN_COST, etCarCost.getText().toString());
                if(this.actionType == ACTION_UPDATE) {
                    boolean result = db.updateUser(getArguments().getString(DETAIL_ID), cv);
                    if (result){
                            Toast.makeText(getContext(), "Update Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    boolean result = db.addUser(cv);
                    if (result ) {
                        Toast.makeText(getContext(), "User Added Successfully", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStack();
                    } else {
                        Toast.makeText(getContext(), "Add User Failed", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
            case R.id.btn_delete: {
                boolean result = db.deleteUser(getArguments().getString(DETAIL_ID));
                if (result) {
                    Toast.makeText(getContext(), "User Deleted Successfully", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), "Failed To Delete User", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.btn_view: {
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            }
            default: {
                // Do Nothing yet
            }
        }
    }

    private boolean inputsValid() {
        boolean valid = true;
        if (TextUtils.isEmpty(etFirstName.getText().toString())) {
            ((TextInputLayout)this.getView().getRootView().findViewById(R.id.til_f_n)).setError("Required!");
            valid = false;
        }
        if (TextUtils.isEmpty(etLastName.getText().toString())) {
            ((TextInputLayout)this.getView().getRootView().findViewById(R.id.til_l_n)).setError("Required!");
            valid = false;
        }
        if (TextUtils.isEmpty(etCarMake.getText().toString())) {
            ((TextInputLayout)this.getView().getRootView().findViewById(R.id.til_make)).setError("Required!");
            valid = false;
        }
        if (TextUtils.isEmpty(etCarModel.getText().toString())) {
            ((TextInputLayout)this.getView().getRootView().findViewById(R.id.til_model)).setError("Required!");
            valid = false;
        }
        if (TextUtils.isEmpty(etCarCost.getText().toString())) {
            ((TextInputLayout)this.getView().getRootView().findViewById(R.id.til_cost)).setError("Required!");
            valid = false;
        }
        return valid;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface DetailsInterface {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
