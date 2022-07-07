package com.marcel.a.n.roxha.ajuda_teconta.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.marcel.a.n.roxha.ajuda_teconta.R;
import com.marcel.a.n.roxha.ajuda_teconta.fragment.GastoAvulsoFragment;
import com.marcel.a.n.roxha.ajuda_teconta.fragment.GastoFixoFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class GastoActivity2 extends AppCompatActivity {

    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gasto2);

        smartTabLayout = findViewById(R.id.smartTabGastos);
        viewPager = findViewById(R.id.viewPagerGastos);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("GASTO AVULSO", GastoAvulsoFragment.class)
                       .add("GASTO FIXO", GastoFixoFragment.class)
                        .create()
        );
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);

    }
}