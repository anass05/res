package com.example.kaito.docsjuris.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kaito.docsjuris.R;
import com.example.kaito.docsjuris.RegisterActivity;

/**
 * Created by KaiTo on 4/28/2018.
 */

public class Register2Fragment extends Fragment implements RegisterActivity.Connector {

    private Context context;
    private View rootView;
    private EditText adresse, ville, profession, number;
    private static Register2Fragment fragment;

    public static Register2Fragment newInstance() {
        if (fragment == null)
            fragment = new Register2Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fr_register2, container, false);
        adresse = rootView.findViewById(R.id.adresse);
        profession = rootView.findViewById(R.id.profession);
        ville = rootView.findViewById(R.id.ville);
        number = rootView.findViewById(R.id.number);
        context = rootView.getContext();
        return rootView;
    }

    @Override
    public void getData() {
        ((RegisterActivity) getActivity()).userData.put("adresse", adresse.getText().toString());
        ((RegisterActivity) getActivity()).userData.put("ville", ville.getText().toString());
        ((RegisterActivity) getActivity()).userData.put("profession", profession.getText().toString());
        ((RegisterActivity) getActivity()).userData.put("tele", number.getText().toString());
    }

    @Override
    public void updateImage(Bitmap bitmap,String path) {

    }

    @Override
    public void updateRadio(boolean isCinChecked) {

    }

    @Override
    public boolean validateData() {
        if (adresse.getText().toString().length() < 8) {
            toast("Vous devez fournin une adresse valide");
            return false;
        }
        if (ville.getText().toString().length() < 2) {
            toast("Vous devez fournin une ville valide");
            return false;
        }
        if (profession.getText().toString().length() < 2) {
            toast("Vous devez fournin une ville valide");
            return false;
        }
        if (number.getText().toString().length() < 2) {
            toast("Vous devez fournin un numéro de téléphone valide");
            return false;
        }

        return true;
    }

    public void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}