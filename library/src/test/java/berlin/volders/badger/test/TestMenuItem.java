/*
 * Copyright (C) 2016 volders GmbH with <3 in Berlin
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

package berlin.volders.badger.test;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

public class TestMenuItem implements MenuItem {

    private Drawable icon;

    @Override
    public MenuItem setIcon(Drawable icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public Drawable getIcon() {
        return icon;
    }

    @Override
    public int getItemId() {
        throw new AssertionError("not mocked");
    }

    @Override
    public int getGroupId() {
        throw new AssertionError("not mocked");
    }

    @Override
    public int getOrder() {
        throw new AssertionError("not mocked");
    }

    @Override
    public MenuItem setTitle(CharSequence title) {
        throw new AssertionError("not mocked");
    }

    @Override
    public MenuItem setTitle(int title) {
        throw new AssertionError("not mocked");
    }

    @Override
    public CharSequence getTitle() {
        throw new AssertionError("not mocked");
    }

    @Override
    public MenuItem setTitleCondensed(CharSequence title) {
        throw new AssertionError("not mocked");
    }

    @Override
    public CharSequence getTitleCondensed() {
        throw new AssertionError("not mocked");
    }

    @Override
    public MenuItem setIcon(int iconRes) {
        throw new AssertionError("not mocked");
    }

    @Override
    public MenuItem setIntent(Intent intent) {
        throw new AssertionError("not mocked");
    }

    @Override
    public Intent getIntent() {
        throw new AssertionError("not mocked");
    }

    @Override
    public MenuItem setShortcut(char numericChar, char alphaChar) {
        throw new AssertionError("not mocked");
    }

    @Override
    public MenuItem setNumericShortcut(char numericChar) {
        throw new AssertionError("not mocked");
    }

    @Override
    public char getNumericShortcut() {
        throw new AssertionError("not mocked");
    }

    @Override
    public MenuItem setAlphabeticShortcut(char alphaChar) {
        throw new AssertionError("not mocked");
    }

    @Override
    public char getAlphabeticShortcut() {
        throw new AssertionError("not mocked");
    }

    @Override
    public MenuItem setCheckable(boolean checkable) {
        throw new AssertionError("not mocked");
    }

    @Override
    public boolean isCheckable() {
        throw new AssertionError("not mocked");
    }

    @Override
    public MenuItem setChecked(boolean checked) {
        throw new AssertionError("not mocked");
    }

    @Override
    public boolean isChecked() {
        throw new AssertionError("not mocked");
    }

    @Override
    public MenuItem setVisible(boolean visible) {
        throw new AssertionError("not mocked");
    }

    @Override
    public boolean isVisible() {
        throw new AssertionError("not mocked");
    }

    @Override
    public MenuItem setEnabled(boolean enabled) {
        throw new AssertionError("not mocked");
    }

    @Override
    public boolean isEnabled() {
        throw new AssertionError("not mocked");
    }

    @Override
    public boolean hasSubMenu() {
        throw new AssertionError("not mocked");
    }

    @Override
    public SubMenu getSubMenu() {
        throw new AssertionError("not mocked");
    }

    @Override
    public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener menuItemClickListener) {
        throw new AssertionError("not mocked");
    }

    @Override
    public ContextMenu.ContextMenuInfo getMenuInfo() {
        throw new AssertionError("not mocked");
    }

    @Override
    public void setShowAsAction(int actionEnum) {
        throw new AssertionError("not mocked");
    }

    @Override
    public MenuItem setShowAsActionFlags(int actionEnum) {
        throw new AssertionError("not mocked");
    }

    @Override
    public MenuItem setActionView(View view) {
        throw new AssertionError("not mocked");
    }

    @Override
    public MenuItem setActionView(int resId) {
        throw new AssertionError("not mocked");
    }

    @Override
    public View getActionView() {
        throw new AssertionError("not mocked");
    }

    @Override
    public MenuItem setActionProvider(ActionProvider actionProvider) {
        throw new AssertionError("not mocked");
    }

    @Override
    public ActionProvider getActionProvider() {
        throw new AssertionError("not mocked");
    }

    @Override
    public boolean expandActionView() {
        throw new AssertionError("not mocked");
    }

    @Override
    public boolean collapseActionView() {
        throw new AssertionError("not mocked");
    }

    @Override
    public boolean isActionViewExpanded() {
        throw new AssertionError("not mocked");
    }

    @Override
    public MenuItem setOnActionExpandListener(OnActionExpandListener listener) {
        throw new AssertionError("not mocked");
    }
}
