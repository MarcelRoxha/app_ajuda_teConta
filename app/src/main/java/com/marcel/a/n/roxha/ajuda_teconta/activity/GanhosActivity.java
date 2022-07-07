package com.marcel.a.n.roxha.ajuda_teconta.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.marcel.a.n.roxha.ajuda_teconta.R;
import com.marcel.a.n.roxha.ajuda_teconta.fragment.GanhoAvulsoFragment;
import com.marcel.a.n.roxha.ajuda_teconta.fragment.GanhoFixoFragment;
import com.marcel.a.n.roxha.ajuda_teconta.fragment.GastoAvulsoFragment;
import com.marcel.a.n.roxha.ajuda_teconta.fragment.GastoFixoFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class GanhosActivity extends AppCompatActivity {
    
    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganhos);

        smartTabLayout = findViewById(R.id.smartTabGanhos);
        viewPager = findViewById(R.id.viewPagerGanhos);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("GANHO AVULSO", GanhoAvulsoFragment.class)
                        .add("GANHO FIXO", GanhoFixoFragment.class)
                        .create()
        );
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);

    }
}