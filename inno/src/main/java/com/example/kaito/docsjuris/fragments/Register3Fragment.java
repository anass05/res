package com.example.kaito.docsjuris.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.kaito.docsjuris.R;
import com.example.kaito.docsjuris.RegisterActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.util.Calendar;

/**
 * Created by KaiTo on 4/28/2018.
 */

public class Register3Fragment extends Fragment implements DatePickerDialog.OnDateSetListener, RegisterActivity.Connector {

    private Context context;
    private View rootView;

    private static Register3Fragment fragment;
    private EditText date, filechoose, cinpass, villeNaissance;
    private RadioGroup radioGroup;
    private RadioButton radioCin, radioPassport;
    private TextInputLayout cinPassHint;

    private FragmentActivity myContext;

    public static Register3Fragment newInstance() {
        if (fragment == null)
            fragment = new Register3Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fr_register3, container, false);
        context = rootView.getContext();

        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
        date = rootView.findViewById(R.id.date);
        filechoose = rootView.findViewById(R.id.file);
        date.setOnClickListener(e -> datePickerDialog.show(myContext.getFragmentManager(), "Datepickerdialog"));
        filechoose.setOnClickListener(e-> {
            ((RegisterActivity) getActivity()).uploadDocument = true;
            ((RegisterActivity) getActivity()).imageBrowse();
        });
        villeNaissance = rootView.findViewById(R.id.villnaissance);
        cinpass = rootView.findViewById(R.id.cinpass);
        radioGroup = rootView.findViewById(R.id.radio_group);
        radioCin = rootView.findViewById(R.id.radio_cin);
        radioPassport = rootView.findViewById(R.id.radio_passport);
        cinPassHint = rootView.findViewById(R.id.cinpasslayout);

        cinpass.setHint("");
        cinPassHint.setHint("Numero de la carte d'identité");
        radioCin.setOnCheckedChangeListener((compoundButton, b) -> {
            if (radioCin.isChecked()) {
                ((RegisterActivity) getActivity()).isCinChecked = true;
                cinpass.setHint("Numero de la carte d'identité");
                cinPassHint.setHint("Numero de la carte d'identité");
            } else {
                ((RegisterActivity) getActivity()).isCinChecked = true;
                cinpass.setHint("Numero du passport");
                cinPassHint.setHint("Numero du passport");
            }
        });

        cinpass.setOnFocusChangeListener((view, b) -> {
            if (!b)
                cinpass.setHint("");

        });
        return rootView;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        date.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
    }

    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void getData() {
        ((RegisterActivity) getActivity()).userData.put(radioCin.isChecked() ? "ncin" : "npassport", cinpass.getText().toString());
        ((RegisterActivity) getActivity()).userData.put("ville_naissance", villeNaissance.getText().toString());
        ((RegisterActivity) getActivity()).userData.put("date_naissance", date.getText().toString());
//        ((RegisterActivity) getActivity()).userData.put("docLink", filechoose.getText().toString());
    }

    @Override
    public void updateImage(Bitmap bitmap,String path) {
        filechoose.setText(path);
    }

    @Override
    public void updateRadio(boolean isCinChecked) {
        radioCin.setChecked(isCinChecked);
    }


    @Override
    public boolean validateData() {
        if (cinpass.getText().toString().length() < 4) {
            String type = radioCin.isChecked() ? "CIN" : "passport";
            toast("Vous devez fournin un numero de "+type+"  valide");
            return false;
        }
        if (date.getText().toString().length() < 4) {
            toast("Vous devez fournin une date de naissance valide");
            return false;
        }
        if (villeNaissance.getText().toString().length() < 2) {
            toast("Vous devez fournin une ville de naissance valide");
            return false;
        }
        if (filechoose.getText().toString().length() < 1) {
            toast("Vous devez fournin un document validant votre identité: image de votre "+(radioCin.isChecked() ? "carte d'identité" : "passport"));
            return false;
        }

        return true;
    }

    public void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}