/*
 * Copyright (C) 2020-21 Application Library Engineering Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.ivbaranov.mli.example.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.ListContainer;
import com.github.ivbaranov.mli.example.Item;
import com.github.ivbaranov.mli.example.ListAdapter;
import com.github.ivbaranov.mli.example.ResourceTable;
import java.util.ArrayList;
import java.util.List;

/**
 * AbilitySlice for Countries.
 */
public class CountryAbilitySlice extends AbilitySlice {

    private static final int COUNTRIES = 0;
    private static final String[] countries = {
            "Albania", "Australia", "Belgium", "Canada", "China", "Dominica", "Egypt", "Estonia",
            "Finland", "France", "Germany", "Honduras", "Italy", "Japan", "Madagascar", "Netherlands",
            "Norway", "Panama", "Portugal", "Romania", "Russia", "Slovakia", "Vatican", "Zimbabwe"
    };

    private List<Item> itemList;
    private ListAdapter listAdapter;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_list_container);
        initView();
    }

    private void initView() {
        itemList = new ArrayList<>();
        for (String personName : countries) {
            Item item = new Item();
            item.setName(personName);
            itemList.add(item);
        }
        listAdapter = new ListAdapter(this, COUNTRIES);
        listAdapter.setItem(itemList);
        ListContainer listContainer = (ListContainer) findComponentById(ResourceTable.Id_recyclerview);
        listContainer.setItemProvider(listAdapter);
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
