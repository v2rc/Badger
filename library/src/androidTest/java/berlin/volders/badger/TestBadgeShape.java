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

package berlin.volders.badger;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

class TestBadgeShape extends BadgeShape {

    private Canvas canvas;
    private Rect region;
    private int badgeColor;

    TestBadgeShape(@FloatRange(from = 0, to = 1) float scale, float ratio, int gravity) {
        super(scale, ratio, gravity);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas, @NonNull Rect region, @NonNull Paint paint) {
        this.canvas = canvas;
        this.region = region;
        this.badgeColor = paint.getColor();
    }

    void assertCanvas(Canvas canvas) {
        assertThat(this.canvas, equalTo(canvas));
    }

    void assertRegion(Rect region) {
        assertThat(this.region, equalTo(region));
    }

    void assertBadgeColor(int badgeColor) {
        assertThat(this.badgeColor, is(badgeColor));
    }
}
