package com.marcel.a.n.roxha.ajuda_teconta.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class ConfiguracaoFirebase {

    private static DatabaseReference reference;
    private static FirebaseFirestore firestore;
    private static FirebaseAuth auth;

    public static FirebaseFirestore getFirestore(){

        if (firestore == null){
            firestore = FirebaseFirestore.getInstance();
        }
        return firestore;
    }


    public static DatabaseReference getReference(){

        if (reference == null){

            reference = FirebaseDatabase.getInstance().getReference();
        }
        return reference;
    }

    public static FirebaseAuth getAuth(){

        if (auth == null){

            auth = FirebaseAuth.getInstance();
        }

        return auth;
    }

}
