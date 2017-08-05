package com.android.elliotmiller.week4appem1974.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.elliotmiller.week4appem1974.R;

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

    // TODO: Rename and change types of parameters
    private long detailId;
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
     * @param id Detail Id.
     * @return A new instance of fragment Details.
     */
    public static Details newInstance(String aType, long id) {
        Details fragment = new Details();
        Bundle args = new Bundle();
        args.putString(ACTION_TYPE, aType);
        args.putLong(DETAIL_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            actionType = getArguments().getString(ACTION_TYPE);
            detailId = getArguments().getLong(DETAIL_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        tvId = view.findViewById(R.id.tv_id);
        if (this.actionType == ACTION_UPDATE) {
            tvId.setVisibility(View.VISIBLE);
        }
        etCarCost = view.findViewById(R.id.et_car_cost);
        etCarMake = view.findViewById(R.id.et_car_make);
        etCarModel = view.findViewById(R.id.et_car_model);
        etFirstName = view.findViewById(R.id.et_first_name);
        etLastName = view.findViewById(R.id.et_last_name);
        view.findViewById(R.id.btn_delete).setOnClickListener(this);
        view.findViewById(R.id.btn_add_update).setOnClickListener(this);
        view.findViewById(R.id.btn_view).setOnClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        switch (view.getId()) {
            case R.id.btn_add_update: {
                break;
            }
            case R.id.btn_delete: {
                break;
            }
            case R.id.btn_view: {
                break;
            }
            default: {
                // Do Nothing yet
            }
        }
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
