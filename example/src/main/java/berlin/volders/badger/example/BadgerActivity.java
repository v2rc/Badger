/*
 * Copyright (C) 2016 Christian Schmitz
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

package berlin.volders.badger.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import berlin.volders.badger.BadgeShape;
import berlin.volders.badger.Badger;
import berlin.volders.badger.CountBadge;

public class BadgerActivity extends AppCompatActivity {

    CountBadge.Factory ovalFactory;
    CountBadge.Factory squareFactory;
    CountBadge.Factory circleFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badger);
        ovalFactory = new CountBadge.Factory(this, BadgeShape.oval(1f, 2f, Gravity.BOTTOM));
        squareFactory = new CountBadge.Factory(this, BadgeShape.square(1f, Gravity.NO_GRAVITY, .5f));
        circleFactory = new CountBadge.Factory(this, new CustomBadgeShape(this, .5f, Gravity.END | Gravity.TOP));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_badger, menu);
        Badger.sett(menu.findItem(R.id.action_oval), ovalFactory).setCount(0);
        Badger.sett(menu.findItem(R.id.action_square), squareFactory).setCount(0);
        Badger.sett(menu.findItem(R.id.action_circle), circleFactory).setCount(0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_oval:
            case R.id.action_square:
            case R.id.action_circle:
                // factory is not used for getting the badge
                //noinspection ConstantConditions
                CountBadge badge = Badger.sett(item, null);
                badge.setCount(badge.getCount() + 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void resetBadges(View view) {
        invalidateOptionsMenu();
    }
}
