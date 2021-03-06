/*
 *    Copyright (C) 2016 Haruki Hasegawa
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.h6ah4i.android.example.advrecyclerview.demo_hf_minimal;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.example.swipetestdemo.R;
import com.h6ah4i.android.example.advrecyclerview.common.adapter.DemoHeaderFooterAdapter;
import com.h6ah4i.android.example.advrecyclerview.common.adapter.OnListItemClickMessageListener;
import com.h6ah4i.android.example.advrecyclerview.common.adapter.SimpleDemoItemAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*
 * This example shows very very minimal implementation of header and footer feature.
 * Please refer to other examples for more advanced usages. Thanks!
 */
public class MinimalHeaderFooterExampleActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo_minimal);

        OnListItemClickMessageListener clickListener = message -> {
            View container = findViewById(R.id.container);
            Snackbar.make(container, message, Snackbar.LENGTH_SHORT).show();
        };

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.Adapter adapter;

        adapter = new SimpleDemoItemAdapter(clickListener);
        adapter = new DemoHeaderFooterAdapter(adapter, clickListener);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
