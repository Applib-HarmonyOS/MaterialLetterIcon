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

package com.github.ivbaranov.mli.example;

import ohos.agp.components.BaseItemProvider;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Text;
import ohos.agp.utils.Color;
import ohos.app.Context;
import com.github.ivbaranov.mli.MaterialLetterIcon;
import java.util.ArrayList;
import java.util.List;

/**
 * ListAdapter.
 */
public class ListAdapter extends BaseItemProvider {
    private static final int CONTACTS = 1;
    private static final int COUNTRIES = 0;
    private static final int DEFAULT_LETTERS_NUMBER = 1;
    private static final int CONTACTS_LETTERS_NUMBER = 1;
    private static final int COUNTRIES_LETTERS_NUMBER = 3;

    private int mType;
    private Context context;
    private List<Item> itemList;
    private ItemClickListener clickListener;
    private Color colors[] = {Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.GRAY};

    /**
     * ItemAdapter.
     *
     * @param context context
     * @param type of list (CONTACTS, COUNTRIES)
     */
    public ListAdapter(Context context, int type) {
        this.context = context;
        this.itemList = new ArrayList<>();
        this.mType = type;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int index) {
        return itemList.get(index);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public Component getComponent(int index, Component component, ComponentContainer componentContainer) {
        ViewHolder viewHolder;
        if (component == null) {
            component = LayoutScatter.getInstance(context).parse(ResourceTable.Layout_list_item, null, false);
            viewHolder = new ViewHolder(component);
            component.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) component.getTag();
        }
        switch (mType) {
            case COUNTRIES:
                viewHolder.placeIcon.setLettersNumber(COUNTRIES_LETTERS_NUMBER);
                break;
            case CONTACTS:
                viewHolder.placeIcon.setLettersNumber(CONTACTS_LETTERS_NUMBER);
                break;
            default:
                viewHolder.placeIcon.setLettersNumber(DEFAULT_LETTERS_NUMBER);
        }
        viewHolder.placeIcon.setShapeColor(colors[index % colors.length]);
        viewHolder.placeName.setText(itemList.get(index).getName());
        viewHolder.placeIcon.setLetter(itemList.get(index).getName());
        return component;
    }

    /**
     * ItemClickListener.
     *
     */
    public interface ItemClickListener {
        /**
         * onItemClick.
         *
         * @param position position
         */
        void onItemClick(int position);
    }

    /**
     * setItem.
     *
     * @param itemList itemList
     */
    public void setItem(List<Item> itemList) {
        this.itemList = itemList;
        notifyDataChanged();
    }

    /**
     * gets item.
     *
     * @param position position
     * @return item name
     */
    public Item getItemName(int position) {
        return itemList.get(position);
    }

    /**
     * ViewHolder.
     */
    public static class ViewHolder {
        Text placeName;
        MaterialLetterIcon placeIcon;

        public ViewHolder(Component component) {
            placeName = (Text) component.findComponentById(ResourceTable.Id_text);
            placeIcon = (MaterialLetterIcon) component.findComponentById(ResourceTable.Id_icon);
        }
    }
}


