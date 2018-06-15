package com.example.kaito.docsjuris.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.kaito.docsjuris.R;
import com.example.kaito.docsjuris.RegisterActivity;
import com.example.kaito.docsjuris.helpers.BCrypt;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by KaiTo on 4/28/2018.
 */

public class Register1Fragment extends Fragment implements RegisterActivity.Connector {

    private Context context;
    private View rootView;
    private static Register1Fragment fragment;
    private EditText nom, prenom, email, password;
    private CircleImageView profileImage;

    public static Register1Fragment newInstance() {
        if (fragment == null)
            fragment = new Register1Fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fr_register1, container, false);
        context = rootView.getContext();
        nom = rootView.findViewById(R.id.nom);
        prenom = rootView.findViewById(R.id.prenom);
        email = rootView.findViewById(R.id.email);
        password = rootView.findViewById(R.id.password);
        profileImage = rootView.findViewById(R.id.profileImageSelect);
        profileImage.setOnClickListener(e -> ((RegisterActivity) getActivity()).imageBrowse());
        return rootView;
    }


    @Override
    public void getData() {
        String hashedPassword;
//        BCrypt.checkpw(password.getText().toString(), hashedPassword);
        ((RegisterActivity) getActivity()).userData.put("nom", nom.getText().toString());
        ((RegisterActivity) getActivity()).userData.put("prenom", prenom.getText().toString());
        ((RegisterActivity) getActivity()).userData.put("email", email.getText().toString());
        ((RegisterActivity) getActivity()).userData.put("password", BCrypt.hashpw(password.getText().toString(), BCrypt.gensalt()));
        ((RegisterActivity) getActivity()).passwordPlain = password.getText().toString();
    }

    @Override
    public void updateImage(Bitmap bitmap,String path) {
        profileImage.setImageBitmap(bitmap);
    }

    @Override
    public void updateRadio(boolean isCinChecked) {

    }

    @Override
    public boolean validateData() {
        if (nom.getText().toString().length() < 2) {
            toast("Vous devez fournin un nom valide");
            return false;
        }
        if (prenom.getText().toString().length() < 2) {
            toast("Vous devez fournin un prenom valide");
            return false;
        }
        if (TextUtils.isEmpty(email.getText().toString()) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            toast("Vous devez fournir une adresse mail valide");
            return false;
        }
        if (password.getText().toString().length() < 4) {
            toast("Vous devez fournir un mot de passe d'une longueur plus que 5 caratceres");
            return false;
        }

        return true;
    }

    public void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}