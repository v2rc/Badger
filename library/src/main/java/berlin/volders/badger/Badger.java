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

import androidx.annotation.NonNull;

/**
 * Utility to wrap a drawable with a badge on it.
 */
@SuppressWarnings("WeakerAccess")
public class Badger<T extends BadgeDrawable> {

    /**
     * The final badged drawable {@link #badge} or a {@link LayerDrawable}
     */
    public final Drawable drawable;
    /**
     * The badge drawable
     */
    public final T badge;

    private Badger(Drawable drawable, T badge) {
        this.drawable = drawable;
        this.badge = badge;
    }

    /**
     * Wraps any drawable with a badge
     *
     * @param drawable to add the badge to
     * @param factory  creating a new badge
     * @param <T>      the subtype of {@code BadgeDrawable} to create
     * @return the {@code Badger} holding the
     */
    public static <T extends BadgeDrawable> Badger<T>
    sett(Drawable drawable, @NonNull BadgeDrawable.Factory<? extends T> factory) {
        if (!(drawable instanceof LayerDrawable)) {
            T badge = factory.createBadge();
            LayerDrawable layer = new LayerDrawable(new Drawable[]{drawable, badge});
            layer.setId(1, R.id.badger_drawable);
            return new Badger<>(layer, badge);
        }
        LayerDrawable layer = (LayerDrawable) drawable;
        drawable = layer.findDrawableByLayerId(R.id.badger_drawable);
        if (drawable == null) {
            T badge = factory.createBadge();
            int count = layer.getNumberOfLayers();
            Drawable[] layers = new Drawable[count + 1];
            for (int i = 0; i < count; i++) {
                layers[i] = layer.getDrawable(i);
            }
            layers[count] = badge;
            layer = new LayerDrawable(layers);
            layer.setId(count, R.id.badger_drawable);
            return new Badger<>(layer, badge);
        }
        try {
            //noinspection unchecked
            return new Badger<>(layer, (T) drawable);
        } catch (ClassCastException e) {
            String errorMessage = "layer with id R.id.badger_drawable must be an instance of "
                    + factory.createBadge().getClass().getSimpleName();
            throw new IllegalStateException(errorMessage);
        }
    }

    /**
     * Wraps the icon of a menu item with a badge
     *
     * @param item    to add the badge to
     * @param factory creating a new badge
     * @param <T>     the subtype of {@code BadgeDrawable} to create
     * @return the badge drawable
     */
    public static <T extends BadgeDrawable> T
    sett(@NonNull MenuItem item, @NonNull BadgeDrawable.Factory<? extends T> factory) {
        Badger<T> badger = sett(item.getIcon(), factory);
        item.setIcon(badger.drawable);
        return badger.badge;
    }

    /**
     * Wraps the image of an {@link ImageView} with a badge
     *
     * @param view    to add the badge to
     * @param factory creating a new badge
     * @param <T>     the subtype of {@code BadgeDrawable} to create
     * @return the badge drawable
     */
    public static <T extends BadgeDrawable> T
    sett(@NonNull ImageView view, @NonNull BadgeDrawable.Factory<? extends T> factory) {
        Badger<T> badger = sett(view.getDrawable(), factory);
        view.setImageDrawable(badger.drawable);
        return badger.badge;
    }
}
