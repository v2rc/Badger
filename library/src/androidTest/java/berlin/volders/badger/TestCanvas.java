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
import android.graphics.RectF;
import android.support.annotation.NonNull;

import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

class TestCanvas extends Canvas {

    private Shape shape;
    private RectF rect;
    private Object[] params;
    private int textColor;

    @Override
    public void drawOval(@NonNull RectF oval, @NonNull Paint paint) {
        rect = oval;
        shape = Shape.OVAL;
    }

    @Override
    public void drawRect(@NonNull Rect rect, @NonNull Paint paint) {
        this.rect = new RectF(rect);
        shape = Shape.RECT;
    }

    @Override
    public void drawRect(@NonNull RectF rect, @NonNull Paint paint) {
        this.rect = rect;
        shape = Shape.RECT;
    }

    @Override
    public void drawRoundRect(@NonNull RectF rect, float rx, float ry, @NonNull Paint paint) {
        this.rect = rect;
        shape = Shape.ROUND_RECT;
        params = new Object[]{rx, ry};
    }

    @Override
    public void drawText(@NonNull String text, float x, float y, @NonNull Paint paint) {
        this.textColor = paint.getColor();
    }

    void assertDrawn(Shape shape, Rect rect, Object... params) {
        assertThat(this.shape, is(shape));
        assertThat(this.rect, equalTo(new RectF(rect)));
        if (params.length == 0) {
            assertThat(this.params, nullValue());
        } else {
            assertThat(this.params, arrayContaining(params));
        }
    }

    void assertNothingDrawn() {
        assertThat(shape, nullValue());
        assertThat(rect, nullValue());
        assertThat(params, nullValue());
        assertThat(textColor, is(0));
    }

    void assertTextColor(int textColor) {
        assertThat(this.textColor, is(textColor));
    }

    enum Shape {
        OVAL, RECT, ROUND_RECT
    }
}
