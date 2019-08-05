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

package berlin.volders.badger;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("WeakerAccess")
public class BadgerTest {

    final TestBadgerDrawableFactory factory = new TestBadgerDrawableFactory();

    @Test
    public void sett_on_non_LayerDrawable() {
        Drawable drawable = new TestDrawable();

        Badger<TestBadgeDrawable> badger = Badger.sett(drawable, factory);

        assertBadged(badger.drawable, badger.badge, drawable);
    }

    @Test
    public void sett_on_LayerDrawable_without_badge() {
        Drawable[] drawables = {new TestDrawable(), new TestDrawable()};
        LayerDrawable layer = new LayerDrawable(drawables);

        Badger<TestBadgeDrawable> badger = Badger.sett(layer, factory);

        assertBadged(badger.drawable, badger.badge, drawables);
    }

    @Test(expected = IllegalStateException.class)
    public void sett_on_LayerDrawable_with_invalid_badge() {
        LayerDrawable layer = new LayerDrawable(new Drawable[]{new TestDrawable(), new TestDrawable()});
        layer.setId(0, R.id.badger_drawable);

        Badger.sett(layer, factory);
    }

    @Test
    public void sett_on_LayerDrawable_with_valid_badge() {
        Drawable[] drawables = {factory.createBadge(), new TestDrawable()};
        LayerDrawable layer = new LayerDrawable(drawables);
        layer.setId(0, R.id.badger_drawable);

        Badger<TestBadgeDrawable> badger = Badger.sett(layer, factory);

        assertBadged(badger.drawable, badger.badge, drawables);
    }

    @Test
    public void sett_on_MenuItem() {
        Drawable[] drawables = {new TestDrawable(), new TestDrawable()};
        LayerDrawable layer = new LayerDrawable(drawables);
        MenuItem menuItem = new TestMenuItem();
        menuItem.setIcon(layer);

        TestBadgeDrawable badge = Badger.sett(menuItem, factory);

        assertBadged(menuItem.getIcon(), badge, drawables);
    }

    @Test
    public void sett_on_ImageView() {
        Drawable[] drawables = {new TestDrawable(), new TestDrawable()};
        LayerDrawable layer = new LayerDrawable(drawables);
        ImageView imageView = new ImageView(ApplicationProvider.getApplicationContext());
        imageView.setImageDrawable(layer);

        TestBadgeDrawable badge = Badger.sett(imageView, factory);

        assertBadged(imageView.getDrawable(), badge, drawables);
    }

    @Test
    public void sett_and_mutate() {
        Drawable[] drawables = {factory.createBadge(), new TestDrawable()};
        LayerDrawable layer = new LayerDrawable(drawables);
        layer.setId(0, R.id.badger_drawable);

        Badger.sett(layer, factory).drawable.mutate();
    }

    void assertBadged(Drawable drawable, TestBadgeDrawable badge, Drawable... drawables) {
        assertThat(drawable, instanceOf(LayerDrawable.class));
        LayerDrawable layer = (LayerDrawable) drawable;
        assertThat(layer.findDrawableByLayerId(R.id.badger_drawable), is((Drawable) badge));
        int size = layer.getNumberOfLayers();
        if (drawables.length < size) {
            assertThat(layer.getDrawable(--size), is((Drawable) badge));
        }
        List<Drawable> layers = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            layers.add(layer.getDrawable(i));
        }
        assertThat(layers, contains(drawables));
    }
}
